package com.mauriceqa.padiworks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Representative Selenium/WebDriver (Java) automation for Padiworks.
   * Covers the two highest-value flows: RBAC login landing, and the
   * leave request -> manager approval -> balance update chain.
   */
public class PadiworksWebTest {

    private WebDriver driver;
      private WebDriverWait wait;
      private static final String BASE = "https://staging.padiworks.example.com";

    @BeforeEach
      void setUp() {
                driver = new ChromeDriver();
                wait = new WebDriverWait(driver, Duration.ofSeconds(15));
      }

    @Test
      void employeeLogin_landsOnDashboard_notAdmin() {
                driver.get(BASE + "/login");
                driver.findElement(By.id("email")).sendKeys("employee@padiworks.test");
                driver.findElement(By.id("password")).sendKeys("EmployeePass1!");
                driver.findElement(By.id("sign-in")).click();

          wait.until(ExpectedConditions.urlContains("/dashboard"));
                // employee must never see admin nav
          assertTrue(driver.findElements(By.id("admin-settings")).isEmpty(),
                                     "Employee must not see Admin settings");
      }

    @Test
      void employeeCannotOpenAdminRoute_byDirectUrl() {
                // log in as employee first
          driver.get(BASE + "/login");
                driver.findElement(By.id("email")).sendKeys("employee@padiworks.test");
                driver.findElement(By.id("password")).sendKeys("EmployeePass1!");
                driver.findElement(By.id("sign-in")).click();
                wait.until(ExpectedConditions.urlContains("/dashboard"));

          driver.get(BASE + "/admin/users");
                // RBAC gate should bounce them away, never render the user table
          assertTrue(driver.findElements(By.id("users-table")).isEmpty(),
                                     "Direct admin URL must not expose the users table");
                assertTrue(driver.getCurrentUrl().contains("/dashboard") ||
                                              driver.getCurrentUrl().contains("/403"));
      }

    @Test
      void leaveRequest_approval_decrementsBalance() {
                // employee submits 1-day leave
          driver.get(BASE + "/login");
                driver.findElement(By.id("email")).sendKeys("employee@padiworks.test");
                driver.findElement(By.id("password")).sendKeys("EmployeePass1!");
                driver.findElement(By.id("sign-in")).click();

          driver.findElement(By.id("new-leave")).click();
                driver.findElement(By.id("leave-type")).sendKeys("Annual");
                driver.findElement(By.id("submit-leave")).click();

          // manager approves
          driver.get(BASE + "/logout");
                driver.findElement(By.id("email")).sendKeys("manager@padiworks.test");
                driver.findElement(By.id("password")).sendKeys("ManagerPass1!");
                driver.findElement(By.id("sign-in")).click();

          wait.until(ExpectedConditions.elementToBeClickable(By.id("approve-leave"))).click();
                WebElement badge = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                  By.id("leave-balance")));

          int balance = Integer.parseInt(badge.getText().replaceAll("\\D+", ""));
                assertEquals(19, balance, "Balance should drop by exactly 1 from 20");
      }

    @AfterEach
      void tearDown() {
                if (driver != null) driver.quit();
      }
}
