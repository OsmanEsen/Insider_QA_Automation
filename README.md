# Insider QA Automation Assessment Project

Selamlar! Bu repo, QA Engineer değerlendirme süreci için hazırladığım otomasyon çözümlerini içeriyor. Projeyi tasarlarken farklı otomasyon tiplerini (UI, API, Load) parca parca gondermek yerine, her şeyin tek bir yerde ama birbirinden izole çalıştığı Unified Test Automation Framework  mimarisini tercih ettim.

## Proje Mimarisi ve Kullanılan Teknolojiler

* **UI (Web) Otomasyonu:** Java 21, Selenium WebDriver (v4.19), TestNG. BDD framework'leri (Cucumber vb.) istenmediği için proje tamamen saf **Page Object Model (POM)** prensiplerine sadık kalınarak tasarlandı.
* **API Otomasyonu:** Java 21, RestAssured, TestNG.
* **Yük (Load) Testi:** Python 3, Locust.

## 📂 Klasör Yapısı Nasıl Çalışıyor?

```text
Insider_QA_Automation/
├── src/test/java/com/insider/
│   ├── pages/        # UI testleri için Page Object sınıfları (POM)
│   ├── tests/        # Test senaryoları (UI ve API burada birleşiyor)
│   └── utils/        # Base driver ayarları ve Listener'lar
├── load_test/        # Yük testleri için tamamen izole edilmiş Python ortamı
│   ├── locustfile.py # Locust senaryoları
│   └── requirements.txt
├── pom.xml           # Java bağımlılıkları (Selenium, RestAssured, TestNG)
└── testng.xml        # Test koşumlarını yöneteceğimiz suite dosyası
