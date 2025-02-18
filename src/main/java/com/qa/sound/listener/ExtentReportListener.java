package com.qa.sound.listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class ExtentReportListener implements ITestListener {

    public static String filePath = "./reports/";
    public static String fileName = "extent-report.html";

    public static WebDriver driver;
    public static ExtentReports extentReports = init();
    public static ExtentTest test;
    public static ExtentSparkReporter extentSparkReporter;
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    public static ExtentReports init() {
        extentReports = new ExtentReports();
        extentSparkReporter = new ExtentSparkReporter(filePath + fileName);
        extentSparkReporter.config().setReportName("Test Execution Report");
        extentReports.attachReporter(extentSparkReporter);
        return extentReports;
    }

    @Override
    public synchronized void onStart(ITestContext context) {
        System.out.println("Test Suit Execution Started");
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        System.out.println("Test Suit Execution Ended");
        extentReports.flush();
        extentTest.remove();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        System.out.println(methodName + " Test Started");
        test = extentReports.createTest(methodName, result.getMethod().getDescription());
        extentTest.set(test);
        extentTest.get().getModel().setStartTime(getTime(result.getStartMillis()));
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        System.out.println(methodName + " Test Succeed");
        extentTest.get().pass(methodName + " Test Succeed");
        extentTest.get().getModel().setStartTime(getTime(result.getEndMillis()));
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        System.out.println(methodName + " Test Failed");
        extentTest.get().fail(methodName + " Test Failed");
        extentTest.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshot(methodName), methodName).build());
        extentTest.get().getModel().setStartTime(getTime(result.getEndMillis()));
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        System.out.println(methodName + " Test Skipped");
        extentTest.get().skip(methodName + " Test Skipped");
        extentTest.get().skip(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshot(methodName), methodName).build());
        extentTest.get().getModel().setStartTime(getTime(result.getEndMillis()));
    }

    public Date getTime(Long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.getTime();
    }

    public static WebDriver getDriver(WebDriver driver) {
        return ExtentReportListener.driver =driver;
    }

    public static String getScreenshot(String methodName) {
        File srcFile = ((TakesScreenshot) getDriver(driver)).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis() + ".jpg";
        File filePath = new File(path);
        try {
            FileHandler.copy(srcFile, filePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return  ((TakesScreenshot) getDriver(driver)).getScreenshotAs(OutputType.BASE64);
    }
}
