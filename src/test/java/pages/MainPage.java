package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

import java.util.List;

public class MainPage {
    private static final Logger log = Logger.getLogger(MainPage.class);
    private final WebDriver driver;

    @FindBy(id = "name0")
    WebElement fromTextBox;
    @FindBy(id = "name1")
    WebElement toTextBox;
    @FindBy(xpath = "//input[@title='Дата']")
    WebElement dateTextBox;
    @FindBy(id = "Submit")
    WebElement submitButton;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public MainPage goTo() {
        driver.get("http://www.rzd.ru/");
        return this;
    }

    public MainPage enterFrom(String username) {
        fromTextBox.sendKeys(username);
        dateTextBox.sendKeys(Keys.TAB);
        return this;
    }

    public MainPage enterTo(String password) {
        toTextBox.sendKeys(password);
        dateTextBox.sendKeys(Keys.TAB);
        return this;
    }

    public MainPage enterDate(String password) {
        dateTextBox.clear();
        dateTextBox.sendKeys(password);
        dateTextBox.sendKeys(Keys.TAB);
        return this;
    }

    public MainPage clickSubmit() {
        final Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();
        return this;
    }

    public MainPage selectTrain(String trainNumber) {
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

    public MainPage selectFreeCarriage() {
        driver.findElements(By.xpath("//a[contains(@class,'route-select-btn')]")).get(0).click();
        List<WebElement> freeSeats = driver.findElements(By.xpath("//*[@class='s-cell s-type-seat']//*[@class='s-number']"));
        for (WebElement freeSeat : freeSeats) {
            log.info("Seat number: " + freeSeat.getText() + " is free");
        }
        return this;
    }
}
