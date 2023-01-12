package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDefinitions extends Utils{

    RequestSpecification req;
    ResponseSpecification res;
    Response response;
    TestDataBuild data =new TestDataBuild();
    static String token;
    static String userId;
    static String productId;
    @Given("Login Request Payload with {string} {string}")
    public void login_request_payload_with(String email, String password) throws IOException {
        req = given().spec(requestSpecification()).body(data.loginRequestPayload(email, password));
    }
    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String method) {
        APIResources resourceAPI=APIResources.valueOf(resource);
        System.out.println(resourceAPI.getResource());
        res =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if(method.equalsIgnoreCase("POST"))
            response =req.when().post(resourceAPI.getResource());
        else if(method.equalsIgnoreCase("GET"))
            response =req.when().get(resourceAPI.getResource());
    }
    @Then("the API call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(int code) {
        assertEquals(code, response.getStatusCode());
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String expectedValue) {
        assertEquals(getJsonPath(response,keyValue),expectedValue);
    }
    @Then("get token using {string} json path")
    public void get_token(String name) {
        token = getJsonPath(response,name);
    }
    @Then("get user_id using {string} json path")
    public void get_userId(String name) {
        userId = getJsonPath(response,name);
        System.out.println(token);
        System.out.println(userId);
    }
    @Then("get product_id using {string} json path")
    public void get_productId(String name) {
        productId = getJsonPath(response,name);
        System.out.println(productId);
    }

    @Given("Add Product Payload")
    public void add_product_payload() throws IOException {
        req = given().spec(baseRequestSpecification(token)).param("productName","T-Shirt").param("productAddedBy", userId)
                .param("productCategory", "fashion").param("productSubCategory", "shirts").param("productPrice", "11500")
                .param("productDescription", "Adidas Originals").param("productFor", "women").multiPart("productImage", new File("src//main//java//Files//images.png"));
    }
    @Given("Create Order Payload")
    public void create_order_payload() throws IOException {
        req = given().spec(baseRequestSpecification(token)).body(data.createOrderPayload(productId));
    }
    @Given("Delete Product Payload")
    public void delete_product_payload() throws IOException {
        req = given().spec(baseRequestSpecification(token)).pathParams("productId", productId);
    }
}
