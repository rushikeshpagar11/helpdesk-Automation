package com.qa.helpdesk.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SetPasswordPage {

    private Page page;

    public SetPasswordPage(Page page) {
        this.page = page;
    }

    public SetPasswordPage enterPassword(String password) {

        System.out.println("Current URL: " + page.url());

        // Print all input elements on the page
        System.out.println("Input count: " + page.locator("input").count());

        for (int i = 0; i < page.locator("input").count(); i++) {
            Locator input = page.locator("input").nth(i);

            System.out.println(
                    "INPUT " + i +
                            " | type=" + input.getAttribute("type") +
                            " | name=" + input.getAttribute("name") +
                            " | placeholder=" + input.getAttribute("placeholder") +
                            " | id=" + input.getAttribute("id")
            );
        }

        Locator passwordField = page.locator("input[type='password']").nth(0);
        Locator confirmPasswordField = page.locator("input[type='password']").nth(1);

        passwordField.waitFor(
                new Locator.WaitForOptions()
                        .setTimeout(15000)
        );

        confirmPasswordField.waitFor(
                new Locator.WaitForOptions()
                        .setTimeout(15000)
        );

        passwordField.click();
        passwordField.fill(password);

        confirmPasswordField.click();
        confirmPasswordField.fill(password);

        System.out.println("Password entered successfully");

        return this;
    }

    public SetPasswordPage clickActivateBtn() {

        Locator activateButton = page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
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
}



