package com.qa.helpdesk.pages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.AriaRole;

import java.io.File;

public class LoginPage {

    private Page page;

    // Locators
    private String emailInput = "//input[@type='email']";
    private String passwordInput = "//input[@type='password']";
    private String loginButton = "button[type='submit']";
    private String forgetPwdLink = "//div[text()='Forgot password ?']";
    private String getCaptcha = "//img[@alt='captcha']";
    private String enterCaptcha = "//input[@placeholder='Enter Captcha']";
    private String staffLogin = "//button[text()='Staff Login']";
    private String reloadCaptcha = "(//*[name()='path'])[2]";
    private String logoContainer = "img[alt='Ampcus Logo']";

    public LoginPage(Page page) {
        this.page = page;
    }

    public String getLoginPageTitle() {
        String title = page.title();
        System.out.println("page title is : " + title);
        return title;
    }

    public String getLoginPageURL() {
        String url = page.url();
        System.out.println("page url is : " + url);
        return url;
    }

//    public String getDynamicCaptchaText() {
//        try {
//            // 1. Intercept response when captcha API is requested by page
//            Response captchaResponse = page.waitForResponse(
//                    response -> response.url().contains("/api/auth/captcha") && response.status() == 200,
//                    () -> {
//                        if (page.isVisible(getCaptcha)) {
//                            page.click(getCaptcha);
//                        }
//                    }
//            );
//
//            // 2. Extract captchaId
//            String responseBody = captchaResponse.text();
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode jsonNode = mapper.readTree(responseBody);
//            String dynamicCaptchaId = jsonNode.get("captchaId").asText();
//
//            System.out.println("Captured Dynamic Captcha ID: " + dynamicCaptchaId);
//
//            // 3. Make GET request directly to backend endpoint using Playwright request API
//            String apiUrl = "https://supportdesk-api.atpl.corp/api/auth/captcha/text?captchaId=" + dynamicCaptchaId;
//            APIResponse apiResponse = page.request().get(apiUrl);
//
//            // 4. Parse plain-text captcha code returned from server
//            JsonNode textJson = mapper.readTree(apiResponse.text());
//            String captchaText = textJson.get("code").asText();
//
//            System.out.println("Fetched Dynamic CAPTCHA Code: " + captchaText);
//            return captchaText;
//
//        } catch (Exception e) {
//            System.err.println("Failed to dynamically resolve CAPTCHA text: " + e.getMessage());
//            e.printStackTrace();
//            return "";
//        }
//    }


    public String getDynamicCaptchaText() {
        ObjectMapper mapper = new ObjectMapper();
        int maxRetries = 3;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                System.out.println("Attempt " + attempt + " to fetch CAPTCHA");

                // Wait for captcha response after clicking/reloading
                int finalAttempt = attempt;
                Response captchaResponse = page.waitForResponse(
                        response -> response.url().contains("/api/auth/captcha")
                                && response.status() == 200,
                        () -> {
                            if (finalAttempt == 1) {
                                page.click(getCaptcha); // Initial captcha load
                            } else {
                                page.click(reloadCaptcha); // Reload captcha
                            }
                        }
                );

                // Extract captchaId
                JsonNode jsonNode = mapper.readTree(captchaResponse.text());
                String captchaId = jsonNode.get("captchaId").asText();

                System.out.println("Captured CAPTCHA ID: " + captchaId);

                // Call captcha text API
                String apiUrl = "https://supportdesk-api.atpl.corp/api/auth/captcha/text?captchaId=" + captchaId;
                APIResponse apiResponse = page.request().get(apiUrl);

                if (!apiResponse.ok()) {
                    throw new RuntimeException("Captcha text API returned " + apiResponse.status());
                }

                JsonNode textJson = mapper.readTree(apiResponse.text());

                if (textJson.get("code") == null || textJson.get("code").asText().isEmpty()) {
                    throw new RuntimeException("Captcha code is missing.");
                }

                String captchaText = textJson.get("code").asText();

                System.out.println("Fetched CAPTCHA Code: " + captchaText);
                return captchaText;

            } catch (Exception e) {
                System.err.println("Attempt " + attempt + " failed: " + e.getMessage());

                if (attempt == maxRetries) {
                    break;
                }

                // Optional: wait briefly before retrying
                page.waitForTimeout(500);
            }
        }

        throw new RuntimeException("Unable to fetch CAPTCHA after " + maxRetries + " attempts.");
    }

    public HomePage userLogin(String username, String password) {
        String dynamicCaptchaText = getDynamicCaptchaText();

        page.fill(emailInput, username);
        page.fill(passwordInput, password);
        page.fill(enterCaptcha, dynamicCaptchaText);
        page.click(loginButton);

        return new HomePage(page);
    }

    public boolean isForgetPwdLinkVisible() {
        return page.isVisible(forgetPwdLink);
    }

    public AgentDashboardPage agentLogin(String username, String password) {
        String dynamicCaptchaText = getDynamicCaptchaText();
        page.click(staffLogin);
        page.fill(emailInput, username);
        page.fill(passwordInput, password);
        page.fill(enterCaptcha, dynamicCaptchaText);
        page.click(loginButton);

        return new AgentDashboardPage(page);
    }

    public HomePage clickLoginBtn() {
        page.click(loginButton);
        return new HomePage(page);
    }

    public String getEmailRequiredMsg() {
        return page.getByText("Email Address is required.").innerText();
    }

    public String getPasswordRequiredMsg() {
        return page.getByText("Password is required.").innerText();
    }

    public String getCaptchaRequiredMsg() {
        return page.getByText("Captcha is required").innerText();
    }

    public HomePage clickStaffLogin() {
        page.locator(staffLogin).click();
        return new HomePage(page);
    }

    public HomePage forgetPassword(String email) {
        page.locator(forgetPwdLink).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter your email")).pressSequentially(email);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reset Password")).click();
        return new HomePage(page);
    }

    public String passwordResetLinkMsg() {
        return page.getByText("Password reset link has been").innerText();
    }

    public File captureLogoImage(String savePath) {
        Locator logoElement = page.locator(logoContainer);
        File screenshotFile = new File(savePath);

        // Ensure parent directories exist
        screenshotFile.getParentFile().mkdirs();

        // Capture element screenshot
        logoElement.screenshot(new Locator.ScreenshotOptions().setPath(screenshotFile.toPath()));
        return screenshotFile;
    }

}