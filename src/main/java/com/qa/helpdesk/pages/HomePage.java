package com.qa.helpdesk.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class HomePage {

    private Page page;

    private String createTicket = "//div[contains(@class,'hidden lg:flex f')]";
    private String ticketTitle ="div[class='text-[14px] font-medium text-heading truncate cursor-pointer block w-full']";
    private String ticketTitleOnStandalonePage = "h1[class='text-[24px] font-semibold leading-[32px] tracking-[0em] text-label break-all whitespace-pre-wrap']";
    private String viewAllTicketsCard = "//h3[text()='View All Tickets']";
    private String helpArticleCard = "//h3[text()='Help Articles']";
    private String FAQCard = "//h3[text()='FAQ']";
    private String downloadForWindowsBtn = "(//span[normalize-space()='Download for Windows'])[1]";
    private String downloadForMacOSBtn = "(//span[normalize-space()='Download for macOS'])[1]";


    public HomePage(Page page) {
        this.page = page;
    }

    public String getHomePageTitle() {
        String title = page.title();
        System.out.println("page title is : " + title);
        return title;
    }

    public boolean isCreateTicketBtnVisible() {
        page.locator(createTicket).waitFor();
        return page.isVisible(createTicket);
    }

    public HomePage createTicket(String subject,String Description){
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Ticket")).first().click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select a Department")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Support")).first().click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Type")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Issue")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Priority")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Low")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Brief description of your"))
                .pressSequentially(subject);
        page.locator(".ql-editor").pressSequentially(Description);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Ticket")).nth(1).click();
        return new HomePage(page);
    }

    public String getTicketTitle() {
        page.waitForTimeout(1000);
        String ticket_title = page.textContent(ticketTitle);
        System.out.println("Ticket title is : " + ticket_title);
        return ticket_title;
    }

    public HomePage createAndViewTicket(String subject,String Description){
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Ticket")).first().click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select a Department")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Support")).first().click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Type")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Issue")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Priority")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Low")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Brief description of your"))
                .pressSequentially(subject);
        page.locator(".ql-editor").pressSequentially(Description);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Ticket")).nth(1).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("View Ticket")).click();
        return new HomePage(page);
    }

    public String getTicketTitleOnStandalonePage() {
        page.waitForTimeout(1000);
        String ticket_title = page.textContent(ticketTitleOnStandalonePage);
        System.out.println("ticket title is : " + ticket_title);
        return ticket_title;
    }

    public boolean isViewAllTicketsCardVisible() {
        page.waitForTimeout(1000);
        return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("View All Tickets")).isVisible();
    }

    public boolean isHelpArticleCardVisible() {
        page.waitForTimeout(1000);
        return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Help Articles")).isVisible();
    }

    public boolean isFAQCardVisible() {
        page.waitForTimeout(1000);
        return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("FAQ")).isVisible();
    }

    public HomePage clickOnArcLinkButton(){
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("ArcLink Logo ArcLink")).click();
        return new HomePage(page);
    }

    public boolean isDownloadForWindowsBtn() {
        page.waitForTimeout(1000);
        return page.locator(downloadForWindowsBtn).isVisible();
    }

    public boolean isDownloadForMacOSBtn() {
        page.waitForTimeout(1000);
        return page.locator(downloadForMacOSBtn).isVisible();
    }
}
