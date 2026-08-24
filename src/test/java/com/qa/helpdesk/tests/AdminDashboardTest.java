package com.qa.helpdesk.tests;

import com.qa.helpdesk.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminDashboardTest extends BaseTest {

    @Test
    public void createAgentTest() {
        loginPage.agentLogin(prop.getProperty("username2"), prop.getProperty("password"));
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
        loginPage.agentLogin(prop.getProperty("username2"), prop.getProperty("password"));
        agentDashboardPage.clickSwitchToAdminBtn();
        String departmentName = faker.name().firstName()+"Department";
        adminPage.createNewDepartment(departmentName);
        Assert.assertEquals(adminPage.searchAndGetDepartmentName(departmentName), departmentName, "Department is not created");
    }

    @Test
    public void createTeamTest() {
        loginPage.agentLogin(prop.getProperty("username2"), prop.getProperty("password"));
        agentDashboardPage.clickSwitchToAdminBtn();
        String teamName = faker.name().firstName() + " Team";
        String agentName = prop.getProperty("username1");
        adminPage.createNewTeam(teamName, agentName);
        Assert.assertEquals(adminPage.searchAndGetTeamName(teamName), teamName, "Team is not created");
    }

    @Test
    public void createAccessTest() {
        loginPage.agentLogin(prop.getProperty("username2"), prop.getProperty("password"));
        agentDashboardPage.clickSwitchToAdminBtn();
        String accessName = faker.name().firstName() + "Access";
        adminPage.createNewAccess(accessName);
        Assert.assertEquals(adminPage.searchAndGetAccessName(accessName), accessName, "Access is not created");
    }

    @Test
    public void createOrganisationTest() {
        loginPage.agentLogin(prop.getProperty("username2"), prop.getProperty("password"));
        agentDashboardPage.clickSwitchToAdminBtn();
        String organisationName = faker.company().name();
        String orgSignature = faker.name().firstName();
        String address = faker.address().fullAddress();
        String city = faker.address().city();
        String zipCode = "2" + faker.number().digits(5);;
        String mobile = "9" + faker.number().digits(9);
        adminPage.createNewOrganisation(organisationName,orgSignature,address,city,zipCode,mobile);
        Assert.assertEquals(adminPage.searchAndGetOrganisationName(organisationName), organisationName, "Organization is not created");
    }


    @Test
    public void createAgentAndCompleteOnboardingTest() {
        loginPage.agentLogin(prop.getProperty("username2"), prop.getProperty("password"));
        agentDashboardPage.clickSwitchToAdminBtn();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userName = firstName + " " + lastName;
        String mobile = "9" + faker.number().digits(9);
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@yopmail.com";
        adminPage.createNewAgent(firstName, lastName, mobile, email);
        homePage.clickOnProfile().clickOnLogoutBtn();
        String onboardingUrl = emailHelper.clickSetMyPassword(email);
        System.out.println("Onboarding URL: " + onboardingUrl);
        page.navigate(onboardingUrl);

        setPasswordPage.enterPassword(prop.getProperty("password")).clickActivateBtn();
        getPage().pause();
        //getPage().pause();
        //Assert.assertEquals(adminPage.getUserName(), userName, "Agent is not created");

    }
}
