import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;

public class TestDataByJson {

    String id = "";

    @BeforeClass
    public void setup() {
        baseURI = "http://216.10.245.166";
    }

    @Test
    public void addBook() throws IOException {

        String requestBody =
                Helper.generateStringFromResource(
                        "src/test/resources/testData.json");

        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/Library/Addbook.php");

        response.then().log().all();

        Assert.assertEquals(response.statusCode(), 200);

        JsonPath js = response.jsonPath();

        String actualMessage = js.getString("Msg");
        id = js.getString("ID");

        if ("successfully added".equalsIgnoreCase(actualMessage)) {

            System.out.println("Book created successfully");
            System.out.println("Book ID: " + id);

        } else if ("Book Already Exists".equalsIgnoreCase(actualMessage)) {

            System.out.println("Duplicate book detected");
            System.out.println("Existing Book ID: " + id);

        } else {

            Assert.fail("Unexpected API response: " + response.asString());
        }
    }
}