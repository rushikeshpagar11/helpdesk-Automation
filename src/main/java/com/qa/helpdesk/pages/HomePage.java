package com.qa.helpdesk.pages;

import com.microsoft.playwright.Page;

public class HomePage {

    private Page page;

    private String createTicket = "//div[contains(@class,'hidden lg:flex f')]";

    public HomePage(Page page){
        this.page = page;
    }

    public String getHomePageTitle(){
        String title = page.title();
        System.out.println("page title is : "+ title);
        return title;
    }

    public boolean isCreateTicketBtnVisible() {
        page.locator(createTicket).waitFor();
        return page.isVisible(createTicket);
    }
}
