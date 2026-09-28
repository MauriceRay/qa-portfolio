# Sample Test Plan — Release `v2.4.0`

| Field | Value |
|---|---|
| Product | Example Web App |
| Release | v2.4.0 |
| Author | Maurice R. Amande |
| Environment | Staging |
| Planned sign-off date | 2 days before production deploy |

## 1. Objectives

Validate the new booking workflow and the patient-list performance improvement before
production deployment, and confirm no regression in existing scheduling, billing, or reporting modules.

## 2. Scope

**In scope**
- New booking flow (slot selection, confirmation, reminders)
- Patient list pagination + search performance
- Smoke regression of auth, dashboard, billing export

**Out of scope**
- Native mobile 3.2 (separate release train)
- Localization (due next sprint)

## 3. Test approach

| Type | Focus | Tool / Evidence |
|---|---|---|
| Smoke | Critical path still works after deploy | Checklist in Zephyr |
| Functional | New booking rules, edge cases | Manual test cases |
| Regression | Existing modules unaffected | Selenium/Java suite |
| API | Booking endpoints contract + timing | Postman/Newman |
| Data validation | Created bookings match DB and reports | SQL queries |
| Cross-browser | Chrome, Firefox, Safari; iOS + Android | Device matrix |
| Usability | Heuristic review with Product | Notes shared in Confluence |

## 4. Entry criteria

- Code frozen, deployed to staging
- Smoke data and test accounts provisioned
- Open Sev-1/Sev-2 bugs from previous release verified closed

## 5. Exit / sign-off criteria

- All planned smoke 
