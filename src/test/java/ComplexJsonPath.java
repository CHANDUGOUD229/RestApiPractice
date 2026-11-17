import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ComplexJsonPath {
    int sum=0;
    //this is for learning
    @Test
    public void complxJson() {

        JsonPath js = Helper.rawToJson(PayLoad.coursePrices());
        int count = js.getInt("courses.size()");
        int purchesAmount = js.getInt("dashboard.purchesAmount");
        String title = js.get("courses[0].title");
//        js.getint("courses[]")

        for(int i=0;i<count;i++){
            String titles=js.getString("courses["+i+"].title");
            int price=js.getInt("courses["+i+"].price");
            System.out.println(titles);
            System.out.println(price);

        }
  //purches amount = copys *price
        for(int i=0;i<count;i++){
            int price=js.getInt("courses["+i+"].price");
            int copy=js.getInt("courses["+i+"].copies");
            sum+=price*copy;

        }
        Assert.assertEquals(purchesAmount,sum,"both are not matching....");
        for(int i=0;i<count;i++){
            String titles=js.getString("courses["+i+"].title");
            if(titles.equals("RPA")){
                String copy=js.getString("courses["+i+"].copies");
                System.out.println("num of copys RPA sold :  "+copy);
                break;

            }


        }



        System.out.println("first course Title : " + title);
        System.out.println("count of total courses " + count);
        System.out.println("purchesAmount "+purchesAmount);


    }


}
