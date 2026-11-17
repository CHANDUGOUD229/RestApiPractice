import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class Testing {
    String placeId = "";

    //this is for learning
    //ctrl+alt+L
    @Test(priority = 1)
    public void addPlace() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String res = given()
                .header("Content-Type", "application/json")
                .queryParam("key", "qaclick123")
                .body(PayLoad.addPlacePayLoad())
                .when()
                .post("maps/api/place/add/json")
                .then()
//                .log().all()
                .assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .body("status", equalTo("OK"))
                .header("Content-Type", equalTo("application/json;charset=UTF-8"))
                .header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
                .header("Connection", equalTo("Keep-Alive"))
                .extract().response().asString();

        JsonPath js= Helper.rawToJson(res);
        placeId = js.getString("place_id");
        String status = js.getString("status");
        String scope = js.getString("scope");
        System.out.println("your place is : " + placeId);
    }

    @Test(priority = 2)
    public void getPlaceAPI() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String res = given()
                .queryParam("place_id", placeId)
                .queryParam("key", "qaclick123")
                .when().get("maps/api/place/get/json")
                .then().statusCode(200)
                .extract().response().asString();
        JsonPath js= Helper.rawToJson(res);// to prsing JSON
        String latitude = js.getString("location.longitude");
        String name = js.getString("name");

        System.out.println("your latitude : " + latitude);
        System.out.println("your name : " + name);

    }

    @Test(priority = 3)
    public void deletePlace() {
        baseURI = "https://rahulshettyacademy.com";
        String res = given().header("Content-Type", "application/json")
                .queryParam("key", "qaclick123")
                .body("{\"place_id\":\"" + placeId + "\"}")
                .when().delete("maps/api/place/delete/json")
                .then().statusCode(200)
                .extract().response().asString();
       JsonPath js= Helper.rawToJson(res);
        String status = js.getString("status");
        System.out.println("status  : " + status);

    }

}
