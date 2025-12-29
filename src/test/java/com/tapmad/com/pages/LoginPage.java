package com.tapmad.com.pages;

import com.tapmad.com.utils.Utils;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // --------------------------
    // LOCATORS
    // --------------------------
    @FindBy(className = "lgndrp")
    WebElement menu;

    @FindBy(className = "pcolor")
    WebElement dropdownItem;

    @FindBy(xpath = "//input[@type='tel' and @placeholder='Mobile number']")
    WebElement mobileInput;

    @FindBy(xpath = "//input[@type='text' and @inputmode='numeric']")
    WebElement pinInput;

    @FindBy(xpath = "//button[normalize-space()='Login Now']")
    WebElement loginBtn;

    @FindBy(className = "close")
    WebElement popupCloseBtn;

    @FindBy(xpath = "//div[contains(@class,'swal-text')]")
    WebElement PopupText;

    // --------------------------
    // PAGE ACTIONS
    // --------------------------
    @Step("Open login menu")
    public void openMenu() {
        Utils.click(driver, menu);
        new Actions(driver).moveToElement(menu).perform();
        Utils.takeScreenshot(driver);
    }

    @Step("Select Login option from dropdown")
    public void selectDropdownItem() {
        Utils.click(driver, dropdownItem);
    }
    @Step("Login with mobile number: {mobile}, pin:{pin}")

    public void login(String mobile, String pin) {
        mobileInput.sendKeys(mobile);
        pinInput.sendKeys(pin);
        Utils.takeScreenshot(driver);
        Utils.click(driver, loginBtn);
    }

    @Step("Verify login success popup")
    public String getLoginSuccessPopupText() {
        wait.until(ExpectedConditions.visibilityOf(PopupText));
        return PopupText.getText();
    }

    @Step("Close login popup")
    public void closePopup() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(popupCloseBtn));
            popupCloseBtn.click();
        } catch (Exception e) {
            System.out.println("No popup appeared.");
        }
    }
    @Step("Scroll page up and down")

    public void scrollPage() {
        Utils.scrollToBottom(driver, js);
        Utils.scrollToTop(js);
    }

    @Step("Open Sports tab in new browser tab")
    public void openSportsTab() {
        WebElement sportsTab = driver.findElement(
                By.xpath("/html/body/div/main/header/div/nav/a[2]"));
        new Actions(driver)
                .keyDown(Keys.CONTROL)
                .click(sportsTab)
                .keyUp(Keys.CONTROL)
                .perform();
        Utils.switchToNewTab(driver);
    }
}
