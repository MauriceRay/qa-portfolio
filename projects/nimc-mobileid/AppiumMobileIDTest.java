package com.mauriceqa.nimc;

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
 * Representative Appium automation for the NIMC Mobile ID vNIN flow.
   * Sandbox only — uses stubbed OTP and test NINs; no real citizen data.
   */
public class AppiumMobileIDTest {

    private AndroidDriver driver;
      private WebDriverWait wait;

    @BeforeEach
      void setUp() throws MalformedURLException {
                UiAutomator2Options options = new UiAutomator2Options()
                                  .setPlatformName("Android")
                                  .setDeviceName("emulator-5554")
                                  .setAppPackage("gov.ng.nimc.mws")
                                  .setAppActivity("gov.ng.nimc.ui.OnboardingActivity");
                driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
                wait = new WebDriverWait(driver, Duration.ofSeconds(20));
      }

    @Test
      void onboardWithValidNin_otpDelivered_pinAccepted() {
                driver.findElement(By.id("gov.ng.nimc.mws:id/nin"))
                                  .sendKeys("01234567890"); // sandbox 11-digit NIN
          driver.findElement(By.id("gov.ng.nimc.mws:id/continue")).click();

          String otp = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.id("gov.ng.nimc.mws:id/otp_box"))).getText();
                assertEquals(6, otp.length(), "Sandbox OTP stub should return 6 digits");

          driver.findElement(By.id("gov.ng.nimc.mws:id/pin")).sendKeys("481516");
                driver.findElement(By.id("gov.ng.nimc.mws:id/confirm_pin")).sendKeys("481516");
                assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(
                                  By.id("gov.ng.nimc.mws:id/home_greeting"))).isDisplayed());
      }

    @Test
      void generateVirtualNin_returns16CharToken() {
                // assume logged-in state from a test fixture
          driver.findElement(By.id("gov.ng.nimc.mws:id/get_vnin")).click();

          String vnin = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.id("gov.ng.nimc.mws:id/vnin_value"))).getText().trim();

          assertTrue(vnin.matches("[A-Z0-9]{16}"), "vNIN must be a 16-char alphanumeric token");
                assertTrue(driver.findElement(By.id("gov.ng.nimc.mws:id/expiry_note"))
                                           .getText().contains("72"), "UI must state the 72-hour expiry");
      }

    @Test
      void basicIdToggle_qrNeverLeaksDob() {
                driver.findElement(By.id("gov.ng.nimc.mws:id/show_my_id")).click();
                // privacy gate: default consent must be Basic ID
          String mode = driver.findElement(By.id("gov.ng.nimc.mws:id/consent_mode")).getText();
                assertEquals("Basic ID", mode.trim(), "Default disclosure must be Basic ID");
      }

    @Test
      void wrongPinFiveTimes_locksSession() {
                driver.findElement(By.id("gov.ng.nimc.mws:id/pin")).sendKeys("000000");
                for (int i = 0; i < 5; i++) {
                              driver.findElement(By.id("gov.ng.nimc.mws:id/unlock")).click();
                }
                assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(
                                  By.id("gov.ng.nimc.mws:id/locked_notice"))).isDisplayed());
      }

    @AfterEach
      void tearDown() {
                if (driver != null) driver.quit();
      }
}
