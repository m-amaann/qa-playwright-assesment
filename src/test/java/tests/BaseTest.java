package tests;

import org.testng.annotations.AfterMethod; // TestNG Annotation
import org.testng.annotations.BeforeMethod;
import utils.BrowserManager;

public class BaseTest {
    @BeforeMethod
    public void setUp() {
        System.out.println("Start browser");
        BrowserManager.startBrowser();
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("Closing browser");
        BrowserManager.closeBrowser();
    }
}
