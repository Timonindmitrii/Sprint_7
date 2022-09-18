import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestGetOrderList {

private OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Проверка получения списка заказов")
    @Description("Проводим десериализация  ответа получения списков заказов и проверяем что объект не пустой")
    public void getListOrdersNotNull() {
        OrdersResponse response = orderClient.getOrders();

        MatcherAssert.assertThat(response, notNullValue());

    }
}
