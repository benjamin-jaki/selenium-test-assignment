import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



class MainPage extends PageBase {
    
    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.menemszol.hu/");
    }    
    
    public LoginPage getLoginPage() {
        getLoginButton().click();
        return new LoginPage(this.driver);
    }

    public void login(String username, String password) {
        LoginPage loginPage = this.getLoginPage();
        loginPage.login(username, password);
    }

    public String getUsernameString() {
        return this.waitAndReturnElement(By.id("elUserLink")).getText();
    }

    public WebElement getLoginButton() {
        return this.waitAndReturnElement(By.id("elUserSignIn"));
    }

    public void logout() {
        this.waitAndReturnElement(By.id("elUserLink")).click();
        this.waitAndReturnElement(
            By.xpath("//ul[@id='elUserLink_menu']/li/a[contains(text(), 'Kijelentkez\u00E9s')]")
        ).click();
    }

    public ProfilePage getProfilePage() {
        this.waitAndReturnElement(By.id("elUserLink")).click();
        this.waitAndReturnElement(
            By.xpath("//ul[@id='elUserLink_menu']/li/a[contains(text(), 'Profil')]")
        ).click();
        return new ProfilePage(this.driver);
    }

    public String getHeaderText() {
        return this.waitAndReturnElement(By.tagName("h1")).getText();
    }

    public CookiesPage getCookiesPage() {
        this.waitAndReturnElement(
            By.xpath("//a[contains(text(), 'Cookies')]")
        ).click();
        return new CookiesPage(this.driver);
    }
    
    public AdvertisementsPage getAdvertisementsPage() {
        this.waitAndReturnElement(
            By.xpath("//a[@href='https://www.menemszol.hu/aprohirdetes/']")
        ).click();
        return new AdvertisementsPage(this.driver);
    }

    public SearchResultPage search(String searchText) {
        SearchResultPage searchResultPage = new SearchResultPage(this.driver);
        WebElement searchInput = this.waitAndReturnElement(By.xpath("//div[@id='elSearch']//input[@type='search']"));
        searchResultPage.search(searchText, searchInput);
        return searchResultPage;
    }
}
