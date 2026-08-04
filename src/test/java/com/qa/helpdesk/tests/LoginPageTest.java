package com.qa.helpdesk.tests;

import com.qa.helpdesk.base.BaseTest;
import com.qa.helpdesk.constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

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
        loginPage.userLogin(prop.getProperty("username"), prop.getProperty("password"));
        Assert.assertTrue(homePage.isCreateTicketBtnVisible(), "user is not logged in");

    }

    @Test
    public void agentLoginTest() {
        agentDashboardPage = loginPage.agentLogin(prop.getProperty("username"), prop.getProperty("password"));
        String title = agentDashboardPage.getAgentDashboardTitle();
        Assert.assertEquals(title, AppConstants.AGENT_DASHBOARD_TITLE);
    }

}