import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Oauth {
    SessionFilter session;
    String token;

    


    @BeforeClass
    public void login() {

        session = new SessionFilter();
        String res = given().queryParams("code", "")
                .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type", "authorization_code")
                .when()
                .get("https://www.googleapis.com/oauth2/v4/token").asString();
        JsonPath js = Helper.rawToJson(res);
         token =  js.get("access_token");
        System.out.println("your created token : "+token);

    }

    @Test
    public void OauthTest() {
        String res = given().log().all()
                .queryParam("access_token", token)
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php")
                .asString();
        System.out.println(res);
    }
}
