Feature: Validating Shop API's
  @Login @Regression
  Scenario Outline: Verify if user is Successfully logged in
    Given Login Request Payload with "<email>" "<password>"
    When user calls "LoginAPI" with "POST" http request
    Then the API call got success with status code 200
    And "message" in response body is "Login Successfully"
    And get token using "token" json path
    And get user_id using "userId" json path

    Examples:
      |email	   | password  |
      |jd@gmail.com|  User123! |
  @AddProduct @Regression
  Scenario: Verify if product is Successfully added
    Given Add Product Payload
    When user calls "addProductAPI" with "POST" http request
    Then the API call got success with status code 200
    And "message" in response body is "Product Added Successfully"
    And get product_id using "productId" json path
  @CreateOrder @Regression
  Scenario: Verify if order is Successfully created
    Given Create Order Payload
    When user calls "createOrderAPI" with "POST" http request
    Then the API call got success with status code 200
    And "message" in response body is "Order Placed Successfully"
  @DeleteProduct @Regression
  Scenario: Verify if product is Successfully deleted
    Given Delete Product Payload
    When user calls "deleteProductAPI" with "DELETE" http request
    Then the API call got success with status code 200
    And "message" in response body is "Product Deleted Successfully"