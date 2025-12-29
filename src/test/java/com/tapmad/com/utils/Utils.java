package com.tapmad.com.utils;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class Utils {

    private static final int DEFAULT_WAIT = 20;

    /** Screenshot attachment for Allure */
    @Attachment(value = "Step Screenshot", type = "image/png")
    public static byte[] takeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Step("Wait until element {element} is visible")
    public static void waitForVisibility(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT))
                .until(ExpectedConditions.visibilityOf(element));
        takeScreenshot(driver);
    }

    @Step("Wait until element {element} is clickable")
    public static void waitForClickable(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT))
                .until(ExpectedConditions.elementToBeClickable(element));
        takeScreenshot(driver);
    }

    @Step("Click on element {element}")
    public static void click(WebDriver driver, WebElement element) {
        waitForClickable(driver, element);
        element.click();
        takeScreenshot(driver);
    }

    @Step("Get text from element {element}")
    public static String getText(WebDriver driver, WebElement element) {
        waitForVisibility(driver, element);
        String text = element.getText();
        takeScreenshot(driver);
        return text;
    }

    @Step("Scroll to bottom of the page")
    public static void scrollToBottom(WebDriver driver, JavascriptExecutor js) {
        long lastHeight = (Long) js.executeScript("return document.body.scrollHeight");
        while (true) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            waitForPageHeightChange(driver, js, lastHeight);
            long newHeight = (Long) js.executeScript("return document.body.scrollHeight");
            if (newHeight == lastHeight) break;
            lastHeight = newHeight;
        }
        takeScreenshot(driver);
    }

    @Step("Scroll to top of the page")
    public static void scrollToTop(JavascriptExecutor js) {
        js.executeScript("window.scrollTo(0, 0);");
    }

    @Step("Switch to newly opened browser tab")
    public static void switchToNewTab(WebDriver driver) {
        Set<String> handles = driver.getWindowHandles();
        List<String> tabs = handles.stream().toList();
        driver.switchTo().window(tabs.get(tabs.size() - 1));
        takeScreenshot(driver);
    }

    /** Helper: wait until page height changes (used in scrolling) */
    private static void waitForPageHeightChange(WebDriver driver, JavascriptExecutor js, long oldHeight) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(d -> (Long) js.executeScript("return document.body.scrollHeight") > oldHeight);
    }
}
