package pages;

import com.microsoft.playwright.Page;
import utils.BrowserManager;

public class ProductPage {
    private Page page;

    public ProductPage()
    {
        this.page = BrowserManager.getPage();
    }

    public void addToCart() {
        System.out.println("🛒 Adding product to cart...");
        smoothPause("Preparing to add to cart...", 1000);
        smoothPause("Add to Cart button...", 1000);

        // Click add to cart button
        page.click("a[onclick*='addToCart']");
        smoothPause("Adding to cart...", 2000);

        System.out.println("Product added to cart successfully!");
        smoothPause("Add to cart completed!", 1000);
    }

    public String getProductName() {
        smoothPause("Reading product name...", 1000);

        String productName = page.locator(".name").textContent();
        System.out.println("Product name: " + productName);

        return productName;
    }

    public String getProductPrice() {
        System.out.println("Getting product price...");
        smoothPause("Reading product price...", 1000);

        String productPrice = page.locator(".price-container").textContent();
        System.out.println("Product price: " + productPrice);

        return productPrice;
    }

    // Helper method for smooth pauses
    private void smoothPause(String message, int milliseconds) {
        System.out.println(message);
        page.waitForTimeout(milliseconds);
    }
}