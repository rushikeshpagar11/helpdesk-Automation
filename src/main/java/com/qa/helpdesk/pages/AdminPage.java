package com.qa.helpdesk.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class AdminPage {
    private Page page;
    private String emailBtn = "//span[normalize-space()='Email']";

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


}
