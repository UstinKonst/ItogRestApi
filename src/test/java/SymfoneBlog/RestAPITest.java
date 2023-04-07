package SymfoneBlog;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RestAPITest {
    @Test
    void postTest1() {
//        Авторизация с корректными данными
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "ustinovskyk")
                .formParam("password", "2e10c081f3")
                .when()
                .post("https://test-stand.gb.ru/gateway/login")
                .prettyPeek()
                .then()
                .statusCode(200);
    }
    @Test
    void postTest2() {
        //        Авторизация с некорректными данными
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "ustinovskyk")
                .formParam("password", "2e10c081f3")
                .when()
                .post("https://test-stand.gb.ru/gateway/login")
                .prettyPeek()
                .then()
                .statusCode(200);
    }
    @Test
    void postTest3() {
        //        Авторизация с некорректными данными
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "ustinovskyk")
                .formParam("password", "2e10c081f3")
                .when()
                .post("https://test-stand.gb.ru/gateway/login")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getTest1() {
//        Проверка запроса без query параметров
        JsonPath get_1 = given()
                .header("X-Auth-Token", "be7ba30ad461327015e130976ee3c589")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(get_1.get("data[0].id"), equalTo(16145));
    }
    @Test
    void getTest2() {
//        Проверка корректности элемнта "count"
        JsonPath get_2 = given()
                .header("X-Auth-Token", "be7ba30ad461327015e130976ee3c589")
                .queryParam("page", "3")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(get_2.get("meta.count"), equalTo(6));
//
    }
    @Test
    void getTest3() {
//        Проверка запроса несуществующей страницы
        JsonPath get_3 = given()
                .header("X-Auth-Token", "be7ba30ad461327015e130976ee3c589")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", "0")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(get_3.get("data[0]"), equalTo(null));
    }
    @Test
    void getTest4() {
//          Проверка запроса корректного совмещения параметров "order" и "page"
        JsonPath get_4 = given()
                .header("X-Auth-Token", "be7ba30ad461327015e130976ee3c589")
                .queryParam("order", "ASC")
                .queryParam("page", "2")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(get_4.get("data[0].title"), equalTo("Бургер"));
    }
    @Test
    void getTest5() {
//        Проверка корркетного поведения параметра "sort"
        JsonPath get_5 = given()
                .header("X-Auth-Token", "be7ba30ad461327015e130976ee3c589")
                .queryParam("sort", "createdAt")
                .queryParam("page", "2")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(get_5.get("data[0].id"), equalTo(16138));
    }
    @Test
    void getTest6() {
//          Проверка запроса чужой ленты со всеми параметрами
        JsonPath get_6 = given()
                .header("X-Auth-Token", "be7ba30ad461327015e130976ee3c589")
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", "0")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(get_6.get("data[0].title"), equalTo("жареные сосиски"));
    }
    @Test
    void getTest7() {
//            Проверка корректности элемента "prevPage"
        JsonPath get_7 = given()
                .header("X-Auth-Token", "be7ba30ad461327015e130976ee3c589")
                .queryParam("owner", "notMe")
                .queryParam("order", "DESC")
                .queryParam("page", "200")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(get_7.get("meta.prevPage"), equalTo(199));
    }
    @Test
    void getTest8() {
//            Проверка запроса чужой ленты без других параметов
        JsonPath get_8 = given()
                .header("X-Auth-Token", "be7ba30ad461327015e130976ee3c589")
                .queryParam("owner", "notMe")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(get_8.get("meta.nextPage"), equalTo(2));
    }
    @Test
    void getTest9() {
//            Проверка корректности параметра "order" = "ALL"
        JsonPath get_9 = given()
                .header("X-Auth-Token", "be7ba30ad461327015e130976ee3c589")
                .queryParam("owner", "notMe")
                .queryParam("order", "ALL")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(get_9.get("meta.count"), equalTo(9166));
    }
    @Test
    void getTest10() {
//          Проверка запрос чужой страницы с отриательным значением страницы
        JsonPath get_10 = given()
                .header("X-Auth-Token", "be7ba30ad461327015e130976ee3c589")
                .queryParam("owner", "notMe")
                .queryParam("order", "DESC")
                .queryParam("page", "-10")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(get_10.get("data[0]"), equalTo(null));
    }
}
