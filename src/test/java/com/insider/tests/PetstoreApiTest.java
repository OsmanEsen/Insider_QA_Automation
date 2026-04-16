package com.insider.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PetstoreApiTest {

    // Testler boyunca kullanacağımız benzersiz bir Pet ID
    long petId = 987654321L; 

    @BeforeClass
    public void setup() {
        // API'nin ana adresi
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    // ------------------ 1. CREATE (POST) ------------------
    @Test(priority = 1)
    public void testCreatePet_Positive() {
        String petBody = "{\n" +
                "  \"id\": " + petId + ",\n" +
                "  \"name\": \"InsiderDog\",\n" +
                "  \"status\": \"available\"\n" +
                "}";

        given()
            .contentType(ContentType.JSON)
            .body(petBody)
        .when()
            .post("/pet")
        .then()
            .statusCode(200) // Başarılı kayıt kontrolü
            .body("name", equalTo("InsiderDog"));
    }

    @Test(priority = 2)
    public void testCreatePet_Negative_InvalidMethod() {
        // POST yerine PUT veya GET beklendiği yerde yanlış endpoint kullanımı
        given()
            .contentType(ContentType.JSON)
        .when()
            .post("/pet/" + petId) // Bu endpoint POST kabul etmez
        .then()
            .statusCode(405); // Method Not Allowed dönmeli
    }

    // ------------------ 2. READ (GET) ------------------
    @Test(priority = 3)
    public void testGetPet_Positive() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/pet/" + petId)
        .then()
            .statusCode(200)
            .body("name", equalTo("InsiderDog"));
    }

    @Test(priority = 4)
    public void testGetPet_Negative_NotFound() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/pet/0000000000") // Olmayan bir ID
        .then()
            .statusCode(404); // Not Found dönmeli
    }

    // ------------------ 3. UPDATE (PUT) ------------------
    @Test(priority = 5)
    public void testUpdatePet_Positive() {
        String updatedBody = "{\n" +
                "  \"id\": " + petId + ",\n" +
                "  \"name\": \"InsiderDog_Updated\",\n" +
                "  \"status\": \"sold\"\n" +
                "}";

        given()
            .contentType(ContentType.JSON)
            .body(updatedBody)
        .when()
            .put("/pet")
        .then()
            .statusCode(200)
            .body("name", equalTo("InsiderDog_Updated"))
            .body("status", equalTo("sold"));
    }

    @Test(priority = 6)
    public void testUpdatePet_Negative_MissingBody() {
        given()
            .contentType(ContentType.JSON)
            // Body eksik gönderiyoruz
        .when()
            .put("/pet")
        .then()
            .statusCode(405); // Method Not Allowed veya Bad Request döner
    }

    // ------------------ 4. DELETE (DELETE) ------------------
    @Test(priority = 7)
    public void testDeletePet_Positive() {
        given()
            .accept(ContentType.JSON)
        .when()
            .delete("/pet/" + petId)
        .then()
            .statusCode(200); // Başarıyla silindi
    }

    @Test(priority = 8)
    public void testDeletePet_Negative_AlreadyDeleted() {
        given()
            .accept(ContentType.JSON)
        .when()
            .delete("/pet/" + petId) // Silinmiş bir şeyi tekrar silmeye çalışıyoruz
        .then()
            .statusCode(404); // Not Found dönmeli
    }
}