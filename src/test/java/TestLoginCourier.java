import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestLoginCourier {
    private Courier courier;
    private CourierClient courierClient = new CourierClient();
    private int courierId;

    @Before
    public void setup(){
    courier = Courier.getRandomCourier();
    courierClient.create(courier).statusCode(SC_CREATED);
    }
    @After
    public void teardown() {
        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.login(creds)
                .statusCode(SC_OK)
                .extract().path("id");
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Заходим под учетными данными рандомного курьера и проверяем код и что вернулся его айдишник")
    public void courierLoginPositive(){

        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.login(creds)
                .statusCode(SC_OK)
                .extract().path("id");

        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Авторизация курьера без атрибута логин")
    @Description("Отрпавляем запрос без атрибута логин для авторизации и проверяем ошибку")
    public void courierLoginWithoutLoginNegative(){

        CourierCredentials creds = CourierCredentials.withoutLoginAttribute(courier);
        String actual = courierClient.login(creds)
                .statusCode(SC_BAD_REQUEST)
                .extract().path("message");

        String expected = "Недостаточно данных для входа";
        assertEquals("Текс ошибки должен совпадать",expected,actual);

    }

    @Test
    @DisplayName("Авторизация курьера без атрибута пароль")
    @Description("Отрпавляем запрос без атрибута пароля для авторизации и проверяем ошибку")
    public void courierLoginWithoutPasswordNegative(){

        CourierCredentials creds = CourierCredentials.withoutPasswordAttribute(courier);
        String actual = courierClient.login(creds)
                .statusCode(SC_BAD_REQUEST)
                .extract().path("message");

        String expected = "Недостаточно данных для входа";
        assertEquals("Текс ошибки должен совпадать",expected,actual);

    }

    @Test
    @DisplayName("Авторизация курьера c неправильным логином")
    @Description("Отрпавляем запрос c неправильным логином для авторизации и проверяем ошибку")
    public void courierLoginWithIncorrectLoginNegative(){

        CourierCredentials creds = CourierCredentials.withIncorrectLogin(courier);
        String actual = courierClient.login(creds)
                .statusCode(SC_NOT_FOUND)
                .extract().path("message");

        String expected = "Учетная запись не найдена";
        assertEquals("Текс ошибки должен совпадать",expected,actual);

    }

    @Test
    @DisplayName("Авторизация курьера c неправильным паролем")
    @Description("Отрпавляем запрос c неправильным паролем для авторизации и проверяем ошибку")
    public void courierLoginWithIncorrectPasswordNegative(){

        CourierCredentials creds = CourierCredentials.withIncorrectPassword(courier);
        String actual = courierClient.login(creds)
                .statusCode(SC_NOT_FOUND)
                .extract().path("message");

        String expected = "Учетная запись не найдена";
        assertEquals("Текс ошибки должен совпадать",expected,actual);

    }

}
