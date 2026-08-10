package com.qa.helpdesk.listners;

import com.microsoft.playwright.Page;
import com.qa.helpdesk.base.BaseTest;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Paths;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        // Retrieve the page instance from the running test class
        Object testClass = result.getInstance();
        Page page = ((BaseTest) testClass).getPage();

        if (page != null) {
            String testName = result.getName();
            String screenshotPath = "src/failureScreenShots/" + testName + "_failed.png";

            // Take the screenshot
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(screenshotPath))
                    .setFullPage(true)); // Optional: captures full scrollable page

            System.out.println("Screenshot saved to: " + screenshotPath);
        }
    }
}

