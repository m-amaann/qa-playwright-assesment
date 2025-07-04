package pages;

import com.microsoft.playwright.Page;
import utils.BrowserManager;

public class CartPage {
    private Page page;

    public CartPage() {
        this.page = BrowserManager.getPage();
    }

    public boolean isProductInCart(String productName) {
        return page.locator("td:has-text('" + productName + "')").count() > 0;
    }

    public void removeProduct(String productName) {
        page.locator("tr:has(td:text('" + productName + "')) a[onclick*='deleteItem']").first().click();
        page.waitForTimeout(2000);
    }

    public void placeOrder(String name, String country, String city, String card, String month, String year) {
        // Click place order
        page.click("button[data-target='#orderModal']");
        page.waitForTimeout(1000);

        // Fill order form
        page.fill("#name", name);
        page.fill("#country", country);
        page.fill("#city", city);
        page.fill("#card", card);
        page.fill("#month", month);
        page.fill("#year", year);

        // Complete purchase
        page.click("button[onclick='purchaseOrder()']");
        page.waitForTimeout(2000);
    }

    public boolean isOrderSuccessful() {
        return page.locator(".sweet-alert h2").textContent().contains("Thank you for your purchase!");
    }

    public void confirmOrder() {
        page.click(".confirm");
        page.waitForTimeout(2000);
    }
}