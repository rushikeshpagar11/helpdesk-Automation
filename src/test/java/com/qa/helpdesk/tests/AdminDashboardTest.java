package com.qa.helpdesk.tests;

import com.qa.helpdesk.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminDashboardTest extends BaseTest {

    @Test
    public void createAgentTest() {
        loginPage.agentLogin(prop.getProperty("username"), prop.getProperty("password"));
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
        loginPage.agentLogin(prop.getProperty("username"), prop.getProperty("password"));
        agentDashboardPage.clickSwitchToAdminBtn();
        String departmentName = faker.name().firstName();
        adminPage.createNewDepartment(departmentName);
        Assert.assertEquals(adminPage.searchAndGetDepartmentName(departmentName), departmentName, "Department is not created");
    }

    @Test
    public void createTeamTest() {
        loginPage.agentLogin(prop.getProperty("username1"), prop.getProperty("password"));
        agentDashboardPage.clickSwitchToAdminBtn();
        String teamName = faker.name().firstName();
        String agentName = prop.getProperty("username1");
        adminPage.createNewTeam(teamName,agentName);
        Assert.assertEquals(adminPage.searchAndGetTeamName(teamName), teamName, "Team is not created");
    }
}
