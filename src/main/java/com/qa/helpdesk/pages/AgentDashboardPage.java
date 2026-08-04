package com.qa.helpdesk.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;

public class AgentDashboardPage {

    private Page page;

    private String agentDashboardTitle = "//h1[normalize-space()='Help Desk Dashboard']";
    private String newBtn = "//span[text()='New']";
    private String createTicketBtn ="//button[normalize-space()='Create Ticket']";
    private String enterSubject = "//input[@placeholder='Brief description of your issue']";
    private String enterDescription = "//div[@class='ql-editor ql-blank']";
    private String viewBtn = "//button[normalize-space()='View Ticket']";
    private String ticketTitle = "//h1[contains(@class,'text-label')]";
    private String profileIcon = "//img[@alt='User']";
    private String switchToAdminBtn = "//span[text()='Switch to Admin']";
    private String macroDescriptionn = ".ql-editor";
    private String test = "select[class='w-full h-[45px] border border-border-default rounded-md px-4 text-[14px] appearance-none focus:outline-none focus:border-primary transition-all bg-white text-placeholder']";
    private String macroTitles = "tbody tr:nth-child(1) td:nth-child(2) div:nth-child(1) div:nth-child(1) span:nth-child(1)";
    private String userName = "tbody tr:nth-child(1) td:nth-child(2) div:nth-child(1) div:nth-child(1) a:nth-child(1)";


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
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Support")).first().click();
    return new AgentDashboardPage(page);
    }

    public AgentDashboardPage selectType(){
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Type")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Incident")).click();
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

    public AdminPage clickSwitchToAdminBtn() {
        page.locator(profileIcon).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Switch to Admin")).click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        return new AdminPage(page);
    }

    public AgentDashboardPage createNewUser(String firstname,String lastname,String mobileNumber,String email) {
        clickOnNewBtn();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add User")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter First Name..."))
                .pressSequentially(firstname);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Last Name..."))
                        .pressSequentially(lastname);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Organisation")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Bravens Inc.")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Mobile Number"))
                        .pressSequentially(mobileNumber);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Email Address"))
                        .pressSequentially(email);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create User")).click();
        return new AgentDashboardPage(page);
    }

    public String getUserName(){
        String user_Name = page.textContent(userName);
        System.out.println("UserName is : "+ user_Name);
        return user_Name;
    }

    public AgentDashboardPage createNewMacro(String macroTitle,String macroDescription) {
        clickOnNewBtn();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("New Macro")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter a title for the macro"))
                .fill(macroTitle);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Category")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open").setExact(true)).click();
        page.locator(macroDescriptionn).fill(macroDescription);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Macro")).click();
        return new AgentDashboardPage(page);
    }

    public String getMacroTitle(){
        String macro_Title = page.textContent(macroTitles);
        System.out.println("Macro title is : "+ macro_Title);
        return macro_Title;
    }

    public AgentDashboardPage createNewKnowledgeable(String title,String description) {
        clickOnNewBtn();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("New Knowledgeable")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter article title"))
                        .fill(title);
       // page.locator(test).selectOption("OPEN");
        page.locator("//select").first()
                .selectOption(new SelectOption().setLabel("version"));
        //page.getByRole(AriaRole.COMBOBOX).selectOption("OPEN");
        page.locator(macroDescriptionn).fill(description);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Article")).click();

        //page.pause();

        return new AgentDashboardPage(page);
    }


    public AgentDashboardPage addNewTask() {
        clickOnNewBtn();
        page.pause();


        return new AgentDashboardPage(page);
    }


}
