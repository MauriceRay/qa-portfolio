import { test, expect } from '@playwright/test';

/**
 * Representative Playwright (TypeScript) regression suite for a web app login flow.
 * Runs across Chromium, Firefox, and WebKit — mirrors the cross-browser testing
 * used on real client releases.
 */
test.describe('Login flow', () => {
    test.beforeEach(async ({ page }) => {
          await page.goto('/login');
    });

                test('valid credentials land on the dashboard', async ({ page }) => {
                      await page.getByLabel('Email').fill('qa.user@example.com');
                      await page.getByLabel('Password').fill('SecurePass123!');
                      await page.getByRole('button', { name: 'Sign in' }).click();

                         await expect(page).toHaveURL(/\/dashboard/);
                      await expect(page.locator('.dashboard-greeting')).toContainText('Welcome');
                });

                test('invalid password shows an inline error and stays on login', async ({ page }) => {
                      await page.getByLabel('Email').fill('qa.user@example.com');
                      await page.getByLabel('Password').fill('wrong-password');
                      await page.getByRole('button', { name: 'Sign in' }).click();

                         await expect(page.locator('.form-error')).toContainText(/invalid/i);
                      await expect(page).toHaveURL(/\/login/);
                });

                test('locked account surfaces an explanatory banner', async ({ page }) => {
                      await page.getByLabel('Email').fill('locked.user@example.com');
                      await page.getByLabel('Password').fill('AnyPassword1!');
                      await page.getByRole('button', { name: 'Sign in' }).click();

                         await expect(page.locator('.account-locked-banner')).toBeVisible();
                });
});
