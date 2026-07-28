package com.qa.helpdesk.tests;

import com.qa.helpdesk.base.BaseTest;
import org.testng.annotations.Test;

public class AgentDashboardTest extends BaseTest {

    @Test
    public void createTicketTest(){
        loginPage.agentLogin(prop.getProperty("username"), prop.getProperty("password") );
        agentDashboardPage.clickOnNewBtn();
        agentDashboardPage.createNewTkt();
        
    }
}
