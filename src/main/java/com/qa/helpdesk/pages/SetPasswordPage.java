package com.qa.helpdesk.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SetPasswordPage {

    private Page page;
    private String goToLoginBtn = "//button[text()='Go to Login']";

    public SetPasswordPage(Page page) {
        this.page = page;
    }

    public SetPasswordPage enterPassword(String password) {

        System.out.println("Current URL: " + page.url());

        Locator passwordField =
                page.locator("input[type='password']").nth(0);

        Locator confirmPasswordField =
                page.locator("input[type='password']").nth(1);

        passwordField.waitFor(
                new Locator.WaitForOptions()
                        .setTimeout(15000)
        );

        confirmPasswordField.waitFor(
                new Locator.WaitForOptions()
                        .setTimeout(15000)
        );

        passwordField.fill(password);
        confirmPasswordField.fill(password);

        System.out.println(
                "Password entered. Value length: "
                        + passwordField.inputValue().length()
        );

        return this;
    }

    public SetPasswordPage clickActivateBtn() {

        Locator activateButton = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName("Activate Account")
        );

        activateButton.waitFor(
                new Locator.WaitForOptions()
                        .setTimeout(15000)
        );

        activateButton.click();

        return this;
    }

    public LoginPage clickGoToLoginBtn() {
        page.locator(goToLoginBtn).click();
        return new LoginPage(page);
    }
}
