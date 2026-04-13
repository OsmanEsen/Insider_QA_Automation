package com.insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // YENİ: JavaScript ile Kesin Tıklama (Engelleri aşar)
    protected void clickWithJS(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // YENİ: Elementi ekranın ortasına kaydırma
    protected void scrollToElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    protected void sendKeys(By locator, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
    }

    protected boolean isDisplayed(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }

    protected List<WebElement> waitAllElements(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected void hoverElement(By locator) {
        Actions actions = new Actions(driver);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        actions.moveToElement(element).perform();
    }
    
    public void acceptCookiesIfPresent() {
        try {
            By cookieAccept = By.id("wt-cli-accept-all-btn");
            if(driver.findElements(cookieAccept).size() > 0) {
                clickWithJS(cookieAccept); // Çerezi de JS ile kapatalım garanti olsun
            }
        } catch (Exception e) {
            System.out.println("Çerez pop-up'ı bulunamadı.");
        }
    }
}