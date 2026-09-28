import { defineConfig, devices } from '@playwright/test';

/**
 * Playwright configuration: cross-browser regression gate.
 * Mirrors the release strategy used on web products:
 * Chromium + Firefox + WebKit, retries in CI, failure traces on.
 */
export default defineConfig({
    testDir: './tests',
    fullyParallel: true,
    retries: process.env.CI ? 2 : 0,
    reporter: 'html',
    use: {
          baseURL: 'https://staging.app.example.com',
          trace: 'on-first-retry',
          screenshot: 'only-on-failure',
    },
    projects: [
      { name: 'chromium', use: { ...devices['Desktop Chrome'] } },
      { name: 'firefox', use: { ...devices['Desktop Firefox'] } },
      { name: 'webkit', use: { ...devices['Desktop Safari'] } },
      { name: 'mobile-android', use: { ...devices['Pixel 7'] } },
        ],
});
