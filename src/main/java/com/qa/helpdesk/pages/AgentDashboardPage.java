package com.qa.helpdesk.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

import java.nio.file.Paths;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AgentDashboardPage {

    private Page page;

    private String agentDashboardTitle = "//h1[normalize-space()='Help Desk Dashboard']";
    private String newBtn = "//span[text()='New']";
    private String createTicketBtn = "//button[normalize-space()='Create Ticket']";
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
    private String knowledgeableTitle = "tbody tr:nth-child(1) td:nth-child(2) div:nth-child(1) div:nth-child(1) div:nth-child(1)";
    private String taskTitle = "tbody tr:nth-child(1) td:nth-child(3) div:nth-child(1)";
    private String deleteUserCheckBox = "//tbody/tr[1]/td[1]//div[contains(@class,'w-5') and contains(@class,'h-5') and contains(@class,'cursor-pointer')]";


    public AgentDashboardPage(Page page) {
        this.page = page;
    }

    public String getAgentDashboardTitle() {
        String title = page.textContent(agentDashboardTitle);
        System.out.println("page title is : " + title);
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

    public AgentDashboardPage selectDepartment() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select a Department")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Support")).first().click();
        return new AgentDashboardPage(page);
    }

    public AgentDashboardPage selectType() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Type")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Incident")).click();
        return new AgentDashboardPage(page);
    }

    public AgentDashboardPage selectPriority() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Priority")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Low")).click();
        return new AgentDashboardPage(page);
    }

    public AgentDashboardPage enterSubject(String subject) {
        page.fill(enterSubject, subject);
        return new AgentDashboardPage(page);
    }

    public AgentDashboardPage enterDescription(String description) {
        page.fill(enterDescription, description);
        return new AgentDashboardPage(page);
    }

    public AgentDashboardPage clickCreateBtn() {
        page.locator(createTicketBtn).click();
        return new AgentDashboardPage(page);
    }

    public AgentDashboardPage clickViewBtn() {
        page.locator(viewBtn).click();
        return new AgentDashboardPage(page);
    }

    public String getTicketTitle() {
        String title = page.textContent(ticketTitle);
        System.out.println("Ticket title is : " + title);
        return title;
    }

    public AdminPage clickSwitchToAdminBtn() {
        page.locator(profileIcon).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Switch to Admin")).click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        return new AdminPage(page);
    }

    public AgentDashboardPage createNewUser(String firstname, String lastname, String mobileNumber, String email) {
        clickOnNewBtn();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add User")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter First Name..."))
                .pressSequentially(firstname);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Last Name..."))
                .pressSequentially(lastname);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Organization")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Ampcus Tech QA").setExact(true)).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Mobile Number"))
                .pressSequentially(mobileNumber);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Email Address"))
                .pressSequentially(email);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create User")).click();
        return new AgentDashboardPage(page);
    }

    public String getUserName() {
        String user_Name = page.textContent(userName);
        System.out.println("UserName is : " + user_Name);
        return user_Name;
    }

    public AgentDashboardPage createNewMacro(String macroTitle, String macroDescription) {
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

    public String getMacroTitle() {
        String macro_Title = page.textContent(macroTitles);
        System.out.println("Macro title is : " + macro_Title);
        return macro_Title;
    }

    public AgentDashboardPage createNewKnowledgeable(String title, String description) {
        clickOnNewBtn();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("New Knowledgeable")).click();
        page.locator(".flex.items-start.gap-2.p-2.border.rounded-\\[6px\\].cursor-pointer.transition-all.bg-\\[\\#FAFAFA\\]").click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter article title")).fill(title);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Category")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("version")).click();
        page.locator(macroDescriptionn).fill(description);
        Locator fileInput = page.locator("//input[@id='file-upload']");
        fileInput.setInputFiles(Paths.get("src/testData/KB4_Helpdesk_KB (1).pdf"));
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Article")).click();
        return new AgentDashboardPage(page);
    }


    public AgentDashboardPage addNewTask(String taskTitle, String taskDescription) {
        clickOnNewBtn();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Task")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter a clear and specific"))
                .pressSequentially(taskTitle);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Department")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("IT Department")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select a Priority")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Critical")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Status")).click();

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open").setExact(true)).click();

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select due Date")).click();
        page.getByText("6").nth(2).click();
        page.locator(enterDescription).pressSequentially(taskDescription);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Task")).click();
        page.waitForTimeout(3000);
        return new AgentDashboardPage(page);
    }

    public String getKnowledgeableTitle() {
        page.getByRole(AriaRole.COMPLEMENTARY).getByText("Knowledge Base").click();
        page.waitForTimeout(1000);
        String knowledgeable_Title = page.textContent(knowledgeableTitle);
        System.out.println("Knowledgeable title is : " + knowledgeable_Title);
        return knowledgeable_Title;
    }

    public String getTaskTitle() {
        page.getByRole(AriaRole.COMPLEMENTARY).getByText("Task", new Locator.GetByTextOptions().setExact(true))
                .click();
        String task_Title = page.textContent(taskTitle);
        System.out.println("task title is : " + task_Title);
        return task_Title;
    }

    public AgentDashboardPage clickOnFirstUser() {
        page.getByText("Users").click();
        page.locator(userName).click();
        return new AgentDashboardPage(page);
    }

    public void verifyTextBoxValue(Page page, String fieldName, String expectedValue) {
        assertThat(
                page.getByRole(AriaRole.TEXTBOX,
                        new Page.GetByRoleOptions().setName(fieldName))
        ).hasValue(expectedValue);
    }

    public AgentDashboardPage searchAndDeleteUser(String email) {

        page.getByRole(
                AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Search Users...")
        ).fill(email);

        page.waitForTimeout(1000);

        // Click/check the actual checkbox
        page.locator(deleteUserCheckBox).click();

        page.getByRole(AriaRole.BUTTON)
                .filter(new Locator.FilterOptions()
                        .setHasText(Pattern.compile("^$")))
                .nth(3)
                .click();

        page.waitForTimeout(1000);

        page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Yes, Delete")
        ).click();

        return new AgentDashboardPage(page);
    }

    public AgentDashboardPage updateUser(String firstname, String lastname) {
        // 1. Target the First Name field, clear it, and type sequentionally
        Locator firstNameInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter First Name..."));
        firstNameInput.click();
        firstNameInput.press("Control+A"); // Mac users may need "Meta+A" if running locally on macOS
        firstNameInput.press("Backspace");
        firstNameInput.pressSequentially(firstname);

        // 2. Target the Last Name field, clear it, and type sequentionally
        Locator lastNameInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Last Name..."));
        lastNameInput.click();
        lastNameInput.press("Control+A");
        lastNameInput.press("Backspace");
        lastNameInput.pressSequentially(lastname);

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Update User")).click();
        page.waitForTimeout(2000);
        return new AgentDashboardPage(page);
    }


}
