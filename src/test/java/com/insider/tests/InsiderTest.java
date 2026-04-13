package com.insider.tests;

import com.insider.pages.CareersPage;
import com.insider.pages.HomePage;
import com.insider.pages.QAJobsPage;
import com.insider.utils.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class InsiderTest extends BaseTest {

    @Test
    public void testInsiderQAProcess() throws InterruptedException {
        // Adım 1: Anasayfa
        getDriver().get("https://insiderone.com/");
        HomePage homePage = new HomePage(getDriver());
        homePage.acceptCookiesIfPresent(); 
        Assert.assertTrue(homePage.isHomePageOpened(), "Anasayfa açılamadı!");

        // Adım 2: Kariyer sayfası
        getDriver().get("https://insiderone.com/careers/#open-roles");
        CareersPage careersPage = new CareersPage(getDriver());
        careersPage.goToQaJobs();

        QAJobsPage qaJobsPage = new QAJobsPage(getDriver());
        qaJobsPage.clickQaOpenPositionsAndSwitchWindow();

        // Adım 3: İlan Kontrolü
        qaJobsPage.verifyJobsListPresence();
        qaJobsPage.verifyJobDetails();

        // Adım 4: Apply Süreci
        qaJobsPage.clickApplyButton(); // Listeden tıkla
        
        Thread.sleep(2000); // Detay sayfasının açılması için 2 saniye nefes payı
        
        qaJobsPage.clickApplyForThisJob(); // Detay sayfasından tıkla

        // FINAL DOĞRULAMA: URL'nin hem lever içermesini hem de /apply ile bitmesini bekle
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/apply"));

        String finalUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(finalUrl.contains("lever.co") && finalUrl.contains("/apply"), 
            "Asıl başvuru formuna ulaşılamadı! Mevcut URL: " + finalUrl);
            
        System.out.println("TEBRİKLER! Test başarıyla tamamlandı. Form sayfası: " + finalUrl);
    }
}