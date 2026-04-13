package com.insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CareersPage extends BasePage {
    public CareersPage(WebDriver driver) { 
        super(driver); 
    }

    // Her türlü HTML etiketini yakalayacak yenilmez XPath'ler:
    private final By seeAllTeamsButton = By.xpath("//*[contains(text(), 'See all teams') or contains(text(), 'See All Teams')]");
    private final By qaSection = By.xpath("//*[contains(text(), 'Quality Assurance')]");

    public void goToQaJobs() throws InterruptedException {
        Thread.sleep(2000); // Sayfa animasyonları için bekleme
        
        scrollToElement(seeAllTeamsButton);
        Thread.sleep(1000); 
        clickWithJS(seeAllTeamsButton);

        scrollToElement(qaSection);
        Thread.sleep(1000);
        clickWithJS(qaSection);
    }
}