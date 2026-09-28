# Sample Test Cases — Booking Flow

Format mirrors the Zephyr/HP ALM style used on client projects:
precondition, steps, expected result, priority.

## TC-BOOK-001 — Book an available slot successfully

- **Priority:** High
- **Precondition:** Logged in as patient; clinic has available slots today
- **Steps:**
  1. Open `/booking`
    2. Select today, 09:00
      3. Confirm booking
      - **Expected:** Confirmation screen shows booking reference; booking appears in "My appointments"; confirmation email received within 2 minutes.

      ## TC-BOOK-002 — Cannot double-book the same slot

      - **Priority:** High
      - **Precondition:** Slot 09:00 already booked by the same patient
      - **Steps:**
        1. Open `/booking`
          2. Select the same slot
          - **Expected:** Slot is disabled/visible as taken; system shows "This slot is no longer available" and prevents submission.

          ## TC-BOOK-003 — Required-field validation on empty submission

          - **Priority:** Medium
          - **Steps:**
            1. Open `/booking`
              2. Leave slot unselected
                3. Click Confirm
                - **Expected:** Inline validation errors appear next to the slot field; no request is sent.

                ## TC-BOOK-004 — Past slots are not selectable

                - **Priority:** Medium
                - **Steps:**
                  1. Open calendar for a past date
                  - **Expected:** Past dates are disabled; past slots show as unavailable.

                  ## TC-BOOK-005 — Booking appears in clinic reporting

                  - **Priority:** High (data integrity)
                  - **Precondition:** TC-BOOK-001 passed
                  - **Steps:**
                    1. Open clinic `/reports?range=today`
                    - **Expected:** The new booking appears in today's total with the correct patient, slot, and status = scheduled. Cross-check against DB with `sql-validation/` queries.
  
