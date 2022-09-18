import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TestOrderCreate {
    private String[] colors;
    private Order order;
    private OrderClient orderClient = new OrderClient();
    private int track;

public TestOrderCreate(String[] colors){
    this.colors = colors;
}


    @Before
    public void setup(){
        order = Order.getOrder();

    }


    @Parameterized.Parameters
    public static Object[][] getColors() {
        return new Object[][]{
                {new String[]{"GRAY", "BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK"}},
                {new String[]{}}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с параметрами цвета")
    public void createOrderWithParams(){
        order.setColor(colors);

        int track = orderClient.createOrder(order)
                .statusCode(SC_CREATED)
                .extract().path("track");

        assertTrue(track > 0);
    }


}