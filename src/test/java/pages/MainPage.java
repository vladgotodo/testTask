package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

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
}
