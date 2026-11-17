package pojo;

import io.restassured.parsing.Parser;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class Reqres {

    @Test
    public void getSingleUser() {
        baseURI = "https://reqres.in";
        UserResponse response = given().
                queryParam("page", "2").expect().defaultParser(Parser.JSON)
                .when().get("api/users").as(UserResponse.class);

        // Validate pagination details
        assertEquals(response.getPage(), 2);
        assertEquals(response.getPer_page(), 6);
        assertEquals(response.getTotal(), 12);
        assertEquals(response.getTotal_pages(), 2);

        // Validate user count
        assertEquals(response.getData().size(), 6);

        // Validate first user
        for (int i = 0; i < response.getData().size(); i++) {
            User user = response.getData().get(i);
            if (user.getFirst_name().equals("George")) {
                System.out.println(user.getEmail());
            }
        }


        User firstUser = response.getData().get(0);
        User secondUser = response.getData().get(1);
        System.out.println("Second user email ID : " + secondUser.getEmail());
        assertEquals(firstUser.getId(), 7);
        assertEquals(firstUser.getEmail(),
                "michael.lawson@reqres.in");

        assertEquals(firstUser.getFirst_name(), "Michael");
        assertEquals(firstUser.getLast_name(), "Lawson");

        // Validate support details
        assertEquals(response.getSupport().getSponsored_by(),
                "Clerk");

        // Validate metadata
//        assertEquals(response.get_meta().getPowered_by(),
//                "ReqRes");

        System.out.println("API response validation successful!");
        Meta m = response.get_meta();
        String msg = m.getMessage();
        System.out.println(msg);
        System.out.println(response.get_meta().getCta().getUrl());


    }
}
