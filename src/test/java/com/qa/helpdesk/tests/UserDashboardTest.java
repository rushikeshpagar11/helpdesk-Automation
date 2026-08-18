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

    @Test
    public void arcLinkTabTest() {
        loginPage.userLogin(prop.getProperty("username2"), prop.getProperty("password"));
        homePage.clickOnArcLinkButton();
        Assert.assertTrue(homePage.isDownloadForWindowsBtn(),"Download for windows button not visible");
        Assert.assertTrue(homePage.isDownloadForMacOSBtn(),"Download for MAC OS button not visible");
    }

    @Test
    public void createSignatureTest() {
        loginPage.userLogin(prop.getProperty("username2"), prop.getProperty("password"));
        String signatureTitle = faker.lorem().sentence();
        String signatureDescription = faker.lorem().paragraph();

        homePage.clickOnProfile().clickOnMyProfileBtn().clickSignatureBtn()
                        .clickAddNewSignatureBtn()
                        .addTitleAndDescriptionSignature(signatureTitle,signatureDescription)
                        .clickAddSignatureBtn();
        Assert.assertEquals(homePage.getSignatureTitle(),signatureTitle,"Signature is not created");
        homePage.deleteSignatureBtn();
    }

}
