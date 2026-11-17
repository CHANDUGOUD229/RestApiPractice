import org.testng.Assert;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class BooksDataDriven {

    @BeforeClass
    public void setup() {
        baseURI = "http://216.10.245.166";
    }

    @DataProvider(name = "booksData")
    public Object[][] getData() {
        // {isbn, aisle}
        return new Object[][]{
                {"ojuy", "deas"},
                {"edfe", "cces"},
                {"cesd", "cwee"},
                {"fdee", "nvhk"}
        };
    }

    @Test(dataProvider = "booksData")
    public void bookLifecycle(String isbn, String aisle) {

        // make data unique so other people hitting this public server don't collide with you
        String uniqueIsbn = isbn + System.currentTimeMillis();

        // ---------- 1. ADD ----------
        String addRes = given().contentType(ContentType.JSON)
                .body(PayLoad.bookPayload(uniqueIsbn, aisle))
                .when().post("Library/Addbook.php")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath addJs = Helper.rawToJson(addRes);
        String id = addJs.getString("ID");          // local variable = thread-safe, no overwriting
        Assert.assertEquals(addJs.getString("Msg"), "successfully added");
        Assert.assertEquals(id, uniqueIsbn + aisle, "ID should be isbn + aisle");
        System.out.println("Created book ID: " + id);

        // ---------- 2. GET ----------
        Response getRes = given()
                .queryParam("ID", id)
                .when().get("Library/GetBook.php")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response();

        JsonPath getJs = getRes.jsonPath();
        Assert.assertEquals(getJs.getString("[0].isbn"), uniqueIsbn, "ISBN mismatch in GetBook");
        Assert.assertEquals(getJs.getString("[0].aisle"), aisle, "Aisle mismatch in GetBook");

        // ---------- 3. DELETE ----------
        String delRes = given().contentType(ContentType.JSON)
                .body("{\"ID\":\"" + id + "\"}")
                .when().post("Library/DeleteBook.php")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        Assert.assertEquals(Helper.rawToJson(delRes).getString("msg"),
                "book is successfully deleted");
        System.out.println("Deleted book ID: " + id);
    }
}

