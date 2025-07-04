package pages;

import com.microsoft.playwright.Page;
import utils.BrowserManager;


public class HomePage {

    public Page page;


    public HomePage() {
        this.page = BrowserManager.getPage();
    }

    public void clickSignUp() {
        System.out.println("Clicking Sign Up button...");
        smoothPause("Locating Sign Up button...", 2000);

        page.locator("#signin2").waitFor();
        smoothPause("Sign Up button found!", 2000);

        page.click("#signin2");
        smoothPause("Sign Up button clicked!", 2000);
        System.out.println("Sign Up modal should be opening...");
    }

    public void clickLogin() {
        System.out.println("🔘 Clicking Login button...");
        smoothPause("Locating Login button...", 1000);

        page.locator("#login2").waitFor();
        smoothPause("Login button found!", 1000);

        page.click("#login2");
        smoothPause("Login button clicked!", 2000);
        System.out.println("Login modal should be opening...");
    }

    public void clickLaptopsCategory() {
        System.out.println("🔘 Navigating to Laptops category...");
        smoothPause("Looking for Laptops category...", 1000);

        page.click("a[onclick=\"byCat('notebook')\"]");
        smoothPause("Loading laptops...", 3000);
        System.out.println("Laptops category loaded!");
    }

    //
    public void navigateToCart() {
        System.out.println("🛒 Navigating to Cart page...");
        smoothPause("Looking for Cart link in navigation...", 1000);

        page.waitForSelector("#navbarExample", new Page.WaitForSelectorOptions().setTimeout(10000));
        smoothPause("Navigation bar found!", 1000);

        page.waitForSelector("#cartur", new Page.WaitForSelectorOptions().setTimeout(10000));
        smoothPause("Cart link found!", 1000);

        System.out.println("Clicking Cart link");
        page.click("#cartur");
        smoothPause("Loading cart page...", 3000);

        // Wait for cart content to be visible
        page.waitForSelector("#tbodyid", new Page.WaitForSelectorOptions().setTimeout(10000));
        smoothPause("Cart page loaded successfully!", 1500);

        System.out.println("✅ Cart page opened!");
    }

    // Keep the old method for backward compatibility
    public void clickCart() {
        navigateToCart();
    }

    public void clickProduct(String productName) {
        System.out.println("Clicking product: " + productName);
        smoothPause("Locating product...", 1000);

        page.click("a:has-text('" + productName + "')");
        smoothPause("Loading product page...", 3000);
        System.out.println("Product page loaded: " + productName);
    }

    public int getSonyLaptopCount() {
        System.out.println("Counting Sony laptops...");
        smoothPause("Navigating to laptops...", 1000);

        clickLaptopsCategory();
        smoothPause("Searching for Sony products...", 2000);

        int count = page.locator("a:has-text('Sony')").count();
        System.out.println("Found " + count + " Sony laptops");
        smoothPause("Count completed!", 1000);

        return count;
    }

    public boolean isProductVisible(String productName) {
        System.out.println("Checking if product is visible: " + productName);
        smoothPause("Searching for product...", 1000);

        boolean isVisible = page.locator("a:has-text('" + productName + "')").isVisible();
        System.out.println("Product '" + productName + "' visible: " + isVisible);
        smoothPause("Check completed!", 500);

        return isVisible;
    }

    // Helper method for smooth pauses
    private void smoothPause(String message, int milliseconds) {
        System.out.println(" " + message);
        page.waitForTimeout(milliseconds);
    }
}