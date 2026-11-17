import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Books {
    String id = "";

    @BeforeClass
    public void setup() {
        baseURI = "http://216.10.245.166";
    }

    @Test(priority = 1)
    public void addBook() {
        String isbn = "bk" + System.currentTimeMillis();           // unique every run
        String aisle = String.valueOf((int) (Math.random() * 100000));

        String res = given().contentType(ContentType.JSON)
                .body(PayLoad.bookPayload("isbn", "aisle"))
                .when().post("Library/Addbook.php")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = Helper.rawToJson(res);
        id = js.getString("ID");
        Assert.assertEquals(js.getString("Msg"), "successfully added");
        System.out.println("your book ID : " + id);
    }

    @Test(priority = 2, dependsOnMethods = "addBook")
    public void getBookAPI() throws InterruptedException {
        int status = 0;
        String res = "";
        // retry a few times in case the server is slow to show the new book
        for (int i = 0; i < 5; i++) {
            io.restassured.response.Response r = given()
                    .queryParam("ID", id)
                    .when().get("Library/GetBook.php");
            status = r.statusCode();
            res = r.asString();
            System.out.println("attempt " + (i + 1) + " -> " + status + " " + res);
            if (status == 200) break;
            Thread.sleep(1000);
        }
        Assert.assertEquals(status, 200, "GetBook failed: " + res);
        System.out.println("get response: " + res);
    }

    @Test(priority = 3, dependsOnMethods = "addBook")
    public void deleteBookAPI() {
        String res = given().contentType(ContentType.JSON)
                .body("{\"ID\":\"" + id + "\"}")
                .when().post("Library/DeleteBook.php")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = Helper.rawToJson(res);
        Assert.assertEquals(js.getString("msg"), "book is successfully deleted");
    }



    @DataProvider(name = "booksData")
    public Object[][] getData() {
        return new Object[][]
                {{"ojuy234", "dea24s"}, {"234dvb", "dfdw3"}, {"cdfghgfdesd", "e4321"},{"fd234567ee", "nwsdvbbvhk"}};
    }
}