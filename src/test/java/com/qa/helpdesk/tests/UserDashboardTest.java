package com.qa.helpdesk.tests;

import com.qa.helpdesk.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserDashboardTest extends BaseTest {

    @Test
    public void createTicketTest() {
        loginPage.userLogin(prop.getProperty("username2"), prop.getProperty("password"));
        String subject = faker.lorem().sentence();
        String description = faker.lorem().paragraph();
        homePage.createTicket(subject, description);
        Assert.assertEquals(homePage.getTicketTitle(), subject, "Ticket is not created");
    }

    @Test
    public void createAndViewTicketTest() {
        loginPage.userLogin(prop.getProperty("username2"), prop.getProperty("password"));
        String subject = faker.lorem().sentence();
        String description = faker.lorem().paragraph();
        homePage.createAndViewTicket(subject, description);
        Assert.assertEquals(homePage.getTicketTitleOnStandalonePage(), subject, "Ticket is not created");
    }

    @Test
    public void viewCardOnHomePageTest() {
        loginPage.userLogin(prop.getProperty("username2"), prop.getProperty("password"));
        Assert.assertTrue(homePage.isViewAllTicketsCardVisible(),"View All Tickets card is not visible");
        Assert.assertTrue(homePage.isHelpArticleCardVisible(),"Help Article card is not visible");
        Assert.assertTrue(homePage.isFAQCardVisible(),"FAQ card is not visible");
    }
}
