import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class TestCourierCreate {
   private Courier courier;
   private CourierClient courierClient;
   private int courierId;

    @Before
    public void setup() {
        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
    }



    @Test
    @DisplayName("Проверка успешного создания курьера")
    @Description("Создаем рандомного курьера и логинимся под учетными данными")
    public void createCourier(){
        boolean isOk = courierClient.create(courier)
                .statusCode(SC_CREATED)
                .extract().path("ok");

        assertTrue(isOk);
    }

    @Test // Текст ошибки взял из спеки
    @DisplayName("Проверка создания двух одинаковых курьеров")
    @Description("Создаем одного курьера и пытаемся дважды его зарегистрировать")
    public void createDuplicateCourier(){
        boolean isOk = courierClient.create(courier)
                .statusCode(SC_CREATED)
                .extract().path("ok");

        String actual = courierClient.create(courier)
                .statusCode(SC_CONFLICT)
                .extract().path("message");

        String expected = "Этот логин уже используется";

        assertTrue(isOk);
        assertEquals("Должен быть текст,что логин уже есть", expected, actual );
    }

    @Test
    @DisplayName("Проверка создания курьера без логина")
    @Description("Отправляем запрос на создание курьера без атрибута логина")
    public void createCourierWithoutLogin(){
        courier = Courier.getCourierWithoutLogin();

        String actual = courierClient.create(courier)
                .statusCode(SC_BAD_REQUEST)
                .extract().path("message");

        String expected = "Недостаточно данных для создания учетной записи";

        assertEquals("Должен быть текст,что данных недостаточно", expected, actual );

    }


    @Test
    @DisplayName("Проверка создания курьера без пароля")
    @Description("Отправляем запрос на создание курьера без атрибута пароля")
    public void createCourierWithoutPassword(){
        courier = Courier.getCourierWithoutPassword();

       String actual = courierClient.create(courier)
                .statusCode(SC_BAD_REQUEST)
                .extract().path("message");

       String expected = "Недостаточно данных для создания учетной записи";

       assertEquals("Должен быть текст,что данных недостаточно", expected, actual );

    }

    @Test
    @DisplayName("Проверка создания курьера без имени")
    @Description("Отправляем запрос на создание курьера без атрибута имени")
    public void createCourierWithoutFirstName(){
        courier = Courier.getCourierWithoutFirstName();

        String actual = courierClient.create(courier)
                .statusCode(SC_BAD_REQUEST)
                .extract().path("message");

        String expected = "Недостаточно данных для создания учетной записи";

        assertEquals("Должен быть текст,что данных недостаточно", expected, actual );

    }
}
