import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class AdvertisementsPage extends PageBase {

    public AdvertisementsPage(WebDriver driver) {
        super(driver);
    }
    
    public int downloadImages(String saveDirectory, int pages) throws IOException {
        int imageCount = 0;
        for (int i = 0; i < pages; i++) {
            if (i > 0) {
                this.waitAndReturnElement(
                    By.xpath("//div[@id='ipsLayout_mainArea']//li[@class='ipsPagination_next']/a")
                ).click();
            }
            List<WebElement> imageDivs = this.driver.findElements(By.xpath("//td/div"));
            for (WebElement imageDiv : imageDivs) {
                String styleAttribute = imageDiv.getAttribute("style");
                String imageUrl = styleAttribute.split("url\\(")[1].split("\\);")[0];
                imageUrl = "https://" + imageUrl.substring(3, imageUrl.length() - 1);
                if (imageUrl.contains("nocamera.png")) {
                    continue;
                }
                URL url = new URL(imageUrl);
                String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                String newFolder = Paths.get(saveDirectory, String.valueOf(i + 1)).toString();
                Files.createDirectories(Paths.get(newFolder));
                String savePath = Paths.get(newFolder, fileName).toString();

                this.downloadImage(savePath, url);
                imageCount++;
            }
        }
        return imageCount;
    }

    public void downloadImage(String savePath, URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        connection.setRequestProperty("Accept", "image/avif,image/webp,image/apng,image/*,*/*;q=0.8");
        connection.setRequestProperty("Referer", driver.getCurrentUrl());

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try (InputStream inputStream = connection.getInputStream()) {
                Files.copy(inputStream, Paths.get(savePath));
            }
        } else {
            throw new IOException("Downloading failed, response code: " + responseCode);
        }
    }
}
