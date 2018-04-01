package pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

public class MainPage {

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
        return this;
    }

    public MainPage enterTo(String password) {
        toTextBox.sendKeys(password);
        return this;
    }

    public MainPage enterDate(String password) {
        dateTextBox.clear();
        dateTextBox.sendKeys(password);
        return this;
    }

    public MainPage clickSubmit() throws IOException {
        final Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        TakesScreenshot scrShot =((TakesScreenshot)driver);

        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination

        File destFile = new File("C:\\Users\\Vladislav_Goncharenk\\Desktop\\testTasks\\testTask\\src\\main\\resources\\New Text Document.png");

        //Copy file at destination

        FileUtils.copyFile(srcFile, destFile);
        submitButton.click();
        return this;
    }
}
