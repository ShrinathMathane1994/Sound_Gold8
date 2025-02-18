package com.qa.sound.test;

import com.qa.sound.pages.LoginPage;
import com.qa.sound.listener.Retry;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LoginPageTest extends BaseTest {

    @Test(priority = 1, enabled = true)
    public void verifyPageTitleTest() {
        String title = basePage.getPageInstance(LoginPage.class).getPageTitle();
        Assert.assertTrue(title.contains("BUSINESSNEXT"));
    }

    @Test(priority = 2, enabled = true,retryAnalyzer = Retry.class)
    public void doLoginTest() {
        basePage.getPageInstance(LoginPage.class).doLogin(prop.getProperty("userName"), prop.getProperty("password"));
    }

    @Test(priority = 3, enabled = true,retryAnalyzer = Retry.class)
    public void verifyLoginTest() {
        String headerTxt =  basePage.getPageInstance(LoginPage.class).verifyLoggedPageHeader();
        Assert.assertTrue(headerTxt.contains("Summary"));
        System.out.println("Login Successful");
    }

}
