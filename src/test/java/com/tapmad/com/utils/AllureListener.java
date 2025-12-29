package com.tapmad.com.utils;

import com.tapmad.com.base.Base;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class AllureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        attachScreenshot();
    }

    @Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] attachScreenshot() {
        return ((TakesScreenshot) Base.getDriver())
                .getScreenshotAs(OutputType.BYTES);
    }
}
