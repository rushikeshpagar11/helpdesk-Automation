package com.qa.helpdesk.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class AgentDashboardPage {

    private Page page;

    private String agentDashboardTitle = "//h1[normalize-space()='Help Desk Dashboard']";
    private String newBtn = "//span[text()='New']";
    private String createTicketBtn ="//button[normalize-space()='Create Ticket']";
    private String enterSubject = "//input[@placeholder='Brief description of your issue']";
    private String enterDescription = "//div[@class='ql-editor ql-blank']";
    private String viewBtn = "//button[normalize-space()='View Ticket']";
    private String ticketTitle = "//h1[contains(@class,'text-label')]";


    public AgentDashboardPage(Page page){
        this.page = page;
    }

    public String getAgentDashboardTitle(){
        String title = page.textContent(agentDashboardTitle);
        System.out.println("page title is : "+ title);
        return title;
    }

    public void clickOnNewBtn() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("library_add New")).click();
    }

    public AgentDashboardPage createNewTktBtn() {
        clickOnNewBtn();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("New Ticket")).click();

        return new AgentDashboardPage(page);
    }

    public AgentDashboardPage selectDepartment(){
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select a Department")).click();
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("IT Department")).click();
    return new AgentDashboardPage(page);
    }

    public AgentDashboardPage selectType(){
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Type")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Issue")).click();
        return new AgentDashboardPage(page);
    }

    public AgentDashboardPage selectPriority(){
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Priority")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Low")).click();
        return new AgentDashboardPage(page);
    }

    public AgentDashboardPage enterSubject(String subject){
        page.fill(enterSubject, subject);
        return new AgentDashboardPage(page);
    }

    public AgentDashboardPage enterDescription(String description){
        page.fill(enterDescription, description);
        return new AgentDashboardPage(page);
    }

    public AgentDashboardPage clickCreateBtn(){
        page.locator(createTicketBtn).click();
        return new AgentDashboardPage(page);
    }

    public AgentDashboardPage clickViewBtn(){
        page.locator(viewBtn).click();
        return new AgentDashboardPage(page);
    }

    public String getTicketTitle(){
        String title = page.textContent(ticketTitle);
        System.out.println("Ticket title is : "+ title);
        return title;
    }



}
