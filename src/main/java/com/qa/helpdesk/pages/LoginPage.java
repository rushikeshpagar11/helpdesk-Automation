package com.qa.helpdesk.pages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;

public class LoginPage {

    private Page page;

    // Locators
    private String emailInput = "//input[@type='email']";
    private String passwordInput = "//input[@type='password']";
    private String loginButton = "button[type='submit']";
    private String forgetPwdLink = "//div[text()='Forgot password ?']";
    private String getCaptcha = "//img[@alt='captcha']";
    private String enterCaptcha = "//input[@placeholder='Enter The Text Shown Above']";
    private String staffLogin = "//button[text()='Staff Login']";

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

    public String getDynamicCaptchaText() {
        try {
            // 1. Intercept response when captcha API is requested by page
            Response captchaResponse = page.waitForResponse(
                    response -> response.url().contains("/api/auth/captcha") && response.status() == 200,
                    () -> {
                        if (page.isVisible(getCaptcha)) {
                            page.click(getCaptcha);
                        }
                    }
            );

            // 2. Extract captchaId
            String responseBody = captchaResponse.text();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(responseBody);
            String dynamicCaptchaId = jsonNode.get("captchaId").asText();

            System.out.println("Captured Dynamic Captcha ID: " + dynamicCaptchaId);

            // 3. Make GET request directly to backend endpoint using Playwright request API
            String apiUrl = "https://supportdesk-api.atpl.corp/api/auth/captcha/text?captchaId=" + dynamicCaptchaId;
            APIResponse apiResponse = page.request().get(apiUrl);

            // 4. Parse plain-text captcha code returned from server
            JsonNode textJson = mapper.readTree(apiResponse.text());
            String captchaText = textJson.get("code").asText();

            System.out.println("Fetched Dynamic CAPTCHA Code: " + captchaText);
            return captchaText;

        } catch (Exception e) {
            System.err.println("Failed to dynamically resolve CAPTCHA text: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
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
}