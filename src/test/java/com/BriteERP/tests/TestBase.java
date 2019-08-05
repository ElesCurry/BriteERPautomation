package com.BriteERP.tests;



import com.BriteERP.utilities.BrowserUtils;
import com.BriteERP.utilities.ConfigurationReader;
import com.BriteERP.utilities.Driver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import java.io.IOException;
public class TestBase {
    protected WebDriver driver;
    protected Actions actions;
    protected WebDriverWait wait;
    protected ExtentReports report;
    protected ExtentHtmlReporter htmlReporter;
    protected ExtentTest extentLogger;
    @BeforeTest
    public void setUpTest() {
        report = new ExtentReports();
        String filePath = System.getProperty("user.dir") + "/test-output/report.html";
        htmlReporter = new ExtentHtmlReporter(filePath);
        report.attachReporter(htmlReporter);
        htmlReporter.config().setReportName("BrightERP automated test reports");
        report.setSystemInfo("Environment", "BriteERP");
        report.setSystemInfo("OS", System.getProperty("os.name"));
        report.setSystemInfo("Browser", ConfigurationReader.get("browser"));
        report.setSystemInfo("Testing Engineer", "Admiral Kunkka");
    }
    @AfterTest
    public void tearDownTest(){
        report.flush();
        driver.quit();
    }
    @BeforeMethod
    public void setUpMethod() throws InterruptedException {
        // initilializes the webdriver object in test base class using the Driver utility
        driver = Driver.get();
        // setting implicit wait --> when elements not found, it will keep trying to find it for 10 seconds
//    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // set up the explicit wait object.
        wait = new WebDriverWait(driver, 10);
        // Actions class enable advanced interactions like double click, drag drop ...
        actions = new Actions(driver);
        // initilializes the webdriver object in test base class using the Driver utility
        driver = Driver.get();
        // setting implicit wait --> when elements not found, it will keep trying to find it for 10 seconds
//    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // set up the explicit wait object.
        wait = new WebDriverWait(driver, 10);
        // Actions class enable advanced interactions like double click, drag drop ...
        actions = new Actions(driver);
    }
    @AfterMethod
    public void tearDownMethod(ITestResult result) throws InterruptedException, IOException {
        // if the test fail
        if(result.getStatus() == ITestResult.FAILURE){
            extentLogger.fail(result.getName());
            // take screenshot and add to report0
            String screenshotLocation = BrowserUtils.getScreenshot(result.getName());
            extentLogger.addScreenCaptureFromPath(screenshotLocation);
            // capture the exception
            extentLogger.fail(result.getThrowable());
        }else if(result.getStatus() == ITestResult.SKIP){
            extentLogger.skip("Test case skipped: " + result.getName());
        }
        Thread.sleep(4000);
        Driver.closeDriver();
    }
}
