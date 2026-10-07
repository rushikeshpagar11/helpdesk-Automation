package com.qa.helpdesk.tests;

import com.microsoft.playwright.Page;
import com.qa.helpdesk.base.BaseTest;
import com.qa.helpdesk.pages.LoginPage;
import com.qa.helpdesk.pages.SetPasswordPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AgentDashboardTest extends BaseTest {

    @Test
    public void createTicketTest() {
        loginPage.agentLogin(prop.getProperty("Agent_username1"), prop.getProperty("password"));
        String subject = faker.book().title();
        String description = faker.company().catchPhrase();
        agentDashboardPage.createNewTktBtn().selectDepartment().selectType()
                .selectPriority().enterSubject(subject).enterDescription(description)
                .clickCreateBtn().clickViewBtn();
        Assert.assertEquals(agentDashboardPage.getTicketTitle(), subject);
    }

    @Test
    public void agentToAdminPanelSwitchTest() {
        loginPage.agentLogin(prop.getProperty("Agent_username2"), prop.getProperty("password"));
        agentDashboardPage.clickSwitchToAdminBtn();
        Assert.assertTrue(adminPage.isEmailTabVisible(), "agent is not redirected to admin panel");
    }

    @Test
    public void createUserTest() {
        loginPage.agentLogin(prop.getProperty("Agent_username3"), prop.getProperty("password"));
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userName = firstName + " " + lastName;
        String mobile = "9" + faker.number().digits(9);
        String email = firstName.toLowerCase() + "."
                + lastName.toLowerCase()
                + "@yopmail.com";
        agentDashboardPage.createNewUser(firstName, lastName, mobile, email);
        getPage().pause();
        Assert.assertEquals(agentDashboardPage.getUserName(), userName, "user is not created");

    }

    @Test
    public void createNewMacroTest() {
        loginPage.agentLogin(prop.getProperty("Agent_username4"), prop.getProperty("password"));
        String macroTitle = faker.company().buzzword() + " Macro";
        String macroDescription =
                faker.letterify("Playwright Macro description. ??????");
        agentDashboardPage.createNewMacro(macroTitle, macroDescription);
        Assert.assertEquals(agentDashboardPage.getMacroTitle(), macroTitle, "New Macro is not created");
    }

    @Test
    public void createNewKnowledgeableTest() {
        loginPage.agentLogin(prop.getProperty("Agent_username5"), prop.getProperty("password"));
        String knowledgeableTitle = faker.company().buzzword() + " Knowledgeable";
        String knowledgeableContent = "Playwright Knowledgeable content " + faker.lorem().paragraphs(2);
        agentDashboardPage.createNewKnowledgeable(knowledgeableTitle, knowledgeableContent);
        Assert.assertEquals(agentDashboardPage.getKnowledgeableTitle(), knowledgeableTitle, "New Knowledgeable is not created");
    }

    @Test
    public void addNewTaskTest() {
        loginPage.agentLogin(prop.getProperty("Agent_username1"), prop.getProperty("password"));
        String taskTitle = faker.company().buzzword() + " task";
        String taskDescription = faker.letterify("Playwright task description. ??????");
        agentDashboardPage.addNewTask(taskTitle, taskDescription);
        Assert.assertEquals(agentDashboardPage.getTaskTitle(), taskTitle, "New task is not created");
    }

    @Test
    public void createUserAndViewDetailsTest() {
        loginPage.agentLogin(prop.getProperty("Agent_username2"), prop.getProperty("password"));
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userName = firstName + " " + lastName;
        String mobile = "9" + faker.number().digits(9);
        String email = firstName.toLowerCase() + "."
                + lastName.toLowerCase()
                + "@yopmail.com";
        agentDashboardPage.createNewUser(firstName, lastName, mobile, email);
        Assert.assertEquals(agentDashboardPage.getUserName(), userName, "user is not created");
        agentDashboardPage.clickOnFirstUser();
        agentDashboardPage.verifyTextBoxValue(page, "First Name", firstName);
        agentDashboardPage.verifyTextBoxValue(page, "Last Name", lastName);
        agentDashboardPage.verifyTextBoxValue(page, "Enter Mobile Number", mobile);
        agentDashboardPage.verifyTextBoxValue(page, "Enter Email Address", email);
    }

    @Test
    public void createUserAndDeleteTest() {
        loginPage.agentLogin(prop.getProperty("Agent_username3"), prop.getProperty("password"));
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userName = firstName + " " + lastName;
        String mobile = "9" + faker.number().digits(9);
        String email = firstName.toLowerCase() + "."
                + lastName.toLowerCase()
                + "@yopmail.com";
        agentDashboardPage.createNewUser(firstName, lastName, mobile, email);
        agentDashboardPage.searchAndDeleteUser(email);

        //Assert.assertEquals(agentDashboardPage.getUserName(), userName, "user is not created");

    }

    @Test
    public void createUserAndCompleteOnboardingTest() {

        loginPage.agentLogin(prop.getProperty("Agent_username4"), prop.getProperty("password"));

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userName = firstName + " " + lastName;
        String mobile = "9" + faker.number().digits(9);
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@yopmail.com";

        agentDashboardPage.createNewUser(firstName, lastName, mobile, email);
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


}
