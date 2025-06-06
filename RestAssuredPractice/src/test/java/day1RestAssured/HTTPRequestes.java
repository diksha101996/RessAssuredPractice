package day1RestAssured;

import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class HTTPRequestes {
    String id;

    @Test(priority = 1)
    void getObjects() {
        given()
                .when()
                .get("https://api.restful-api.dev/objects")
                .then()
                .statusCode(200)
//                .body("name", equalTo("Google Pixel 6 Pro"))
                .log().all();
    }

    //@Ignore
    @Test(priority = 2)
    void createObjects() {
        HashMap data = new HashMap();
        data.put("name", "Apple MacBook Pro 16");
//        data.put("job", "leader");
        id = given()
                .contentType("application/json")
                .body(data)
                .when()
                .post("https://api.restful-api.dev/objects")
                .jsonPath().getString("id");
//                .then()
//                .statusCode(200)
//                .body("name", equalTo("Apple MacBook Pro 16"))
//                .log().all();
    }

    @Test(priority = 3, dependsOnMethods = {"createObjects"})
    void updateUser() {
        HashMap data = new HashMap();
        data.put("name", "Apple MacBook Pro Max 16");
//        data.put("job", "leader");
        given()
                .contentType("application/json")
                .body(data)
                .when()
                .put("https://api.restful-api.dev/objects/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo("Apple MacBook Pro Max 16"))
                .log().all();
    }
    @Test(priority = 4,dependsOnMethods = {"createObjects"})
    void deleteUser(){
        when()
                .delete("https://api.restful-api.dev/objects/"+id)
                .then()
                .statusCode(200)
                .log().all();
    }
}
