import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.net.MalformedURLException;

import utils.Config;
import utils.FileUtils;



public class SeleniumTest {
    public WebDriver driver;
    private final String username = Config.get("username");
    private final String password = Config.get("password");
    
    @Before
    public void setup()  throws MalformedURLException  {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL(Config.get("selenium.grid.url")), options);
        driver.manage().window().maximize();
    }
    
    @Test
    public void testLoadPage() {
        MainPage mainPage = new MainPage(this.driver);
        String headerText = mainPage.getHeaderText();
        Assert.assertTrue(headerText.contains("F\u00F3rumok"));
    }

    @Test
    public void testLoginLogout() {
        MainPage mainPage = new MainPage(this.driver);
        LoginPage loginPage = mainPage.getLoginPage();
        loginPage.login(this.username, this.password);
        String usernameOnPage = "";
        try {
            usernameOnPage = mainPage.getUsernameString();
        } catch (Exception e) {
            Assert.fail("Login failed: username element not found");
        }
        Assert.assertEquals(
            "Username element field should contain the username",
            this.username, 
            usernameOnPage
        );
        mainPage.logout();
        try {
            mainPage.getLoginButton();
        } catch (Exception e) {
            Assert.fail("Logout failed: login button not found on the page");
        }
    }

    @Test
    public void testProfileAndBack() {
        MainPage mainPage = new MainPage(this.driver);
        mainPage.login(this.username, this.password);
        ProfilePage profilePage = mainPage.getProfilePage();
        String headerText = profilePage.getHeaderText();
        Assert.assertTrue(
            "Username should be visible on profile page",
            profilePage.getHeaderText().contains(this.username)
        );
        mainPage.driver.navigate().back();
        headerText = mainPage.getHeaderText();
        Assert.assertTrue(headerText.contains("F\u00F3rumok"));
    }

    @Test
    public void testChangeBirthday() {
        MainPage mainPage = new MainPage(this.driver);
        mainPage.login(this.username, this.password);
        ProfilePage profilePage = mainPage.getProfilePage();
        String generatedBirthday = profilePage.editBirthday();
        String birthdayOnProfile = profilePage.getBirthday();
        Assert.assertEquals(
            "Birthday should be changed",
            birthdayOnProfile, 
            generatedBirthday
        );
    }

    @Test
    public void testHover() {
        MainPage mainPage = new MainPage(this.driver);
        mainPage.login(this.username, this.password);
        ProfilePage profilePage = mainPage.getProfilePage();
        profilePage.menuHover(1);
        String submenuVisibility = profilePage.getSubmenuVisibility(1);
        Assert.assertEquals("Submenu should be visible.", "block", submenuVisibility);
        profilePage.menuHover(2);
        submenuVisibility = profilePage.getSubmenuVisibility(1);
        Assert.assertEquals("Submenu should be visible.", "none", submenuVisibility);
    }

    @Test
    public void testStaticPage() {
        MainPage mainPage = new MainPage(this.driver);
        CookiesPage cookiesPage = mainPage.getCookiesPage();
        String headerText = cookiesPage.getHeaderText();
        Assert.assertTrue(headerText.contains("Cookies"));
        Assert.assertEquals(
            "http://www.aboutcookies.org/",
            cookiesPage.getAboutCookiesLink()
        );
        Assert.assertEquals(
            "http://www.allaboutcookies.org/",
            cookiesPage.getAllAboutCookiesLink()
        );
        Assert.assertEquals(
            "http://tools.google.com/dlpage/gaoptout",
            cookiesPage.getGoogleAnalyticsOptOutLink()
        );
        Assert.assertTrue(
            "Element should be visible", cookiesPage.isPageImageDisplayed()
        );
        String position = cookiesPage.getPageImagePosition();
        Assert.assertEquals(
            "Position of the page image element should be static.",
            "static",
            position
        );
        Assert.assertEquals(
            "M\u00E9nemsz\u00F3l.hu", 
            cookiesPage.getMetaData("application-name")
        );
    }

    @Test
    public void testSearch() {
        String[] searchQueries = {"test","foobarbaz","tech"};
        for (String searchQuery : searchQueries) {  
            MainPage mainPage = new MainPage(this.driver);
            SearchResultPage searchResultPage = mainPage.search(searchQuery);
            String searchResultText = searchResultPage.getSearchResultText();
            Assert.assertTrue(searchResultText.contains("tal\u00E1lat"));
        }
    }

    @Test
    
    public void testUrlListLoad() {
        Map<String, String> urlMap = new HashMap<>();
        urlMap.put("F\u00F3rumok", "https://www.menemszol.hu/");
        urlMap.put("Cookies", "https://www.menemszol.hu/cookies/");
        urlMap.put("Kapcsolat", "https://www.menemszol.hu/contact/");
        urlMap.put("Keres\u00E9s a tartalomban", "https://www.menemszol.hu/search/");

        for (Map.Entry<String, String> entry : urlMap.entrySet()) {
            String title = entry.getKey();
            String url = entry.getValue();
            driver.get(url);
            String headerText = driver.findElement(By.tagName("h1")).getText();
            Assert.assertTrue(
                "Header text should contain " + title, headerText.contains(title)
            );
        }
    }

    @Test
    public void testAdvertisementsPage() {
        final String directory = Config.get("file.download.path");
        MainPage mainPage = new MainPage(this.driver);
        mainPage.login(this.username, this.password);
        AdvertisementsPage advertisementsPage = mainPage.getAdvertisementsPage();
        int imagesDownloaded = 0;
        try {
            imagesDownloaded = advertisementsPage.downloadImages(
                directory, 2
            );
        } catch (IOException e) {
            Assert.fail("The image should be able to download properly.");
        }
        Assert.assertTrue("Images should be downloaded", imagesDownloaded > 0);
        int fileCount = 0;
        try {
            fileCount = FileUtils.countFiles(directory);
        } catch (IOException e) {
            Assert.fail("The files should be able to be counted properly.");
        }
        Assert.assertTrue(
            "File count should be equal to images downloaded",
            fileCount == imagesDownloaded
        );

        // Comment this out if you want to keep the downloaded files
        try {
            FileUtils.deleteFiles(directory);
        } catch (IOException e) {
            Assert.fail("The files should be able to be deleted properly.");
        }
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
