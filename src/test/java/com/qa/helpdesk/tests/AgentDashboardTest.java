package com.qa.helpdesk.tests;

import com.qa.helpdesk.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AgentDashboardTest extends BaseTest {

    @Test
    public void createTicketTest() {
        loginPage.agentLogin(prop.getProperty("username"), prop.getProperty("password") );
        agentDashboardPage.createNewTktBtn().selectDepartment().selectType()
                .selectPriority().enterSubject("test title").enterDescription("description")
                .clickCreateBtn().clickViewBtn();
        Assert.assertEquals(agentDashboardPage.getTicketTitle(),"test title");
    }
}
