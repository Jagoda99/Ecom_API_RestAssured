package Ecom_API;

import Pojo.LoginRequest;
import Pojo.LoginResponse;
import Pojo.OrderDetails;
import Pojo.Orders;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcomAPITest {

    @Test
    public void ecomTest() {

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("jd@gmail.com");
        loginRequest.setUserPassword("User123!");

        RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequest);
        LoginResponse resLogin = reqLogin.when().post("/api/ecom/auth/login")
                .then().extract().response().as(LoginResponse.class);

        String token = resLogin.getToken();
        String userId = resLogin.getUserId();

//Add product
        RequestSpecification reqBaseAddProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token).build();
        RequestSpecification reqAddProduct = given().spec(reqBaseAddProduct).param("productName","T-Shirt").param("productAddedBy", userId)
                .param("productCategory", "fashion").param("productSubCategory", "shirts").param("productPrice", "11500")
                .param("productDescription", "Adidas Originals").param("productFor", "women").multiPart("productImage", new File("src//main//java//Files//images.png"));
       String resAddProduct = reqAddProduct.when().post("/api/ecom/product/add-product")
                .then().log().all().extract().response().asString();
        JsonPath js = new JsonPath(resAddProduct);
        String productId = js.get("productId");

//Create order
        RequestSpecification reqBaseCreateOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token).setContentType(ContentType.JSON).build();

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCountry("Poland");
        orderDetails.setProductOrderedId(productId);

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(orderDetails);

        Orders orders = new Orders();
        orders.setOrders(orderDetailsList);

        RequestSpecification reqCreateOrder = given().log().all().spec(reqBaseCreateOrder).body(orders);

        String resCreateOrder = reqCreateOrder.when().post("/api/ecom/order/create-order")
                .then().log().all().extract().response().asString();

/*//Delete product
        RequestSpecification reqBaseDeleteProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token).setContentType(ContentType.JSON).build();
        RequestSpecification reqDeleteProduct = given().log().all().spec(reqBaseDeleteProduct).pathParams("productId", productId);
        String resDeleteProduct = reqDeleteProduct.when().delete("/api/ecom/product/delete-product/{productId}")
                .then().log().all().extract().response().asString();
        JsonPath js1 = new JsonPath(resDeleteProduct);
        Assert.assertEquals("Product Deleted Successfully",js1.get("message"));*/
    }
}
