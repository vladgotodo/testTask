package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.log4testng.Logger;

import java.util.List;

public class RouteChoicePage {
    private static final Logger log = Logger.getLogger(MainPage.class);
    private final WebDriver driver;

    @FindBy(xpath = "//a[contains(@class,'route-select-btn')]")
    List<WebElement> freeCarriages;

    public RouteChoicePage(WebDriver driver) {
        this.driver = driver;
    }

    public RouteChoicePage selectTrain(String trainNumber) {
        try {
            driver.findElement(By.xpath("//span[@class='route-trnum' and .='" + trainNumber + "']" +
                    "/../../../../..//div[contains(text(),'Купе')]")).click();
        }
        catch (StaleElementReferenceException e) {
            log.error(e);
            driver.findElement(By.xpath("//span[@class='route-trnum' and .='" + trainNumber + "']" +
                    "/../../../../..//div[contains(text(),'Купе')]")).click();
        }
        return this;
    }

    public RouteChoicePage selectFreeCarriage() {
        freeCarriages.get(0).click();
        List<WebElement> freeSeats = driver.findElements(By.xpath("//*[@class='s-cell s-type-seat']//*[@class='s-number']"));
        for (WebElement freeSeat : freeSeats) {
            log.info("Seat number: " + freeSeat.getText() + " is free");
        }
        return this;
    }
}
