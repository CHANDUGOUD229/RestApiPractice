public class PayLoad {

    //this is for learning
    public static  String addPlacePayLoad(){
        return "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -38.383494,\n" +
                "    \"lng\": 33.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"chirra house\",\n" +
                "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                "  \"address\": \"29, narsampet, cohen 09\",\n" +
                "  \"types\": [\n" +
                "    \"shoe park\",\n" +
                "    \"shop\"\n" +
                "  ],\n" +
                "  \"website\": \"http://google.com\",\n" +
                "  \"language\": \"French-IN\"\n" +
                "}\n" +
                "\n";
    }

    public static String coursePrices(){
        return "{\n" +
                " \"dashboard\":{\n" +
                "   \"purchesAmount\":910,\n" +
                "   \"website\":\"https://www.google.com\"\n" +
                " },\n" +
                "  \"courses\":[\n" +
                "    {\n" +
                "      \"title\":\"selenium python\",\n" +
                "      \"price\":50,\n" +
                "      \"copies\":6\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\":\"Cypress\",\n" +
                "      \"price\":40,\n" +
                "      \"copies\":4\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \"title\":\"RPA\",\n" +
                "      \"price\":45,\n" +
                "      \"copies\":10\n" +
                "    }\n" +
                "    \n" +
                "  ]\n" +
                "}";
    }

    public static String bookPayload(String isbnValue,String aisleValue){
        return "{\n" +
                "\n" +
                "\"name\":\"automation selenium with Java\",\n" +
                "\"isbn\":\""+isbnValue+"\",\n" +
                "\"aisle\":\""+aisleValue+"\",\n" +
                "\"author\":\"krishivChandra\"\n" +
                "}\n";
    }


}
