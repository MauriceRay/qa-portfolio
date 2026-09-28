-- ============================================================
-- Sample data-integrity validation queries
-- Used to reconcile UI / API behavior against the database after
-- releases, billing runs, and reporting changes.
-- ============================================================

-- 1. Double-booked slots (should return 0 rows)
SELECT clinic_id, slot_start, COUNT(*) AS bookings
FROM bookings
WHERE status = 'scheduled'
GROUP BY clinic_id, slot_start
HAVING COUNT(*) > 1;

-- 2. Booking total on today's report matches transactional table
SELECT
  (SELECT COUNT(*) FROM bookings
      WHERE CAST(created_at AS DATE) = CAST(GETDATE() AS DATE)) AS bookings_today,
  (SELECT COUNT(*) FROM reporting_daily
      WHERE report_date = CAST(GETDATE() AS DATE))           AS report_rows_today;

-- 3. Billing amount = booking price + taxes (no drift)
SELECT b.booking_id,
       b.price,
       b.tax,
       i.amount AS invoice_amount
FROM bookings b
JOIN invoices i ON i.booking_id = b.booking_id
WHERE ABS((b.price + b.tax) - i.amount) > 0.01;

-- 4. Orphaned references (booking exists but patient was deleted)
SELECT b.booking_id
FROM bookings b
LEFT JOIN patients p ON p.patient_id = b.patient_id
WHERE p.patient_id IS NULL;

-- 5. Streaming / catalog reconciliation example
--    (used on catalog-heavy products: count vs expected)
SELECT s.artist_id,
       COUNT(s.track_id)              AS catalog_tracks,
       COALESCE(c.reported_tracks, 0) AS reported_tracks
FROM catalog s
LEFT JOIN catalog_report c ON c.artist_id = s.artist_id
GROUP BY s.artist_id, COALESCE(c.reported_tracks, 0)
HAVING COUNT(s.track_id) <> COALESCE(c.reported_tracks, 0);
