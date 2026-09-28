# Case Study: Padiworks — Workforce & Employee Status Tracking Platform

> Representative QA portfolio project for Padiworks, built under DIBERR Solutions
> (Lagos). Public product details are limited; this entry describes the testing
> work as it stood during my engagement, with no client-confidential data.

## What Padiworks is

A B2B SaaS web platform companies use to track their workforce in one place —
employee status (active / on leave / off), attendance, leave requests, task
assignment, and HR reporting. Think of it as a focused internal tool in the
spirit of Jira / BambooHR, built for Nigerian SMEs running small teams.

Key modules:

1. **Authentication & roles** — Admin, Manager, Employee (RBAC)
2. **Dashboard** — live headcount / who-is-on-leave-at-a-glance
3. **Attendance** — daily check-in / check-out, work-hours log
4. **Leave management** — request → manager approval → balance updates
5. **Tasks** — manager assigns work, employee updates status
6. **Reporting** — CSV/Excel exports of attendance and leave

## My role: sole QA

I was the **only QA engineer** on the project, so I owned the whole lifecycle:

- Defined test strategy, wrote test plans and case sheets from scratch
- Manual regression for every release (RBAC, attendance, leave approvals, exports)
- Selenium/WebDriver (Java) automation for the critical login + leave-request flows
- Postman API testing on auth, attendance and leave endpoints
- Sign-off with product/engineering before each client UAT
- Bug triage directly with the dev team (Jira-style board)

## Why it was a testing challenge

- **RBAC done wrong = data leak** — a low-privilege employee must never see
  another employee's salary/leave balance or admin settings
  - **State consistency** — approving a leave must atomically update the leave
    balance and the dashboard; partial updates were a real bug class
    - **Time zones & business hours** — check-in rules differed by company policy
    - **CSV exports** — reports had to match DB aggregates exactly (no rounding drift)

    ## Deliverables in this folder

    - `test-plan.md` — sample test plan (roles, flows, entry/exit criteria)
    - `PadiworksWebTest.java` — Selenium-Java automation for login + leave request
    - 
