import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



class SearchResultPage extends PageBase {

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public int getSearchResultCount() {
        WebElement searchResultCountElement = this.waitAndReturnElement(
            By.xpath("//p[@class='ipsType_sectionTitle']")
        );
        String searchResultCountText = searchResultCountElement.getText();
        String[] parts = searchResultCountText.split(" ");
        return Integer.parseInt(parts[0]);
    }

    public String getSearchResultText() {
        WebElement searchResultCountElement = this.waitAndReturnElement(
            By.xpath("//p[@class='ipsType_sectionTitle']")
        );
        return searchResultCountElement.getText();
    }

    public void search(String searchText, WebElement searchInput) {
        searchInput.sendKeys(searchText);
        searchInput.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.xpath("//div[contains(@class, 'ipsLoading')]")
        ));
    }

    public void search(String searchText) {
        WebElement searchInput = this.waitAndReturnElement(By.id("elMainSearchInput"));
        this.search(searchText, searchInput);
    }
}
