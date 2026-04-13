package com.insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) { 
        super(driver); 
    }

    // GÜNCELLENEN KISIM: Artık img etiketi değil, header-logo class'ını arıyoruz
    private final By homePageLogo = By.cssSelector(".header-logo");

    public boolean isHomePageOpened() {
        return isDisplayed(homePageLogo);
    }
}