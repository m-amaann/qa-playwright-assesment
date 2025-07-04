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
        System.out.println("Starting signup");
        smoothPause("Preparing signup form...", 1000);

        // Wait for signup modal to be visible
        page.waitForSelector("#signInModal", new Page.WaitForSelectorOptions().setTimeout(6000));
        smoothPause("Signup modal opened!", 1000);

        // Wait for form fields to be visible
        page.locator("#sign-username").waitFor();
        page.locator("#sign-password").waitFor();
        smoothPause("Form fields are ready!", 1000);

        // Fill username
        System.out.println("Type username: " + username);
        page.fill("#sign-username", username);
        smoothPause("Username entered!", 1000);

        // Fill password
        System.out.println("Type password...");
        page.fill("#sign-password", password);
        smoothPause("Password entered!", 1000);

        // Click signup button (Global dialog handler will handle alert)
        System.out.println("Clicking 'Sign up' button...");
        page.click("button[onclick='register()']");
        smoothPause("Processing", 4000);

        // Close modal
        try {
            if (page.locator("#signInModal .btn-secondary").isVisible()) {
                smoothPause("Closing signup modal...", 1000);
                page.click("#signInModal .btn-secondary");
            }
        } catch (Exception e) {
            System.out.println("Modal already closed");
        }

        smoothPause("Signup process completed!", 2000);
        System.out.println("Signup process finished!");
    }


    // User Login method
    public void login(String username, String password) {
        smoothPause("Preparing login...", 1000);

        // Wait for login modal to be visible
        page.waitForSelector("#logInModal", new Page.WaitForSelectorOptions().setTimeout(3000));
        smoothPause("Login modal opened!", 1000);

        page.locator("#loginusername").waitFor();
        page.locator("#loginpassword").waitFor();
        smoothPause("Login form is ready!", 1000);

        // Fill username
        System.out.println("Typing login username: " + username);
        page.fill("#loginusername", username);
        smoothPause("Username entered!", 1000);

        // Fill password
        System.out.println("Typing login password...");
        page.fill("#loginpassword", password);
        smoothPause("Password entered!", 1000);

        // Click login button
        System.out.println("Clicking 'Log in' button...");
        page.click("button[onclick='logIn()']");

        // Wait for login to complete
        smoothPause("Processing login...", 5000);

        System.out.println("Login process completed!");
    }


    // check whether logged or not
    public boolean isLoggedIn() {
        smoothPause("Verifying login...", 2000);

        try {
            boolean welcomeVisible = page.locator("#nameofuser").isVisible();
            System.out.println("Welcome message visible: " + welcomeVisible);

            if (welcomeVisible) {
                String welcomeText = page.locator("#nameofuser").textContent();
                System.out.println("👋 Welcome text: " + welcomeText);
                return true;
            }

            return false;
        } catch (Exception e) {
            System.out.println("Error checking login: " + e.getMessage());
            return false;
        }
    }


    // User getting method
    public String getLoggedInUser()
    {
        if (isLoggedIn())
        {
            try {
                String welcomeText = page.locator("#nameofuser").textContent();
                String username = welcomeText.replace("Welcome ", "");
                System.out.println("Logged in user: " + username);
                return username;
            } catch (Exception e) {
                System.out.println("Error getting username: " + e.getMessage());
                return "Unknown User";
            }
        }
        return null;
    }

    private void smoothPause(String message, int milliseconds) {
        System.out.println(message);
        page.waitForTimeout(milliseconds);
    }
}