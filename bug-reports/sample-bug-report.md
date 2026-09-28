# Sample Defect Report (Jira-style)

| Field | Value |
|---|---|
| **Summary** | [Booking] Double booking allowed when two users select same slot simultaneously |
| **Type** | Bug |
| **Priority** | Critical |
| **Severity** | Blocker |
| **Environment** | Staging v2.4.0-rc3 · Chrome 128 · Desktop |
| **Reporter** | Maurice R. Amande |

## Steps to reproduce

1. Open `/booking` in browser A (user Alice) and browser B (user Bob) simultaneously.
2. Both select slot `2026-10-01 09:00` and click Confirm within the same 3 seconds.
3. Open clinic schedule for that day.

## Expected result

The second request is rejected with "Slot no longer available"; only one booking exists.

## Actual result

Both bookings are created with status `scheduled`; clinic sees two patients in the same slot.

## Evidence

- Console: `POST /bookings -> 201` returned twice with different `bookingId` values.
- Network HAR: `screenshots/booking-race.png`
- DB query result: two rows for slot `2026-10-01T09:00:00Z` (see `sql-validation/`).

## Suggested fix

Enforce a unique constraint on `(clinic_id, slot_start)` and return `409 Conflict` on the duplicate request.

## Regression notes

After fix, re-run TC-BOOK-002 and add a concurrency test to the Postman collection.
