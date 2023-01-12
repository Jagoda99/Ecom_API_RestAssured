package resources;

public enum APIResources {

    LoginAPI("/api/ecom/auth/login"),
    addProductAPI("/api/ecom/product/add-product"),
    createOrderAPI("/api/ecom/order/create-order"),
    deleteProductAPI("/api/ecom/product/delete-product/{productId}");

    private String resource;

    APIResources(String resource)
    {
        this.resource=resource;
    }

    public String getResource()
    {
        return resource;
    }

}
