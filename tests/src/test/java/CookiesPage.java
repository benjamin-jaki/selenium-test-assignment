import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class CookiesPage extends PageBase {
    public CookiesPage(WebDriver driver) {
        super(driver);
    }
    public String getHeaderText() {
        return this.waitAndReturnElement(By.tagName("h1")).getText();
    }

    public String getAboutCookiesLink() {
        WebElement link = this.waitAndReturnElement(By.xpath("//a[contains(text(), 'www.aboutcookies.org')]"));
        return link.getAttribute("href");
    }

    public String getAllAboutCookiesLink() {
        WebElement link = this.waitAndReturnElement(By.xpath("//a[contains(text(), 'www.allaboutcookies.org')]"));
        return link.getAttribute("href");
    }

    public String getGoogleAnalyticsOptOutLink() {
        WebElement link = this.waitAndReturnElement(By.xpath("//a[contains(text(), 'http://tools.google.com/dlpage/gaoptout')]"));
        return link.getAttribute("href");
    }
    
    public boolean isPageImageDisplayed() {
        WebElement image = this.waitAndReturnElement(By.id("elLogo"));
        return image.isDisplayed();
    }
    public String getPageImagePosition() {
        WebElement image = this.waitAndReturnElement(By.id("elLogo"));
        return image.getCssValue("position");
    }

    public String getMetaData(String attribute) {
        WebElement metaDescription = this.driver.findElement(By.xpath("//meta[@name='" + attribute + "']"));
        return metaDescription.getAttribute("content");
    }

}
