# Test Plan — NIMC Mobile ID App (Sample)

## 1. Objective
Validate that a citizen can securely onboard with their NIN, generate a working
Virtual NIN (vNIN), and present verifiable identity via QR — without ever exposing
their permanent NIN or leaking more personal data than they consent to.

## 2. In scope
- Onboarding: NIN entry, OTP across MTN/Glo/Airtel/9mobile, 6-digit PIN setup
- vNIN generation: 16-char token, 72-hour expiry, one-time use semantics
- Show My ID: Basic ID vs Full ID toggle; verifier QR scan
- PIN security: 5-attempt lockout, biometric (face/fingerprint) fallback
- Verification record log accuracy

## 3. Out of scope
- Backend enrolment system (captured separately by NIMC ops)
- USSD *346# channel (owned by the telecoms team)

## 4. Environments
- Staging Android build on Android 10 / 13 / 14 devices (low-end: 2GB RAM)
- iOS 16/17 for the QR verification half
- Staging identity API with sandboxed NIN records (no real PII)

## 5. Key test data
- Valid NIN (sandbox), unregistered NIN, malformed NIN (<11 digits / non-numeric)
- OTP: valid, expired (>5 min), wrong, resend-limit (>3 requests)
- vNIN: fresh, between 0–72h, expired >72h, already-redeemed
- Basic ID vs Full ID consent states

## 6. Entry / Exit criteria
**Entry:** build passes smoke; sandbox OTP stub delivers reliably.
**Exit:**
- P0/P1 defects = 0 open
- vNIN expiry behaves consistently on reconnect after flight-mode
- Privacy gate verified: Basic QR contains no DOB/nationality/sex
- Crash-free sessions on low-end devices > 99%

## 7. Risks & mitigations
| Risk | Mitigation |
|---|---|
| SMS OTP delays on a network | Wait retries aligned to carrier; report network-specific |
| Biometric mismatch on dark skin / low light | Real-device testing in Lagos daylight + night |
| vNIN expiry timezone drift | Assert expiry against server time, not device clock |
| Privacy leak through QR payload | Negative tests: scan Basic QR and assert fields absent |
