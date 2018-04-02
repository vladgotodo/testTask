package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.RouteChoicePage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class RzdTest {
    private static final Logger log = Logger.getLogger(RzdTest.class);
    static Properties prop = new Properties();
    static InputStream in;
    private static WebDriver driver;

    @BeforeMethod
    public static void setupClass() {
        try {
            in = new FileInputStream("src/main/resources/driver.properties");
            prop.load(in);
            in.close();
        } catch (IOException e) {
            log.error(e);
        }

        String driverProp = (String) prop.get("browserName");
        switch (driverProp.toLowerCase()) {
            case ("firefox"):
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
    }

    @BeforeMethod
    public void setupTest() {
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterMethod
    public void takeScreenShotOnFailure(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Date date = new Date();
            SimpleDateFormat ft =
                    new SimpleDateFormat ("yyyy-MM-dd-hh-mm-ss");
            try {
                FileUtils.copyFile(scrFile, new File("errorScreenshots\\" + testResult.getName()
                        + "-" + ft.format(date) + ".jpg"));
            } catch (IOException e) {
                log.error(e);
            }
        }
    }

    @Test
    public void test_1() {
        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);
        RouteChoicePage routeChoicePage = PageFactory.initElements(driver, RouteChoicePage.class);
        mainPage.goTo()
                .enterFrom("МОСКВА")
                .enterTo("ТУЛА")
                .enterDate("03.04.2018")
                .checkDateTextBoxContains("03.04.2018")
                .clickSubmit();
        routeChoicePage
                .selectTrain("135А")
                .selectFreeCarriage();
    }

    @Test
    public void test_2() {
        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);
        mainPage.goTo()
                .enterFrom("МОСКВА")
                .enterTo("ТУЛА")
                .enterDate("03.04.2018")
                .checkDateTextBoxContains("03.04.2018")
                .openCalendar()
                .selectDateByMonthNameAndDayNumber("Апрель", 5)
                .checkDateTextBoxContains("05.04.2018")
                .openCalendar()
                .selectDateByMonthNameAndDayNumber("Май", 10)
                .checkDateTextBoxContains("10.05.2018")
                .openCalendar()
                .selectDateByMonthNameAndDayNumber("Июнь", 30)
                .checkDateTextBoxContains("30.06.2018")
                .openCalendar()
                .clickLeftArrowOnCalendar()
                .checkMonthIsDisplayed("Март")
                .clickRightArrowOnCalendar()
                .checkMonthIsDisplayed("Июнь")
                .clickRightArrowOnCalendar()
                .checkMonthIsDisplayed("Июль");
    }
}
