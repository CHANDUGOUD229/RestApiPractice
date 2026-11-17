import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;

import java.io.File;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class JiraAutomation {
    SessionFilter sf;

    @BeforeClass
    public void test() {
        baseURI = "";
    }

    public void sessionCreation() {
        sf = new SessionFilter();
        String res = given().log().all().header("Content-Type", "application/json")
                .body("username:rahulShety,password:master we need to sent as json format")
                .filter(sf)
                .when()
                .post("rest/auth/1/session")
                .then()
                .extract().response().asString();
        //2nd way to get session value
        JsonPath js = Helper.rawToJson(res);
        String sessionId = js.getString("session.value");
    }

    public void comment() {
        given().pathParam("key", "1001")
                .header("Content-Type", "application/json")
                .body("payload")
                .when()
                .post("/rest/api/2/issue/{key}/comment")
                .then()
                .extract().response().asString();
    }


    public void attachment() {
        baseURI = "";
        given().header("Content-Type", "mulltipart/form-data")
                .filter(sf)
                .multiPart("file", new File("testData.json"))
                .when()
                .post("resource")
                .then();
    }

    public void getDetails() {
        String res = given().filter(sf).pathParam("key", "1003")
                .queryParam("filed", "comment")
                .log().all()
                .when().get("resourse")
                .then().extract().response().asString();
    }

}
