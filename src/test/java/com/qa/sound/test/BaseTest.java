package com.qa.sound.test;

import com.qa.sound.listener.ExtentReportListener;
import com.qa.sound.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {

    public ExtentReportListener listener;
    public WebDriver driver;
    public Properties prop;
    public WebDriverWait wait;
    public BasePage basePage;

    @BeforeClass
    public void browserIntialization() {
        try {
            listener = new ExtentReportListener();
            prop = new Properties();
            FileInputStream file = new FileInputStream(System.getProperty("user.dir")
                    + "/src/test/java/com/qa/sound/configs/config.properties");
            prop.load(file);
            String browser = System.getProperty("browserName") != null ? System.getProperty("browserName") : prop.getProperty("browserName");
            if (browser.contains("chrome")) {
                ChromeOptions option = new ChromeOptions();
                option.addArguments("--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors",
                        "--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage");
                if (browser.contains("headless")) {
                    option.addArguments("--headless");
                }
                driver = new ChromeDriver(option);
            }
            if (browser.contains("firefox")) {
                FirefoxOptions foption = new FirefoxOptions();
                if (browser.contains("headless")) {
                    foption.addArguments("--headless");
                }
                driver = new FirefoxDriver(foption);
            } else {
                System.out.println("Provide Valid Browser Name");
            }

            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();

            driver.get(prop.getProperty("url"));

            driver.manage().timeouts().getPageLoadTimeout();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            basePage = new BasePage(driver, wait);

            driver = listener.getDriver(driver);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @AfterClass
    public void tearDown() {
        try {
            driver.quit();
            System.out.println("Browser Terminated Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
