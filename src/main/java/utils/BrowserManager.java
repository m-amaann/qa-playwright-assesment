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
        playwright = Playwright.create();
        Browser browserInstance = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        browser.set(browserInstance);

        Page pageInstance = browserInstance.newPage();
        page.set(pageInstance);

        // Go to application
        pageInstance.navigate("https://www.demoblaze.com/");
    }

    public static Page getPage() {
        return page.get();
    }

    public static void closeBrowser() {
        if (page.get() != null) page.get().close();
        if (browser.get() != null) browser.get().close();
        if (playwright != null) playwright.close();
    }
}
