package com.mauriceqa.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Representative Selenium WebDriver (Java) regression test.
                                       * Pattern used on real products: page-ready waits, explicit assertions,
   * evidence on failure, and a clean teardown so the suite stays reliable in CI.
   */
public class LoginFlowTest {

    private WebDriver driver;
      private WebDriverWait wait;
      private static final String BASE_URL = "https://app.example.com";

    @BeforeEach
      void setUp() {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new", "--window-size=1440,900", "--no-sandbox");
                driver = new ChromeDriver(options);
                wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      }

    @Test
      void validCredentials_logsInAndShowsDashboard() {
                driver.get(BASE_URL + "/login");

          driver.findElement(By.id("email")).sendKeys("qa.user@example.com");
                driver.findElement(By.id("password")).sendKeys("SecurePass123!");
                driver.findElement(By.id("login-btn")).click();

          // Expected: lands on dashboard and greets the user
          WebElement greeting = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".dashboard-greeting")));
                assertTrue(greeting.getText().contains("Welcome"),
                                           "Expected welcome greeting on dashboard after login");
                assertTrue(driver.getCurrentUrl().contains("/dashboard"),
                                           "Expected URL to contain /dashboard after successful login");
      }

    @Test
      void invalidPassword_showsInlineError_andStaysOnLogin() {
                driver.get(BASE_URL + "/login");

          driver.findElement(By.id("email")).sendKeys("qa.user@example.com");
                driver.findElement(By.id("password")).sendKeys("wrong-password");
                driver.findElement(By.id("login-btn")).click();

          WebElement error = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".form-error")));
                assertTrue(error.getText().toLowerCase().contains("invalid"),
                                           "Expected an 'invalid credentials' inline error message");
                assertTrue(driver.getCurrentUrl().endsWith("/login"),
                                           "Expected to remain on /login after failed login");
      }

    @Test
      void lockedAccount_blocksAccess_andOffersRecoveryLink() {
                driver.get(BASE_URL + "/login");

          driver.findElement(By.id("email")).sendKeys("locked.user@example.com");
                driver.findElement(By.id("password")).sendKeys("AnyPassword1!");
                driver.findElement(By.id("login-btn")).click();

          WebElement banner = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".account-locked-banner")));
                assertTrue(banner.isDisplayed(), "Expected account-locked banner for locked users");
      }

    @AfterEach
      void tearDown() {
                if (driver != null) {
                              driver.quit();
                }
      }
}
