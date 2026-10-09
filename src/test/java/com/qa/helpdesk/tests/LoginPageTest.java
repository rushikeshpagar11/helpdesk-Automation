package com.qa.helpdesk.tests;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import com.microsoft.playwright.Page;
import com.qa.helpdesk.base.BaseTest;
import com.qa.helpdesk.constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginPageTest extends BaseTest {

    @Test
    public void loginPageTitleTest() {
        String actualTitle = loginPage.getLoginPageTitle();
        Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE, "Page title is incorrect");
    }

    @Test
    public void loginPageURLTest() {
        String actualUrl = loginPage.getLoginPageURL();
        Assert.assertEquals(actualUrl, prop.getProperty("url"), "page url is incorrect");
    }

    @Test
    public void forgetPwdLinkTest() {
        Assert.assertTrue(loginPage.isForgetPwdLinkVisible(), "Forget password link not visible");
    }

    @Test
    public void userLoginTest() {
        // The page action dynamically fetches the real-time CAPTCHA code!
        loginPage.userLogin(prop.getProperty("username1"), prop.getProperty("password"));
        Assert.assertTrue(homePage.isCreateTicketBtnVisible(), "user is not logged in");

    }

    @Test
    public void agentLoginTest() {
        agentDashboardPage = loginPage.agentLogin(prop.getProperty("Agent_username1"), prop.getProperty("password"));
        String title = agentDashboardPage.getAgentDashboardTitle();
        Assert.assertEquals(title, AppConstants.AGENT_DASHBOARD_TITLE);
    }

    @Test
    public void userLoginValidationTest() {
        loginPage.clickLoginBtn();
        Assert.assertEquals(loginPage.getEmailRequiredMsg(), AppConstants.EMAIL_MANDATORY_MSG, "Message incorrect");
        Assert.assertEquals(loginPage.getPasswordRequiredMsg(), AppConstants.PASSWORD_MANDATORY_MSG, "Message incorrect");
        Assert.assertEquals(loginPage.getCaptchaRequiredMsg(), AppConstants.CAPTCHA_MANDATORY_MSG, "Message incorrect");

    }

    @Test
    public void agentLoginValidationTest() {
        loginPage.clickStaffLogin();
        loginPage.clickLoginBtn();
        Assert.assertEquals(loginPage.getEmailRequiredMsg(), AppConstants.EMAIL_MANDATORY_MSG, "Message incorrect");
        Assert.assertEquals(loginPage.getPasswordRequiredMsg(), AppConstants.PASSWORD_MANDATORY_MSG, "Message incorrect");
        Assert.assertEquals(loginPage.getCaptchaRequiredMsg(), AppConstants.CAPTCHA_MANDATORY_MSG, "Message incorrect");

    }

    @Test
    public void forgetPasswordTest() {
        String email = prop.getProperty("username2");
        loginPage.forgetPassword(email);
        Assert.assertEquals(loginPage.passwordResetLinkMsg(), AppConstants.PASSWORD_RESET_LINK_SEND_MSG, "Message incorrect");

        // Capture the new password reset tab
        Page resetPasswordPage = page.context().waitForPage(() -> {
            emailHelper.clickResetPasswordLink(email);
        });

        resetPasswordPage.bringToFront();

        String randomPassword = faker.internet().password(10, 16, true, true, true);


        setPasswordPage.resetPassword(resetPasswordPage, randomPassword);

        loginPage.userLogin(email, randomPassword);
    }


    @Test
    public void verifyLogoVisualWithDataFolder() throws IOException {
        File expectedFile = new File("src/testData/actual_logo.png");
        File actualFile = new File("target/visual-results/actual_logo.png");
        File diffFile = new File("target/visual-results/diff_logo.png");

        Assert.assertTrue(expectedFile.exists(),
                "Missing expected baseline image! Please place 'expected_logo.png' inside the 'data/' directory.");

        loginPage.captureLogoImage(actualFile.getAbsolutePath());

        BufferedImage expectedImg = javax.imageio.ImageIO.read(expectedFile);
        BufferedImage actualImg = javax.imageio.ImageIO.read(actualFile);


        ImageComparison comparer = new ImageComparison(expectedImg, actualImg);

        comparer.setThreshold(10);

        ImageComparisonResult result = comparer.compareImages();

        if (result.getImageComparisonState() != ImageComparisonState.MATCH) {
            ImageComparisonUtil.saveImage(diffFile, result.getResult());
            System.err.println("Visual test failed! Differences highlighted at: " + diffFile.getAbsolutePath());
        }

        Assert.assertEquals(result.getImageComparisonState(), ImageComparisonState.MATCH,
                "Visual layout mismatch against baseline stored in data folder! Review: " + diffFile.getAbsolutePath());
    }
    

}