package pages;

import com.microsoft.playwright.Page;
import utils.BrowserManager;

public class CartPage {
    private Page page;

    public CartPage() {
        this.page = BrowserManager.getPage();
    }

    public boolean isProductInCart(String productName) {
        System.out.println("🔍 Checking if product is in cart: " + productName);
        smoothPause("Searching in cart...", 1000);

        try {
            // First check if cart table exists and is visible
            if (!page.locator("#tbodyid").isVisible()) {
                System.out.println("🛒 Cart is empty - no products found");
                return false;
            }

            // Check if the specific product is in cart
            boolean isInCart = page.locator("td:has-text('" + productName + "')").count() > 0;
            System.out.println("🛒 Product '" + productName + "' in cart: " + isInCart);
            smoothPause("Check completed!", 1000);

            return isInCart;

        } catch (Exception e) {
            System.out.println("ℹ️ Cart check failed (likely empty cart): " + e.getMessage());
            return false;
        }
    }

    public void removeProduct(String productName) {
        System.out.println("🗑️ Removing product from cart: " + productName);
        smoothPause("Locating product in cart...", 1500);

        try {
            // Check if cart has any content
            if (!page.locator("#tbodyid").isVisible()) {
                System.out.println("⚠️ Cart is already empty");
                return;
            }

            smoothPause("Cart table found!", 1000);

            // Find the row containing the product
            String productRowSelector = "tr:has(td:text('" + productName + "'))";
            System.out.println("🔍 Looking for product row...");
            smoothPause("Searching for: " + productName, 1500);

            // Check if product exists
            if (page.locator(productRowSelector).count() == 0) {
                System.out.println("⚠️ Product not found in cart: " + productName);
                return;
            }

            System.out.println("✅ Product found in cart!");
            smoothPause("Preparing to remove...", 1000);

            // Find the delete button in the product row
            String deleteButtonSelector = productRowSelector + " a:has-text('Delete')";
            System.out.println("🔍 Looking for Delete button...");
            smoothPause("Locating Delete button...", 1000);

            // Verify delete button is visible
            if (!page.locator(deleteButtonSelector).isVisible()) {
                System.out.println("⚠️ Delete button not found for: " + productName);
                return;
            }

            System.out.println("✅ Delete button found!");
            smoothPause("Preparing to click Delete...", 1500);

            // Click the delete button
            System.out.println("🔘 Clicking Delete button...");
            page.click(deleteButtonSelector);

            // Wait for the product to be removed
            smoothPause("Removing product...", 3000);

            System.out.println("✅ Product removed successfully!");
            smoothPause("Removal completed!", 1500);

        } catch (Exception e) {
            System.out.println("⚠️ Error during removal: " + e.getMessage());
        }
    }

    public void placeOrder(String name, String country, String city, String card, String month, String year) {
        System.out.println("📦 Starting order placement...");
        smoothPause("Preparing to place order...", 1500);

        // Check if cart has items before placing order
        if (!hasItemsInCart()) {
            System.out.println("⚠️ Cannot place order - cart is empty");
            return;
        }

        // Click place order button
        System.out.println("🔘 Clicking Place Order button...");
        page.click("button[data-target='#orderModal']");
        smoothPause("Opening order form...", 2000);

        // Wait for order modal to appear
        page.waitForSelector("#orderModal", new Page.WaitForSelectorOptions().setTimeout(10000));
        smoothPause("Order form opened!", 1000);

        // Fill order form
        System.out.println("✍️ Filling order details...");
        smoothPause("Filling customer name...", 1000);
        page.fill("#name", name);

        smoothPause("Filling country...", 1000);
        page.fill("#country", country);

        smoothPause("Filling city...", 1000);
        page.fill("#city", city);

        smoothPause("Filling card number...", 1000);
        page.fill("#card", card);

        smoothPause("Filling month...", 1000);
        page.fill("#month", month);

        smoothPause("Filling year...", 1000);
        page.fill("#year", year);

        System.out.println("✅ Order form completed!");
        smoothPause("Preparing to purchase...", 1500);

        // Click purchase button
        System.out.println("🔘 Clicking Purchase button...");
        page.click("button[onclick='purchaseOrder()']");

        smoothPause("Processing order...", 3000);
        System.out.println("✅ Order placement completed!");
    }

    public boolean isOrderSuccessful() {
        System.out.println("🔍 Checking order success...");
        smoothPause("Verifying order completion...", 2000);

        try {
            page.waitForSelector(".sweet-alert h2", new Page.WaitForSelectorOptions().setTimeout(10000));
            String successText = page.locator(".sweet-alert h2").textContent();
            boolean isSuccess = successText.contains("Thank you for your purchase!");

            System.out.println("🎉 Order success message: " + successText);
            smoothPause("Order verification completed!", 1500);

            return isSuccess;
        } catch (Exception e) {
            System.out.println("⚠️ Order success verification failed");
            return false;
        }
    }

    public void confirmOrder() {
        System.out.println("✅ Confirming order...");
        smoothPause("Clicking confirmation...", 1000);

        page.click(".confirm");
        smoothPause("Order confirmed!", 2000);
        System.out.println("🎉 Order confirmation completed!");
    }

    public int getCartItemCount() {
        System.out.println("🔢 Counting cart items...");
        smoothPause("Counting products...", 1000);

        try {
            if (!page.locator("#tbodyid").isVisible()) {
                System.out.println("🛒 Cart is empty - 0 items");
                return 0;
            }

            int count = page.locator("#tbodyid tr").count();
            System.out.println("🛒 Cart has " + count + " items");
            smoothPause("Count completed!", 500);

            return count;
        } catch (Exception e) {
            System.out.println("ℹ️ Could not count items (likely empty cart)");
            return 0;
        }
    }

    public boolean hasItemsInCart() {
        try {
            return page.locator("#tbodyid").isVisible() && page.locator("#tbodyid tr").count() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Helper method for smooth pauses
    private void smoothPause(String message, int milliseconds) {
        System.out.println("⏸️ " + message);
        page.waitForTimeout(milliseconds);
    }
}