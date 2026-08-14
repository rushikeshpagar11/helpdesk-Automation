package com.qa.helpdesk.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class HomePage {

    private Page page;

    private String createTicket = "//div[contains(@class,'hidden lg:flex f')]";
    private String ticketTitle ="div[class='text-[14px] font-medium text-heading truncate cursor-pointer block w-full']";

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
        System.out.println("Access Name is : " + ticket_title);
        return ticket_title;
    }
}
