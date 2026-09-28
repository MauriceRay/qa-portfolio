# Selenium WebDriver (Java) examples

Representative regression tests written in the style used on real client products.

## Stack

- **Language:** Java 11+
- **Framework:** Selenium WebDriver 4 + JUnit 5
- **Runners:** local Chrome / headless CI
- **Pattern:** explicit waits, single-assertion intent per test, evidence on failure

## Run

```bash
# chromedriver matching your Chrome version must be on PATH
mvn test -Dtest=LoginFlowTest
```

## Notes

- Tests target `app.example.com` (placeholder). Replace with the product under test.
- Sensitive values (real credentials, URLs) live in environment variables / `.env`, never in code — see `.gitignore`.
- These tests were designed to run as part of a release regression gate before production deployments.
- 
