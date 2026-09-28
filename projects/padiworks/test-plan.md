# Test Plan — Padiworks (Sample)

## 1. Objective
Guarantee that employee status, attendance and leave state are consistent across
roles, dashboards and exports — and that role-based access never exposes data it
should not.

## 2. In scope
- Auth: signup, login, password reset, session timeout
- RBAC: Admin / Manager / Employee permissions matrix
- Dashboard: live active / on-leave / off-duty counts
- Attendance: check-in, check-out, work-hours calculation
- Leave: request, manager approve/reject, balance decrement, email notifications
- Tasks: assignment, status updates, manager visibility
- Reports: attendance & leave CSV exports

## 3. Out of scope
- Payments / billing for the SaaS itself (handled by a separate team)
- Mobile app (web-only at the time)

## 4. Key risk scenarios
| Risk | Why it matters |
|---|---|
| Employee opens `/admin/users` directly via URL | RBAC bypass = data leak |
| Approving leave but balance not decremented | HR pays wrong leave days |
| Dashboard shows stale count after approval | Managers mis-staff the day |
| CSV row count != filtered result count | Reports disputed at payroll |
| Check-in after midnight edge case | Wrong day logged |

## 5. Test data
- Users in each role (admin / manager / employee) across 2 companies
- Leave balances at 0, mid, full
- Approval flow: pending / approved / rejected

## 6. Entry / Exit criteria
**Entry:** staging build deployed; seed users and balances reset.
**Exit:**
- P0/P1 = 0 open (RBAC bypasses and leave-balance bugs are P0 by definition)
- Leave flow verified end-to-end: request → approve → balance → dashboard → export
- RBAC matrix executed negative-by-negative

## 7. Automation coverage (Selenium/Java + Postman)
- Login as each role → correct landing page
- Employee submits leave → manager sees it → approves → balance drops
- Employee cannot reach admin routes
- 
