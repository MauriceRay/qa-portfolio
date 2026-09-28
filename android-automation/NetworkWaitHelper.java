package com.mauriceqa.mobile;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Network-aware wait helper for slow / throttled mobile conditions.
   * Fixes the flaky login regression tracked in issue #1: instead of a fixed
   * 15s blind wait, we wait up to 30s and treat genuine assertion failures as
   * failures (no blanket retry — only infrastructure flake gets retried at the runner level).
   */
public class NetworkWaitHelper {

    private final WebDriverWait wait;

    public NetworkWaitHelper(WebDriver driver) {
              this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    /** Wait for a boolean condition, e.g. an intercepted login network response. */
    public void until(java.util.function.Supplier<Boolean> condition) {
              wait.until(driver -> condition.get());
    }
}
