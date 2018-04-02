package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.assertThat;

public class MainPage {
    private static final Logger log = Logger.getLogger(MainPage.class);
    private final WebDriver driver;
    Wait<WebDriver> wait;

    @FindBy(id = "name0")
    WebElement fromTextBox;
    @FindBy(id = "name1")
    WebElement toTextBox;
    @FindBy(xpath = "//input[@title='Дата']")
    WebElement dateTextBox;
    @FindBy(id = "Submit")
    WebElement submitButton;

    ////////////Calendar popup controls
    @FindBy(id = "buttonDate")
    WebElement calendarButton;
    @FindBy(xpath = "//span[@class='prev-month']/img")
    WebElement leftArrow;
    @FindBy(xpath = "//span[@class='next-month']/img")
    WebElement rightArrow;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10, 1000);
    }

    public MainPage goTo() {
        driver.get("http://www.rzd.ru/");
        return this;
    }

    public MainPage enterFrom(String username) {
        fromTextBox.sendKeys(username);
        fromTextBox.sendKeys(Keys.TAB);
        return this;
    }

    public MainPage enterTo(String password) {
        toTextBox.sendKeys(password);
        toTextBox.sendKeys(Keys.TAB);
        return this;
    }

    public MainPage enterDate(String password) {
        dateTextBox.clear();
        dateTextBox.sendKeys(password);
        dateTextBox.sendKeys(Keys.TAB);
        return this;
    }

    public MainPage openCalendar() {
        calendarButton.click();
        return this;
    }

    public MainPage selectDateByMonthNameAndDayNumber(String month, int day) {
        WebElement chosenDay = driver.findElement(By.xpath("//div[@class='month_title']/span[text()='" + month
                + "']/../..//span[text()='" + day + "']"));
        chosenDay.click();
        return this;
    }

    public MainPage checkDateTextBoxContains(String date) {
        assertThat(dateTextBox.getAttribute("value"))
                .as("Date")
                .containsIgnoringCase(date);
        return this;
    }

    public MainPage checkMonthIsDisplayed(String month) {
        By monthHeaderXpath = By.xpath("//div[@class='month_title']/span[text()='" + month + "']");
        wait.until(ExpectedConditions.presenceOfElementLocated(monthHeaderXpath));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(monthHeaderXpath)));
        WebElement monthHeader = driver.findElement(monthHeaderXpath);
        assertThat(monthHeader.isDisplayed())
                .as("Month " + month)
                .isTrue();
        return this;
    }

    public MainPage clickLeftArrowOnCalendar() {
        wait = new WebDriverWait(driver, 10, 1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='prev-month']/img")));
        leftArrow.click();
        return this;
    }

    public MainPage clickRightArrowOnCalendar() {
        wait = new WebDriverWait(driver, 10, 1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='next-month']/img")));
        rightArrow.click();
        return this;
    }

    public MainPage clickSubmit() {
        final Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();
        return this;
    }
}
