package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class RouteChoicePage {
    private static final Logger log = Logger.getLogger(RouteChoicePage.class);
    private final WebDriver driver;
    Wait<WebDriver> wait;

    @FindBy(xpath = "//a[contains(@class,'route-select-btn')]")
    List<WebElement> freeCarriages;
    @FindBy(xpath = "//*[@class='s-cell s-type-seat']//*[@class='s-number']")
    List<WebElement> freeSeats;

    public RouteChoicePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10, 1000);
    }

    public RouteChoicePage selectTrain(String trainNumber) {
        By selectedTrainXpath = By.xpath("//span[@class='route-trnum' and .='" + trainNumber + "']" +
                "/../../../../..//div[contains(text(),'Купе')]");
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(selectedTrainXpath));
            driver.findElement(selectedTrainXpath).click();
        }
        catch (StaleElementReferenceException e) {
            log.error(e);
            driver.findElement(selectedTrainXpath).click();
        }
        return this;
    }

    public RouteChoicePage selectFreeCarriage() {
        By freeCarriagesXpath = By.xpath("//a[contains(@class,'route-select-btn')]");
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(freeCarriagesXpath));
            driver.findElement(freeCarriagesXpath).click();
        }
        catch (StaleElementReferenceException e) {
            log.error(e);
            driver.findElement(freeCarriagesXpath).click();
        }
        for (WebElement freeSeat : freeSeats) {
            log.info("Seat number: " + freeSeat.getText() + " is free");
        }
        return this;
    }
}
