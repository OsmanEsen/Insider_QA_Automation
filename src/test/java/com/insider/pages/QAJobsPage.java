package com.insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import java.util.List;
import java.util.Set;

public class QAJobsPage extends BasePage {

    public QAJobsPage(WebDriver driver) {
        super(driver);
    }

    private final By qaOpenPositionsButton = By.xpath("//div[@data-department='Quality Assurance']//a");
    private final By jobsList = By.cssSelector(".posting"); 
    
    // İlk sayfadaki 'Apply' butonu
    private final By applyButton = By.cssSelector(".posting-btn-submit");
    
    // İKİNCİ SAYFA (Detay Sayfası) - Senin attığın HTML'deki class ismini kullanarak lokatörü sabitledik
    private final By applyForThisJobButton = By.cssSelector(".postings-btn.template-btn-submit");

    public void clickQaOpenPositionsAndSwitchWindow() throws InterruptedException {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(qaOpenPositionsButton));
        scrollToElement(qaOpenPositionsButton);
        Thread.sleep(1000);
        clickWithJS(qaOpenPositionsButton);
        
        String originalWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(jobsList));
    }

    public void verifyJobsListPresence() {
        List<WebElement> jobs = waitAllElements(jobsList);
        Assert.assertTrue(jobs.size() > 0, "Lever sayfasında iş listesi boş geldi!");
    }

    public void verifyJobDetails() {
        List<WebElement> jobs = waitAllElements(jobsList);
        boolean istanbulJobFound = false;

        for (WebElement job : jobs) {
            String jobText = job.getText().toUpperCase();
            if (jobText.contains("ISTANBUL") || jobText.contains("TURKEY")) {
                istanbulJobFound = true;
                Assert.assertTrue(jobText.contains("QUALITY ASSURANCE") || jobText.contains("QA"), 
                        "İstanbul ilanında QA bulunamadı!");
            }
        }
        Assert.assertTrue(istanbulJobFound, "HATA: Sayfada hiç İstanbul ilanı bulunamadı!");
    }

    public void clickApplyButton() {
        WebElement applyBtn = wait.until(ExpectedConditions.elementToBeClickable(applyButton));
        scrollToElement(applyButton);
        clickWithJS(applyButton);
        System.out.println("İlk Apply butonuna tıklandı, detay sayfası bekleniyor...");
    }

    public void clickApplyForThisJob() {
        // Detay sayfasının yüklenmesi için kısa bir bekleme
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(applyForThisJobButton));
        scrollToElement(applyForThisJobButton);
        clickWithJS(applyForThisJobButton);
        System.out.println("Detay sayfasındaki 'apply for this job' butonuna tıklandı!");
    }
}