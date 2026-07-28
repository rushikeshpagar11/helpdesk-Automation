package com.qa.helpdesk.pages;

import com.microsoft.playwright.Page;

public class AgentDashboardPage {

    private Page page;

    private String agentDashboardTitle = "//h1[normalize-space()='Help Desk Dashboard']";
    private String newBtn = "//span[text()='New']";
    private String newTicketBtn ="//button[normalize-space()='New Ticket']";

    public AgentDashboardPage(Page page){
        this.page = page;
    }

    public String getAgentDashboardTitle(){
        String title = page.textContent(agentDashboardTitle);
        System.out.println("page title is : "+ title);
        return title;
    }

    public void clickOnNewBtn(){
        page.locator(newBtn).click();
    }

    public AgentDashboardPage createNewTkt(){
        clickOnNewBtn();
        page.locator(newTicketBtn).click();
        page.pause();
        return new AgentDashboardPage(page);
    }

}
