package pages;

import com.microsoft.playwright.Page;
import utils.BrowserManager;

public class ProductPage {
    private Page page;

    public ProductPage() {
        this.page = BrowserManager.getPage();
    }

    public void addToCart() {
        // Handle popup
        page.onDialog(dialog -> {
            System.out.println("Alert: " + dialog.message());
            page.waitForTimeout(1000);
            dialog.accept();
        });
        page.waitForTimeout(3000);
        page.click("a[onclick*='addToCart']");
        page.waitForTimeout(3000);
    }

    public String getProductName() {
        return page.locator(".name").textContent();
    }
}