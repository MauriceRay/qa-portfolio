# Web Automation (Playwright / TypeScript)

Cross-browser web regression suites written with Playwright.

## Stack

- **Language:** TypeScript
- **Framework:** Playwright
- **Coverage:** Chromium, Firefox, WebKit, plus an Android mobile viewport (Pixel 7)

## Run

```bash
npm install
npx playwright install --with-deps
npx playwright test
```

## What is covered

- Login flow across browsers (valid, invalid, locked-account states)
- Page-object-friendly locators (`getByLabel`, `getByRole`)
- Traces, screenshots on failure, retries in CI

These tests are designed to run as part of the pre-release regression gate.
