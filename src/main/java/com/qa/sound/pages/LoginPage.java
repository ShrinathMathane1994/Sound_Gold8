package com.qa.sound.pages;

import com.qa.sound.utils.ElementUtil;
import com.qa.sound.utils.JavaScriptExecuterUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {

    public WebDriver driver;
    public JavaScriptExecuterUtil jsUtil;
    public ElementUtil elementUtil;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        jsUtil = new JavaScriptExecuterUtil(driver);
        elementUtil = new ElementUtil(driver);
    }

    public By emailId = By.id("TxtName");
    public By password = By.id("TxtPassword");
    public By loginBtn = By.name("command");

    public String getPageTitle() {
        return jsUtil.getPageTitleByJS();
    }

    public String verifyLoginPageTitle() {
        return elementUtil.getPageTitle();
    }

    public void doLogin(String userName, String pass) {
        try {
            jsUtil.doSendKeysByJS(emailId, userName);
            jsUtil.doSendKeysByJS(password, pass);
            jsUtil.doClickByJS(loginBtn);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
