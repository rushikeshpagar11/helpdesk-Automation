package com.qa.helpdesk.base;

import com.microsoft.playwright.Page;
import com.qa.helpdesk.factory.PlaywrightFactory;
import com.qa.helpdesk.listners.TestListener;
import com.qa.helpdesk.pages.AdminPage;
import com.qa.helpdesk.pages.AgentDashboardPage;
import com.qa.helpdesk.pages.HomePage;
import com.qa.helpdesk.pages.LoginPage;
import net.datafaker.Faker;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

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

    @BeforeTest
    public void setup() {
        playwrightFactory = new PlaywrightFactory();
        prop = playwrightFactory.init_prop();
        page = playwrightFactory.initBrowser(prop);
        loginPage = new LoginPage(page);
        agentDashboardPage = new AgentDashboardPage(page);
        faker = new Faker();
        adminPage = new AdminPage(page);
        homePage = new HomePage(page);
    }

    @AfterTest
    public void tearDown() {
        if (page != null) {
            page.context().browser().close();
        }
    }

    public Page getPage() {
        return this.page;
    }
}