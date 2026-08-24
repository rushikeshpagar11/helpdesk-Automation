package com.qa.helpdesk.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

import java.util.regex.Pattern;

public class AdminPage {
    private Page page;
    private String emailBtn = "//span[normalize-space()='Email']";
    private String userName = "tbody tr:nth-child(1) td:nth-child(2) div:nth-child(1) div:nth-child(1) a:nth-child(1)";
    private String departmentName = "tbody tr:nth-child(1) td:nth-child(2) div:nth-child(1) div:nth-child(1)";


    public AdminPage(Page page) {
        this.page = page;
    }

    public boolean isEmailTabVisible() {
        page.waitForLoadState(LoadState.NETWORKIDLE);
        return page.waitForSelector(emailBtn).isVisible();
    }

    public AdminPage clickOnEmail() {
        page.waitForSelector(emailBtn).click();
        return new AdminPage(page);
    }

    public void clickOnNewBtn() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("library_add New")).click();
    }


    public AdminPage createNewAgent(String firstname, String lastname, String mobileNumber, String email) {
        clickOnNewBtn();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("New Agent")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter First Name..."))
                .pressSequentially(firstname);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Last Name..."))
                .pressSequentially(lastname);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Add Organization..."))
                .click();
        page.getByText("Ampcus Tech", new Page.GetByTextOptions().setExact(true)).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("e.g. Agent/Admin"))
                .pressSequentially("Agent");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Add Department...")).click();
        page.getByText("FINANCE", new Page.GetByTextOptions().setExact(true)).click();
        page.getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText(Pattern.compile("^$"))).nth(4)
                .click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Mobile Number"))
                .pressSequentially(mobileNumber);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Email Address"))
                .pressSequentially(email);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Agent")).click();
        return new AdminPage(page);
    }

    public String getUserName() {
        String user_Name = page.textContent(userName);
        System.out.println("UserName is : " + user_Name);
        return user_Name;
    }

    public AdminPage createNewDepartment(String departmentName) {
        clickOnNewBtn();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("New Department")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Department Name..."))
                .pressSequentially(departmentName);
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("System Default")).first().click();
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("System Default")).nth(1).click();
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("System Default")).nth(2).click();
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("System Default")).nth(3).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Department")).click();
        return new AdminPage(page);
    }

    public String searchAndGetDepartmentName(String dept) {
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search Title...")).pressSequentially(dept);
        page.waitForTimeout(1000);
        String department_Name = page.textContent(departmentName);
        System.out.println("Department Name is : " + department_Name);
        return department_Name;
    }

    public AdminPage createNewTeam(String teamName, String agentName) {
        clickOnNewBtn();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Team")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Team Name..."))
                .pressSequentially(teamName);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Lead").setDescription("Select Lead").setExact(true))
                .click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Vishal Gore")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Add Agents..."))
                .pressSequentially(agentName);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("agentsession agentsession agentsessionn@yopmail.com"))
                .click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Team")).click();
        return new AdminPage(page);
    }

    public String searchAndGetTeamName(String team) {
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search Title...")).pressSequentially(team);
        page.waitForTimeout(1000);
        String team_Name = page.textContent(departmentName);
        System.out.println("Team Name is : " + team_Name);
        return team_Name;
    }

    public AdminPage createNewAccess(String accessName) {
        clickOnNewBtn();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("New Access")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Access Name..."))
                .pressSequentially(accessName);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Permissions")).click();
        page.locator(".w-\\[18px\\]").first().click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Access")).click();
        return new AdminPage(page);
    }

    public String searchAndGetAccessName(String access) {
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search Title...")).pressSequentially(access);
        page.waitForTimeout(1000);
        String access_Name = page.textContent(departmentName);
        System.out.println("Access Name is : " + access_Name);
        return access_Name;
    }

    public AdminPage createNewOrganisation(String organisationName,String organisationSignature,
                                           String address,String city,String zip,String phoneNumber) {
        clickOnNewBtn();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("New Organization")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Your Organization Name"))
                .pressSequentially(organisationName);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select SLA")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("P1")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Add department..."))
                .click();
        page.getByText("Helpdesk").click();
        page.locator(".ql-editor").pressSequentially(organisationSignature);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter your full address"))
                .pressSequentially(address);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select country")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Afghanistan")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select State / Province")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Badakhshan")).click();

        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter a City."))
                .pressSequentially(city);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter a Postal code."))
                .pressSequentially(zip);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter phone number"))
                .pressSequentially(phoneNumber);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Organization"))
                .click();
        return new AdminPage(page);
    }

    public String searchAndGetOrganisationName(String organization) {
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search Organization..."))
                .pressSequentially(organization);
        page.waitForTimeout(1000);
        String organization_Name = page.textContent(departmentName);
        System.out.println("Organization Name is : " + organization_Name);
        return organization_Name;
    }

}
