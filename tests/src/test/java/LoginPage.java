import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class LoginPage extends PageBase {

    public LoginPage(WebDriver driver) {
        super(driver);
    }    
    
    public void login(String username, String password) {
        WebElement usernameField = this.waitAndReturnElement(By.id("auth"));
        usernameField.sendKeys(username);
        WebElement passwordField = this.waitAndReturnElement(By.id("password"));
        passwordField.sendKeys(password);

        WebElement rememberMeCheckbox = this.waitAndReturnElement(By.id("remember_me_checkbox"));
        if (rememberMeCheckbox.isSelected()) {
            rememberMeCheckbox.click();
        }
        this.waitAndReturnElement(By.id("elSignIn_submit")).click();
    }
   
}
