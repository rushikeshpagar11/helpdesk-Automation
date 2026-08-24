package com.qa.helpdesk.pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class EmailHelper {

    private final Page page;

    public EmailHelper(Page page) {
        this.page = page;
    }

    public String clickSetMyPassword(String email) {

        String inboxName = email.split("@")[0];

        // Open Yopmail inbox
        page.navigate("https://yopmail.com/?login=" + inboxName);

        page.waitForTimeout(3000);

        for (int attempt = 1; attempt <= 10; attempt++) {

            try {

                FrameLocator inboxFrame =
                        page.locator("#ifinbox").contentFrame();

                Locator emails =
                        inboxFrame.locator("div.m");

                if (emails.count() == 0) {

                    System.out.println(
                            "Email not found. Attempt: " + attempt
                    );

                } else {

                    // Open latest email
                    emails.first().click();

                    page.waitForTimeout(1500);

                    FrameLocator mailFrame =
                            page.locator("#ifmail").contentFrame();

                    // Find "Set My Password"
                    Locator passwordLink =
                            mailFrame.getByRole(
                                    AriaRole.LINK,
                                    new FrameLocator.GetByRoleOptions()
                                            .setName("Set My Password")
                            );

                    if (passwordLink.count() > 0) {

                        System.out.println(
                                "Set My Password link found."
                        );

                        // Get href before clicking
                        String onboardingUrl =
                                passwordLink.getAttribute("href");

                        System.out.println(
                                "Onboarding URL: "
                                        + onboardingUrl
                        );

                        if (onboardingUrl == null || onboardingUrl.trim().isEmpty()) {

                            throw new RuntimeException(
                                    "Set My Password link has no href"
                            );
                        }

                        // CLICK THE ACTUAL LINK
                        passwordLink.click();

                        return onboardingUrl;
                    }

                    System.out.println(
                            "Set My Password link not found. Attempt: "
                                    + attempt
                    );
                }

            } catch (Exception e) {

                System.out.println(
                        "Email/link not ready. Attempt: "
                                + attempt
                );

                System.out.println(
                        "Reason: " + e.getMessage()
                );
            }

            // Refresh Yopmail
            page.reload();

            page.waitForTimeout(3000);
        }

        throw new RuntimeException(
                "Set My Password link was not found for: "
                        + email
        );
    }
}