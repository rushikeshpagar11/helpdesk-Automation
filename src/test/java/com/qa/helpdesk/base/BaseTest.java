package com.qa.helpdesk.base;

import com.microsoft.playwright.Page;
import com.qa.helpdesk.factory.PlaywrightFactory;
import com.qa.helpdesk.listners.TestListener;
import com.qa.helpdesk.pages.*;
import net.datafaker.Faker;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

@Listeners(TestListener.class)
public class BaseTest {

    protected PlaywrightFactory playwrightFactory;
    protected Page page;
    protected Properties prop;
    protected LoginPage loginPage;
    protected HomePage homePage;
    protected AgentDashboardPage agentDashboardPage;
    protected Faker faker;
    protected AdminPage adminPage;
    protected EmailHelper emailHelper;
    protected SetPasswordPage setPasswordPage;

    @BeforeMethod
    public void setup() {
        playwrightFactory = new PlaywrightFactory();
        prop = playwrightFactory.init_prop();
        page = playwrightFactory.initBrowser(prop);
        loginPage = new LoginPage(page);
        agentDashboardPage = new AgentDashboardPage(page);
        faker = new Faker(Locale.ENGLISH);
        adminPage = new AdminPage(page);
        homePage = new HomePage(page);
        emailHelper = new EmailHelper(page);
        setPasswordPage = new SetPasswordPage(page);
    }

    @AfterMethod
    public void tearDown() {
        if (page != null) {
            page.context().browser().close();
        }
    }

    /**
     * Automatically generates the physical HTML report files, and then
     * opens a safe, temporary local web server to bypass browser CORS restrictions.
     */
    @AfterSuite(alwaysRun = true)
    public void generateAndOpenAllureReport() {
        // Check if automatic opening is explicitly disabled
        // By default, it will now SKIP opening unless you pass -DopenReport=true
        String openReportProp = System.getProperty("openReport", "true");

        if (!Boolean.parseBoolean(openReportProp)) {
            System.out.println("=========================================================");
            System.out.println("⏭️ Skipping Allure Report generation and launch.");
            System.out.println("👉 To enable, run with: -DopenReport=true");
            System.out.println("=========================================================");
            return;
        }

        ProcessBuilder generateBuilder = new ProcessBuilder();
        ProcessBuilder openBuilder = new ProcessBuilder();

        // 1. Configure the commands to generate the static files
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            generateBuilder.command("cmd.exe", "/c", "allure generate allure-results --clean -o allure-report");
            openBuilder.command("cmd.exe", "/c", "allure open allure-report");
        } else {
            generateBuilder.command("bash", "-c", "allure generate allure-results --clean -o allure-report");
            openBuilder.command("bash", "-c", "allure open allure-report");
        }

        try {
            System.out.println("=========================================================");
            System.out.println("🚀 Test Suite Complete. Storing physical Allure Report...");
            System.out.println("=========================================================");

            // Build the physical files inside /allure-report/
            Process generateProcess = generateBuilder.inheritIO().start();
            int exitCode = generateProcess.waitFor();

            if (exitCode == 0) {
                System.out.println("=========================================================");
                System.out.println("📂 SUCCESS: Report permanently saved to: /allure-report/");
                System.out.println("🌐 Launching secure local web server to safely preview data...");
                System.out.println("=========================================================");

                // Spin up a live local web server to view the generated folder safely
                openBuilder.inheritIO().start();
            } else {
                System.err.println("⚠️ Allure generation finished with an error code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Failed to process and open Allure report automatically: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }


    public Page getPage() {
        return this.page;
    }
}
