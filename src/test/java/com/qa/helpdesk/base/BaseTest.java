package com.qa.helpdesk.base;

import com.microsoft.playwright.Page;
import com.qa.helpdesk.factory.PlaywrightFactory;
import com.qa.helpdesk.listners.TestListener;
import com.qa.helpdesk.pages.*;
import net.datafaker.Faker;
import org.testng.annotations.*;

import java.util.Locale;
import java.util.Properties;

@Listeners(TestListener.class)
public class BaseTest {

    protected PlaywrightFactory playwrightFactory;
    protected Page page;
    protected Properties prop;
    protected LoginPage loginPage;
    protected HomePage homePage;
    protected AgentDashboardPage agentDashboardPage;
    protected Faker faker;
    protected AdminPage adminPage;
    protected EmailHelper emailHelper;
    protected SetPasswordPage setPasswordPage;

    @BeforeMethod
    public void setup() {
        playwrightFactory = new PlaywrightFactory();
        prop = playwrightFactory.init_prop();
        page = playwrightFactory.initBrowser(prop);
        loginPage = new LoginPage(page);
        agentDashboardPage = new AgentDashboardPage(page);
        faker = new Faker(new Locale("en", "US"));
        adminPage = new AdminPage(page);
        homePage = new HomePage(page);
        emailHelper = new EmailHelper(page);
        setPasswordPage = new SetPasswordPage(page);
    }

    @AfterMethod
    public void tearDown() {
        if (page != null) {
            page.context().browser().close();
        }
    }

    public Page getPage() {
        return this.page;
    }
}