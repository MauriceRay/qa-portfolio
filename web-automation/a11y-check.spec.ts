import { test, expect } from '@playwright/test';
import AxeBuilder from '@axe-core/playwright';

/**
 * Accessibility regression — part of the work tracked in issue #2.
 * Fails the build on critical WCAG violations on the key authenticated pages.
 */
test.describe('Accessibility', () => {
    test('login page has no critical a11y violations', async ({ page }) => {
          await page.goto('/login');
          const results = await new AxeBuilder({ page })
            .withTags(['wcag21aa', 'wcag22aa'])
            .analyze();

             const critical = results.violations.filter(v => v.impact === 'critical');
          expect(critical, JSON.stringify(critical.map(v => v.id))).toHaveLength(0);
    });

                test('dashboard has no critical a11y violations for signed-in user', async ({ page }) => {
                      await page.goto('/login');
                      await page.getByLabel('Email').fill('qa.user@example.com');
                      await page.getByLabel('Password').fill('SecurePass123!');
                      await page.getByRole('button', { name: 'Sign in' }).click();
                      await expect(page).toHaveURL(/\/dashboard/);

                         const results = await new AxeBuilder({ page })
                        .withTags(['wcag21aa'])
                        .analyze();

                         const critical = results.violations.filter(v => v.impact === 'critical');
                      expect(critical, JSON.stringify(critical.map(v => v.id))).toHaveLength(0);
                });
});
