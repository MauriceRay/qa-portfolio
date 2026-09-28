package com.mauriceqa.mobile;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Representative Appium (UiAutomator2) regression test for an Android app login flow.
   * Pattern used on real mobile products: explicit waits, locators by accessibility/id,
 * and assertions on both UI state and persisted session state.
   */
public class AndroidLoginTest {

    private AndroidDriver driver;
      private WebDriverWait wait;
      private static final String APPIUM_SERVER = "http://127.0.0.1:4723";

    @BeforeEach
      void setUp() throws MalformedURLException {
                UiAutomator2Options options = new UiAutomator2Options()
                                  .setPlatformName("Android")
                                  .setDeviceName("emulator-5554")
                                  .setAppPackage("com.example.app")
                                  .setAppActivity("com.example.app.MainActivity")
                                  .setNoReset(false);
                driver = new AndroidDriver(new URL(APPIUM_SERVER), options);
                wait = new WebDriverWait(driver, Duration.ofSeconds(15));
      }

    @Test
      void validCredentials_opensHomeScreen() {
                WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                  By.id("com.example.app:id/email")));
                email.sendKeys("qa.user@example.com");

          driver.findElement(By.id("com.example.app:id/password")).sendKeys("SecurePass123!");
                driver.findElement(By.id("com.example.app:id/login_button")).click();

          WebElement home = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.id("com.example.app:id/home_greeting")));
                assertTrue(home.isDisplayed(), "Home greeting should appear after successful login");
      }

    @Test
      void invalidCredentials_showsInlineError() {
                driver.findElement(By.id("com.example.app:id/email")).sendKeys("qa.user@example.com");
                driver.findElement(By.id("com.example.app:id/password")).sendKeys("wrong-password");
                driver.findElement(By.id("com.example.app:id/login_button")).click();

          WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.id("com.example.app:id/error_message")));
                assertTrue(error.getText().toLowerCase().contains("invalid"),
                                           "Expected an invalid-credentials error on screen");
      }

    @AfterEach
      void tearDown() {
                if (driver != null) {
                              driver.quit();
                }
      }
}
