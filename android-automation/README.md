# Android Automation (Appium + UiAutomator2)

Representative mobile regression tests written for real Android devices/emulators.

## Stack

- **Language:** Java 11+
- **Framework:** Appium 2.x + JUnit 5
- **Driver:** UiAutomator2 (native Android)
- **Targets:** Android phones / tablets, real device farm in CI

## What these tests cover

- Native app login flow (valid + invalid credentials)
- Locators by resource-id and accessibility labels
- Explicit waits, single-assertion intent per test, clean teardown

## Run locally

```bash
# Start Appium server, connect an emulator/device (adb devices), then:
mvn test -Dtest=AndroidLoginTest
```

Replace `appPackage` / `appActivity` with the application under test.
