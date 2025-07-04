package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestDataGenerator;

public class ECommerceTest extends BaseTest {

    @Test(description = "E2E Workflow Test")
    public void testECommerceWorkflow() {

        // Initialize pages
        HomePage homePage = new HomePage();
        LoginPage loginPage = new LoginPage();
        ProductPage productPage = new ProductPage();
        CartPage cartPage = new CartPage();

        /*
            i. Sign up with unique user
        */
        // Generate test data
        String username = TestDataGenerator.generateUniqueUsername(); // generate the unique usernames
        String password = TestDataGenerator.getDefaultPassword();

        System.out.println(" Starting Test");
        System.out.println(" Username: " + username);
        System.out.println(" Password: " + password);


        // Step 1: Sign up
        System.out.println("\n User Registration");
        homePage.clickSignUp();
        loginPage.signUp(username, password);


        /*
            ii. Log in with created user
        */
        // Step 2: Login
        System.out.println(" Step 2: User Login");
        homePage.clickLogin();
        loginPage.login(username, password);

        // Verify login
        Assert.assertTrue(loginPage.isLoggedIn(), "User should be logged in");
        System.out.println(" Logged in as: " + loginPage.getLoggedInUser());


        // iii. Search for all Sony laptops
        // Step 3: Browse Sony laptops
        System.out.println("\n Step 3: Browse Sony Laptops");
        int sonyCount = homePage.getSonyLaptopCount();
        System.out.println(" Found " + sonyCount + " Sony laptops");
        Assert.assertTrue(sonyCount > 0, "Sony laptops should be available");


        // Step 4: Add Sony products to cart
        System.out.println("\n🛒 Step 4: Add Sony Products");


        // iv. Click on products & v. Add to cart
        // Add Sony vaio i5
        if (homePage.isProductVisible("Sony vaio i5")) {
            homePage.clickProduct("Sony vaio i5");
            productPage.addToCart();
            homePage.page.goBack(); // Go back to products
            System.out.println("Added Sony vaio i5");
        }


        // Add Sony vaio i7
        if (homePage.isProductVisible("Sony vaio i7")) {
            homePage.clickProduct("Sony vaio i7");
            productPage.addToCart();
            homePage.page.goBack(); // Go back to products
            System.out.println("Added Sony vaio i7");
        }

        // Step 5: Verify cart
        System.out.println("\n Step 5: Check Cart");
        homePage.clickCart();
        Assert.assertTrue(cartPage.isProductInCart("Sony vaio i5"), "Sony vaio i5 should be in cart");
        System.out.println("Products verified in cart");


        // vi. Remove Sony vaio i5 product
        // Step 6: Remove Sony vaio i5
        System.out.println("\n🗑️ Step 6: Remove Sony vaio i5");
        cartPage.removeProduct("Sony vaio i5");
        Assert.assertFalse(cartPage.isProductInCart("Sony vaio i5"), "Sony vaio i5 should be removed");
        System.out.println("Sony vaio i5 removed");


        // vii. Place order
        // Step 7: Place order
        System.out.println("\n Step 7: Place Order");
        cartPage.placeOrder(
                "Mohamed Amaan",
                "Sri Lanka",
                "Colombo",
                TestDataGenerator.getCardNumber(),
                TestDataGenerator.getCurrentMonth(),
                TestDataGenerator.getCurrentYear()
        );

        // Verify order
        Assert.assertTrue(cartPage.isOrderSuccessful(), "Order should be successful");
        cartPage.confirmOrder();
        System.out.println("🎉 Order placed successfully!");

        System.out.println("\n All tests completed successfully!");
    }
}

