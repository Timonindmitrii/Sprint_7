import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static org.apache.http.HttpStatus.SC_OK;

public class OrderClient extends BaseClient {

    private final String ORDER = "/orders";
    private final String CANCEL =ORDER + "/cancel";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return getBaseSpec()
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all();
    }

    @Step("Получение заказа")
    public OrdersResponse getOrders(){
      return   getBaseSpec()
                .when()
                .get(ORDER)
                .body().as(OrdersResponse.class);

    }


}
