import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class CourierClient extends BaseClient {

    private final String ROOT = "/courier";
    private final String COURIER = "/courier/{courierId}";
    private final String LOGIN ="/courier/login";


    @Step("Создание нового курьера {courier}")
    public ValidatableResponse create(Courier courier) {
        return getBaseSpec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Удаление курьера {courierId}")
    public void delete(int courierId) {
        getBaseSpec()
                .pathParam("courierId", courierId)
                .when()
                .delete(COURIER)
                .then().log().all()
               .assertThat()
               .statusCode(200);
    }

    @Step("Логин {courier} курьера")
    public ValidatableResponse login(CourierCredentials creds) {
        return getBaseSpec()
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all();


    }


}
