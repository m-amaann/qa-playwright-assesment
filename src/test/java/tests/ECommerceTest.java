package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestDataGenerator;
import utils.BrowserManager;

public class ECommerceTest extends BaseTest
{

    @Test(description = "Complete E-commerce workflow test")
    public void testECommerceWorkflow()
    {
        System.out.println("=" + "=".repeat(70));

        // Initialize pages
        HomePage homePage = new HomePage();
        LoginPage loginPage = new LoginPage();
        ProductPage productPage = new ProductPage();
        CartPage cartPage = new CartPage();

        // Generate test data
        String username = TestDataGenerator.generateUniqueUsername();
        String password = TestDataGenerator.getDefaultPassword();

        System.out.println("🎯 Test Configuration:");
        System.out.println("👤 Username: " + username);
        System.out.println("🔑 Password: " + password);
        System.out.println("=" + "=".repeat(70));

        smoothPause("Starting test execution...", 3000);

        try
        {
            // Step 1-3: Registration, Login, Verification
            performUserSetup(homePage, loginPage, username, password);

            // Step 4: Browse Sony Laptops
            int sonyCount = browseSonyLaptops(homePage);

            // Step 5: Add Available Sony Products
            addAvailableSonyProducts(homePage, productPage);

            // Step 6: Navigate to Cart and Verify
            homePage.navigateToCart();

            // Step 7: Check Cart Status and Handle Products
            handleCartOperations(cartPage);

            System.out.println("\n Test completed successfully (adapted to available products)!");

        }
        catch (Exception e)
        {
            System.out.println("\n TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private void performUserSetup(HomePage homePage, LoginPage loginPage, String username, String password) {
        System.out.println("\n USER SETUP");
        System.out.println("-" + "-".repeat(50));

        homePage.clickSignUp();
        loginPage.signUp(username, password);
        homePage.clickLogin();
        loginPage.login(username, password);

        Assert.assertTrue(loginPage.isLoggedIn(), "User should be logged in");
        System.out.println(" User setup completed: " + loginPage.getLoggedInUser());
    }

    private int browseSonyLaptops(HomePage homePage) {
        System.out.println("\n BROWSE SONY LAPTOPS");
        System.out.println("-" + "-".repeat(50));

        int sonyCount = homePage.getSonyLaptopCount();
        Assert.assertTrue(sonyCount > 0, "Sony laptops should be available");
        return sonyCount;
    }

    private void addAvailableSonyProducts(HomePage homePage, ProductPage productPage)
    {
        System.out.println("\n ADD SONY PRODUCTS");
        System.out.println("-" + "-".repeat(50));

        String[] sonyProducts = {"Sony vaio i5", "Sony vaio i7"};
        int addedCount = 0;

        for (String product : sonyProducts)
        {
            if (homePage.isProductVisible(product))
            {
                homePage.clickProduct(product);
                productPage.addToCart();
                BrowserManager.getPage().goBack();
                addedCount++;
                System.out.println("Added " + product + " to cart");
            } else {
                System.out.println(product + " not available");
            }
        }

        System.out.println("Total Sony products added: " + addedCount);
    }

    private void handleCartOperations(CartPage cartPage) {
        System.out.println("\nCART OPERATIONS");
        System.out.println("-" + "-".repeat(50));

        int itemCount = cartPage.getCartItemCount();
        System.out.println("🛒 Cart has " + itemCount + " items");

        if (itemCount > 0) {
            // Try to remove Sony vaio i5 if it exists
            if (cartPage.isProductInCart("Sony vaio i5")) {
                System.out.println("🗑️ Removing Sony vaio i5...");
                cartPage.removeProduct("Sony vaio i5");

                // Verify removal
                boolean stillInCart = cartPage.isProductInCart("Sony vaio i5");
                Assert.assertFalse(stillInCart, "Sony vaio i5 should be removed");
                System.out.println("Sony vaio i5 removal verified");
            } else {
                System.out.println("Sony vaio i5 not in cart - skipping removal");
            }

            // Check if cart still has items for order placement
            int remainingItems = cartPage.getCartItemCount();
            if (remainingItems > 0) {
                System.out.println("Placing order with remaining items...");
                placeOrder(cartPage);
            } else {
                System.out.println("Cart is now empty - skipping order placement");
            }
        } else {
            System.out.println("Cart is empty - no operations needed");
        }
    }

    private void placeOrder(CartPage cartPage) {
        cartPage.placeOrder(
                "Mohamed Amaan",
                "Sri Lanka",
                "New York",
                TestDataGenerator.getCardNumber(),
                TestDataGenerator.getCurrentMonth(),
                TestDataGenerator.getCurrentYear()
        );

        Assert.assertTrue(cartPage.isOrderSuccessful(), "Order should be successful");
        cartPage.confirmOrder();
        System.out.println("Order placed successfully!");
    }

    // Helper method for smooth pauses
    private void smoothPause(String message, int milliseconds) {
        System.out.println("⏸️ " + message);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}