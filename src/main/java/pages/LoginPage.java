package pages;

import com.microsoft.playwright.Page;
import utils.BrowserManager;

public class LoginPage {

    private Page page;

    public LoginPage() {
        this.page = BrowserManager.getPage();
    }


    // signup method
    public void signUp(String username, String password) {
        // Wait for modal and fill form
        page.waitForSelector("#signInModal", new Page.WaitForSelectorOptions().setTimeout(7000));
        page.fill("#sign-username", username);
        page.fill("#sign-password", password);

        // Handle alert BEFORE clicking
        page.onDialog(dialog -> {
            System.out.println("Alert: " + dialog.message());
            dialog.accept(); // This clicks OK
            page.waitForTimeout(2000);
        });

        // Click BTN
        page.click("button[onclick='register()']");
        page.waitForTimeout(3000); // Wait

        // Close the modal
        try {
            if (page.locator("#signInModal .btn-secondary").isVisible()) {
                page.click("#signInModal .btn-secondary"); // Close button
                page.waitForTimeout(3000);
            }
        } catch (Exception e) {
            System.out.println("Modal already closed");
        }

        page.waitForTimeout(2000);
    }


    // User Login method
    public void login(String username, String password) {
        // Fill login-form
        page.fill("#loginusername", username);
        page.fill("#loginpassword", password);
        page.click("button[onclick='logIn()']");
        page.waitForTimeout(2000);
    }


    // check whether logged or not
    public boolean isLoggedIn() {
        return page.locator("#nameofuser").isVisible();
    }


    // User getting method
    public String getLoggedInUser() {
        if (isLoggedIn()) {
            try {
                String welcomeText = page.locator("#nameofuser").textContent();
                return welcomeText.replace("Welcome ", "");
            } catch (Exception e) {
                System.out.println("⚠️ Error getting logged in user: " + e.getMessage());
                return "Unknown User";
            }
        }
        return null;
    }
}