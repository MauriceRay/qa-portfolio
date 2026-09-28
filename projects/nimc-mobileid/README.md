# Case Study: NIMC Mobile ID (NIN) — QA Test Project

> Representative QA portfolio project based on Nigeria's national mobile identity
> system (NIMC MWS Mobile ID / NINAuth). Built from public product documentation;
> no confidential NIMC data is used.

## Background

Nigeria's NIMC Mobile ID app lets citizens carry their identity on a smartphone:
they log in with their 11-digit NIN and OTP, set a 6-digit PIN, view their
verified profile (names, photograph, enrolment status), and — critically —
generate a **Virtual NIN (vNIN)** instead of exposing their real NIN.

Key product flows this project covers:

1. **Onboarding** — NIN + trusted phone number → OTP → 6-digit PIN
2. **Virtual NIN** — generate a 16-character tokenized vNIN that expires after 72h
3. **Show My ID** — dynamic QR with a Basic / Full ID toggle (verifier scans)
4. **Biometric login** — fingerprint / face unlock
5. **Verification records** — every verification is logged to the holder

## Why it is a hard testing problem

- Identity-grade security: wrong data = wrong KYC for banks/telecoms
- Privacy is a feature: Basic vs Full disclosure must never leak extra fields
- vNIN has a hard 72-hour expiry that must behave consistently offline/online
- SMS OTP delivery across 4 Nigerian networks (MTN, Glo, Airtel, 9mobile)
- Biometric capture + low-end Android devices (main market)

## My scope as QA

- Risk-based test strategy across onboarding, vNIN, QR verification, PIN security
- Manual regression on real low-end Android devices + biometric hardware
- Appium mobile automation for the critical vNIN generation flow
- API-level verification of vNIN issuance/expiry with Postman
- Sign-off gates for privacy: assertion that Basic-ID QR never carries DOB

## Deliverables in this folder

- `test-plan.md` — sample test plan (scope, environments, entry/exit criteria)
- `AppiumMobileIDTest.java` — sample Appium automation for vNIN generation
- 
