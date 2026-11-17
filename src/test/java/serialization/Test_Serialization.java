package serialization;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class Test_Serialization {

    //this is for learning

    @Test
    public void test() {
        AddPlaceRequest ap = new AddPlaceRequest();
        ap.setAccuracy(50);
        ap.setAddress("29, nspt, knpr 09");
        ap.setWebsite("http://google.com");
        ap.setLanguage("French-IN");
        ap.setName("chandraaa");

        List<String> list = new ArrayList<>();
        list.add("shoe park");
        list.add("shop");

        ap.setTypes(list);

        Location l = new Location();
        l.setLng(-38.383494);
        l.setLat(33.427362);
        ap.setLocation(l);

        RequestSpecification spec = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON)
                .addQueryParam("key", "qaclick123")
                .build();

        ResponseSpecification  resp=new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .build();



//        baseURI = "https://rahulshettyacademy.com";
//        String res = given().header("Content-Type", "application/json")
//                .queryParam("key", "qaclick123")
        RequestSpecification res = given().spec(spec)
                .body(ap);

        Response output= res.when()
                .post("maps/api/place/add/json")
                .then()
                .spec(resp)
                .extract().response();
      String s=  output.asString();
        System.out.println(s);



    }
}
