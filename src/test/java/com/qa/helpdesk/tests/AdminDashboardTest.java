package com.qa.helpdesk.tests;

import com.microsoft.playwright.Page;
import com.qa.helpdesk.base.BaseTest;
import com.qa.helpdesk.pages.HomePage;
import com.qa.helpdesk.pages.LoginPage;
import com.qa.helpdesk.pages.SetPasswordPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminDashboardTest extends BaseTest {

    @Test
    public void createAgentTest() {
        loginPage.agentLogin(prop.getProperty("Admin_username"), prop.getProperty("password"));
        agentDashboardPage.clickSwitchToAdminBtn();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userName = firstName + " " + lastName;
        String mobile = "9" + faker.number().digits(9);
        String email = firstName.toLowerCase() + "."
                + lastName.toLowerCase()
                + "@yopmail.com";
        adminPage.createNewAgent(firstName, lastName, mobile, email);
        Assert.assertEquals(adminPage.getUserName(), userName, "Agent is not created");

    }

    @Test
    public void createDepartmentTest() {
        loginPage.agentLogin(prop.getProperty("Admin_username"), prop.getProperty("password"));
        agentDashboardPage.clickSwitchToAdminBtn();
        String departmentName = faker.name().firstName() + "Department";
        adminPage.createNewDepartment(departmentName);
        Assert.assertEquals(adminPage.searchAndGetDepartmentName(departmentName), departmentName, "Department is not created");
    }

//    @Test
//    public void createTeamTest() {
//        loginPage.agentLogin(prop.getProperty("Admin_username"), prop.getProperty("password"));
//        agentDashboardPage.clickSwitchToAdminBtn();
//        String teamName = faker.name().firstName() + " Team";
//        String agentName = prop.getProperty("username1");
//        adminPage.createNewTeam(teamName, agentName);
//        Assert.assertEquals(adminPage.searchAndGetTeamName(teamName), teamName, "Team is not created");
//    }

    @Test
    public void createAccessTest() {
        loginPage.agentLogin(prop.getProperty("Admin_username"), prop.getProperty("password"));
        agentDashboardPage.clickSwitchToAdminBtn();
        String accessName = faker.name().firstName() + "Access";
        adminPage.createNewAccess(accessName);
        Assert.assertEquals(adminPage.searchAndGetAccessName(accessName), accessName, "Access is not created");
    }

    @Test
    public void createOrganisationTest() {
        loginPage.agentLogin(prop.getProperty("Admin_username"), prop.getProperty("password"));
        agentDashboardPage.clickSwitchToAdminBtn();
        String organisationName = faker.company().name();
        String orgSignature = faker.name().firstName();
        String address = faker.address().fullAddress();
        String city = faker.address().city();
        String zipCode = "2" + faker.number().digits(5);
        ;
        String mobile = "9" + faker.number().digits(9);
        adminPage.createNewOrganisation(organisationName, orgSignature, address, city, zipCode, mobile);
        Assert.assertEquals(adminPage.searchAndGetOrganisationName(organisationName), organisationName, "Organization is not created");
    }


    @Test
    public void createAgentAndCompleteOnboardingTest() {

        loginPage.agentLogin(prop.getProperty("Admin_username"), prop.getProperty("password"));

        agentDashboardPage.clickSwitchToAdminBtn();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userName = firstName + " " + lastName;
        String mobile = "9" + faker.number().digits(9);
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@yopmail.com";

        adminPage.createNewAgent(firstName, lastName, mobile, email);
        homePage.clickOnProfile().clickOnLogoutBtn();

        Page onboardingPage = page.context().waitForPage(() -> {
            emailHelper.clickSetMyPassword(email);
        });

        onboardingPage.waitForLoadState();

        System.out.println("Onboarding URL: " + onboardingPage.url());

        SetPasswordPage setPasswordPage = new SetPasswordPage(onboardingPage);

        setPasswordPage.enterPassword(prop.getProperty("password")).clickActivateBtn();

        setPasswordPage.clickGoToLoginBtn();
        LoginPage onboardingLoginPage =
                new LoginPage(onboardingPage);
        onboardingLoginPage.userLogin(email, prop.getProperty("password"));
        Assert.assertTrue(homePage.isCreateTicketBtnVisible(), "user is not logged in");
    }

    @Test
    public void createAgentAndEditTest() {
        // 1. Login as Admin and switch to Admin panel
        loginPage.agentLogin(prop.getProperty("Admin_username"), prop.getProperty("password"));
        agentDashboardPage.clickSwitchToAdminBtn();

        // 2. Generate random credentials for the new Agent
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userName = firstName + " " + lastName;
        String mobile = "9" + faker.number().digits(9);
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@yopmail.com";

        // 3. Create the Agent and logout from Admin account
        adminPage.createNewAgent(firstName, lastName, mobile, email);
        homePage.clickOnProfile().clickOnLogoutBtn();

        // 4. Handle dynamic tab for email password setup
        Page onboardingPage = page.context().waitForPage(() -> {
            emailHelper.clickSetMyPassword(email);
        });
        onboardingPage.waitForLoadState();
        System.out.println("Onboarding URL: " + onboardingPage.url());

        // 5. Complete password activation process
        SetPasswordPage setPasswordPage = new SetPasswordPage(onboardingPage);
        setPasswordPage.enterPassword(prop.getProperty("password")).clickActivateBtn();
        setPasswordPage.clickGoToLoginBtn();

        // 6. Login as the newly created Agent to complete onboarding verification
        LoginPage onboardingLoginPage = new LoginPage(onboardingPage);
        onboardingLoginPage.userLogin(email, prop.getProperty("password"));

        // 7. Logout the Agent from the onboarding tab session and close it cleanly
        HomePage onboardingHomePage = new HomePage(onboardingPage);
        onboardingHomePage.UserPanelClickOnProfileBtn().UserPanelClickOnLogoutBtn();
        onboardingPage.close();

        // 8. Shift execution lens back to primary browser window
        page.bringToFront();
        page.navigate(prop.getProperty("url"));

        // 9. Login as Admin again to manage and edit the newly created Agent
        loginPage.agentLogin(prop.getProperty("Admin_username"), prop.getProperty("password"));
        agentDashboardPage.clickSwitchToAdminBtn();

        // 10. Open the first agent profile (the one we just created)
        adminPage.clickOnFirstAgent();

        // 11. Generate update details
        String updatedFirstName = faker.name().firstName();
        String updatedLastName = faker.name().lastName();

        // 12. Execute the edit update action (using our clean wipe .fill() pattern)
        adminPage.updateAgent(updatedFirstName, updatedLastName);

        // 13. Re-open the profile to verify the persisted changes
        adminPage.clickOnNewAgent();
        agentDashboardPage.verifyTextBoxValue(page, "First Name", updatedFirstName);
        agentDashboardPage.verifyTextBoxValue(page, "Last Name", updatedLastName);
    }


}
