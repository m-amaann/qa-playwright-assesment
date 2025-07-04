package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BrowserManager {
    private static ThreadLocal<Page> page = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static Playwright playwright;

    // init method
    public static void startBrowser() {
        System.out.println("Starting browser");
        playwright = Playwright.create();

        Browser browserInstance = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(1000)
                        .setDevtools(false)
        );
        browser.set(browserInstance);

        Page pageInstance = browserInstance.newPage();
        pageInstance.setDefaultTimeout(4000);
        page.set(pageInstance);

        // Navigate slowly
        pageInstance.navigate("https://www.demoblaze.com/");
        pageInstance.waitForLoadState();
        System.out.println("🌐 Navigated to site :");
    }

    public static Page getPage() {
        return page.get();
    }

    public static void closeBrowser() {
        System.out.println("⏳ Closing browser");
        // Give time to see the final state
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
        System.out.println("Browser closed");
    }
}
