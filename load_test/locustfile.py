from locust import HttpUser, task, between

class N11SearchLoadTest(HttpUser):
    # Kullanıcının iki işlem arasında 1 ile 3 saniye beklemesini simüle eder
    wait_time = between(1, 3)

    def on_start(self):
        """SC-01: Her kullanıcı teste başladığında ilk olarak ana sayfayı yükler."""
        self.client.get("/", name="Ana Sayfa")

    @task(3)
    def test_valid_search(self):
        """SC-02: Geçerli ve popüler bir kelime ile arama yapma"""
        self.client.get("/arama?q=telefon", name="Arama - Basarili")

    @task(1)
    def test_invalid_search(self):
        """SC-03: Sonuç bulunamayacak anlamsız bir kelime arama"""
        self.client.get("/arama?q=olmayanesya999888", name="Arama - Sonucsuz")

    @task(1)
    def test_search_pagination(self):
        """SC-04: Arama yaptıktan sonra 2. sayfaya geçiş"""
        self.client.get("/arama?q=laptop&pg=2", name="Arama - Sayfa 2")