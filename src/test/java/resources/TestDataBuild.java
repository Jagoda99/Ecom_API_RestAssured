package resources;

import Pojo.LoginRequest;
import Pojo.OrderDetails;
import Pojo.Orders;
import stepDefinitions.StepDefinitions;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {

public LoginRequest loginRequestPayload(String email, String password) {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUserEmail(email);
    loginRequest.setUserPassword(password);
    return loginRequest;
}
public Orders createOrderPayload(String productId) {
    OrderDetails orderDetails = new OrderDetails();
    orderDetails.setCountry("Poland");
    orderDetails.setProductOrderedId(productId);

    List<OrderDetails> orderDetailsList = new ArrayList<>();
    orderDetailsList.add(orderDetails);

    Orders orders = new Orders();
    orders.setOrders(orderDetailsList);
    return orders;
}

}
