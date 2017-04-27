/*
package com.example.airuser.soyf10;

import java.net.MalformedURLException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestProj_MobileEmulator {

    WebDriver driver;

    @Before
    public void setUp() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
// Created object of DesiredCapabilities class.

        capabilities.setCapability("deviceName", "Android Emulator");
// Set android deviceName desired capability. Set your device name.

        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
// Set BROWSER_NAME desired capability. As we are running on Android Emulator, It's Android.

        capabilities.setCapability(CapabilityType.VERSION, "7.0");
// Set Android's OS VERSION @ desired capability.

        capabilities.setCapability("platformName", "Android");
// Set platformName desired capability to Android.

        capabilities.setCapability("appPackage", "com.example.airuser.soyf10");
// Set your application's package.

        capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");
// Set your application's MainActivity.

        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
// Create driver, set the Appium Server details.
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    @Test
    public void testFirstCalculator() {

        driver.findElement(By.name("4")).click(); // Click on Number 4
        driver.findElement(By.name("+")).click(); // Click on Number +
        driver.findElement(By.name("7")).click(); // Click on Number 7
        driver.findElement(By.name("=")).click(); // Click on Number 11

        driver.manage().timeouts().implicitlyWait(25,TimeUnit.SECONDS);
    }

    @After
    public void End() {
        driver.quit();
    }
}

}
*/