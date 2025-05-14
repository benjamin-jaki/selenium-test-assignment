import java.util.concurrent.ThreadLocalRandom;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public class ProfilePage extends PageBase {
    public ProfilePage(WebDriver driver) {
        super(driver);
    }    
    
    public String getHeaderText() {
        return this.waitAndReturnElement(By.tagName("h1")).getText();
    }

    public String editBirthday() {
        this.waitAndReturnElement(
            By.xpath("//ul[@id='elEditProfile']/li/a[span[contains(text(), 'Adatlap szerkeszt\u00E9se')]]")
        ).click();
        int randomYear = ThreadLocalRandom.current().nextInt(1875, 2026);
        int randomMonth = ThreadLocalRandom.current().nextInt(1, 13);
        int randomDay = ThreadLocalRandom.current().nextInt(1, 29);
        this.selectFromSelect(By.name("bday[year]"), String.valueOf(randomYear));
        this.selectFromSelect(By.name("bday[month]"), String.valueOf(randomMonth));
        this.selectFromSelect(By.name("bday[day]"), String.valueOf(randomDay));
        this.waitAndReturnElement(
            By.xpath("//button[contains(text(), 'Ment\u00E9s')]")
        ).click();
        String formattedMonth = String.format("%02d", randomMonth);
        String formattedDay = String.format("%02d", randomDay);
        return randomYear + "-" + formattedMonth + "-" + formattedDay;
    }

    public String getBirthday() {
        return this.waitAndReturnElement(
            By.xpath("//div[@id='elProfileInfoColumn']//li[@class='ipsDataItem']//span[@class='ipsDataItem_generic']")
        ).getText();
    }

    public void menuHover(int menuItem) throws IllegalArgumentException {
        if (menuItem < 1 || menuItem > 4) {
            throw new IllegalArgumentException("Menu item must be between 1 and 4");
        }
        WebElement parentMenuItem = this.waitAndReturnElement(
            By.xpath("//div[@id='ipsLayout_header']/nav//ul[@class='ipsClearfix']/li[" + menuItem + "]")
        );
        Actions actions = new Actions(this.driver);
        actions.moveToElement(parentMenuItem).perform();
    }

    public String getSubmenuVisibility(int menuItem) throws IllegalArgumentException {
        if (menuItem < 1 || menuItem > 4) {
            throw new IllegalArgumentException("Menu item must be between 1 and 4");
        }
        WebElement ul = null;
        try {
            ul = this.waitAndReturnElement(
                By.xpath("//div[@id='ipsLayout_header']/nav//ul[@class='ipsClearfix']/li[" + menuItem + "]/ul")
            );
        } catch (NoSuchElementException | TimeoutException e) {
            return "none";
        }
        return ul.getCssValue("display");
    }

    public String getProfileInfoText() {
        return this.waitAndReturnElement(
            By.xpath("//div[@id='elProfileInfoColumn']//li[@class='ipsDataItem']//span[@class='ipsDataItem_generic']")
        ).getText();
    }
}
