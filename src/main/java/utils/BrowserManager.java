package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BrowserManager {
    private static ThreadLocal<Page> page = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static Playwright playwright;

    public static void startBrowser() {
        System.out.println("🚀 Starting browser");
        playwright = Playwright.create();

        Browser browserInstance = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)           // Keep browser visible
                        .setSlowMo(500)               // 500ms delay between actions
        );
        browser.set(browserInstance);

        Page pageInstance = browserInstance.newPage();
        pageInstance.setDefaultTimeout(60000);  // 60 second timeout

        // GLOBAL DIALOG HANDLER - Handles ALL alerts automatically
        pageInstance.onDialog(dialog -> {
            try {
                System.out.println("Global Alert: " + dialog.message());
                dialog.accept();
                System.out.println("Alert auto-accepted");
            } catch (Exception e) {
                System.out.println("Alert already handled: " + e.getMessage());
            }
        });

        page.set(pageInstance);

        // Navigate to application
        pageInstance.navigate("https://www.demoblaze.com/");
        pageInstance.waitForLoadState();
        System.out.println("🌐 Navigated to site");
    }

    public static Page getPage() {
        return page.get();
    }

    public static void closeBrowser() {
        System.out.println("⏳ Closing browser slowly...");
        if (page.get() != null) {
            page.get().waitForTimeout(2000);
            page.get().close();
            page.remove();
        }
        if (browser.get() != null) {
            browser.get().close();
            browser.remove();
        }
        if (playwright != null) {
            playwright.close();
        }
        System.out.println("*** Browser closed ***");
    }
}
