package com.qa.sound.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptExecuterUtil {

    public WebDriver driver;

    public JavaScriptExecuterUtil(WebDriver driver) {
        this.driver = driver;
    }

    public String getEleTextByJS(WebElement element){
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        return js.executeScript("return document.textContent()",element).toString();
    }

    public String getPageTitleByJS(){
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        return js.executeScript("return document.title").toString();
    }

    public void doClickByJS(By locator){
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].click()",driver.findElement(locator));
    }

    public void doSendKeysByJS(By locator,String value){
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].value='"+value+"'",driver.findElement(locator));
    }

    public void doScollIntoView(WebElement element){
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView(true)",element);
    }

}
