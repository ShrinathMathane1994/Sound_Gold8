package com.qa.sound.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementUtil {

    public WebDriver driver;
    public WebDriverWait wait;

    public ElementUtil(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPageTitle() {
        String tile = null;
        try {
            tile = driver.getTitle();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tile;
    }

    public String getEleText(By locator) {
        String text = null;
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            text = driver.findElement(locator).getText();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return text;
    }

    public String getEleAttribute(By locator, String attr) {
        String value = null;
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            value = driver.findElement(locator).getDomAttribute(attr);
        } catch (StaleElementReferenceException e) {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            driver.findElement(locator).click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return value;
    }

    public void doClick(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            driver.findElement(locator).click();
        } catch (StaleElementReferenceException e) {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            driver.findElement(locator).click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void doSendKeys(By locator, String value) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            driver.findElement(locator).sendKeys(value);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void doSelectDropdown(By locator, String value) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            Select sel = new Select(driver.findElement(locator));
            sel.selectByVisibleText(value.trim());
        } catch (StaleElementReferenceException e) {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            driver.findElement(locator).click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
