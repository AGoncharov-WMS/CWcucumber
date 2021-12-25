package steps;

import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class StepDefinitions {

    @Когда("Выполнена авторизация")
    public void GET_login() {
        given()
                .baseUri("https://petstore.swagger.io/v2/user")
                .basePath("/login")
                .queryParam("username","Admin")
                .queryParam("password","Admin")
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200);
    }

    public Response POST_new_user() {
        String reqBody = "{\n" +
                "\"id\": 777999,\n" +
                "\"username\": \"Antuanetta\",\n" +
                "\"firstName\": \"Antuanetta\",\n" +
                "\"lastName\": \"Ioganna\",\n" +
                "\"Password\": \"123456\",\n" +
                "\"Email\": \"ok@ok.ru\",\n" +
                "\"userStatus\": 0\n" +
                "}";

//        System.out.println(reqBody);

        return given()
                .baseUri("https://petstore.swagger.io/v2")
                .basePath("/user")
                .contentType(ContentType.JSON)
                .body(reqBody)
                .when().post()
                .then()
                .extract().response();
    }

    public Response Delete_user(String name) {
        return given()
                .baseUri("https://petstore.swagger.io/v2/user")
                .basePath("/Antuanetta")
                .contentType(ContentType.JSON)
                .when().delete()
                .then()
                .extract().response();
    }

    @Тогда("Создаём пользователя с полным набором полей")
    public void POST_new_user_with_all_fields() {
        for (int i = 0; i < 5; i++) {
            Response response_post_new_user = POST_new_user();

            System.out.println("Создание: " + Integer.toString(i + 1) + " раз");

            if (i == 4) {
                Assert.assertEquals(response_post_new_user.jsonPath().getString("message"), "777999");
            }
        }
    }

//    @И("Ожидаю, что пользователя не возможно создать повторно")
//    public void POST_new_user_with_all_fields_second_time() {
//        Response response_post_new_user = POST_new_user();
//        Assert.assertNotEquals(response_post_new_user.jsonPath().getString("message"), "777999");
//    }

    @Когда("Проверяем наличие пользователя")
    public void Get_user() {
        given()
                .baseUri("https://petstore.swagger.io/v2/user")
                .basePath("/Antuanetta")
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200);
    }

    @Тогда("Удаляем пользователя")
    public void Del_user() {
        for (int i = 0; i < 5; i++) {
            Response response_del_user = Delete_user("Antuanetta");

            System.out.println("Удаление: " + Integer.toString(i + 1) + " раз");

            if (i == 0) {
                Assert.assertEquals(response_del_user.jsonPath().getString("message"), "Antuanetta");
            }
        }
    }

//    @И("Удаляем уже удалённого пользователя")
//    public void Second_del_user() {
//        Response response_del_user = given()
//                .baseUri("https://petstore.swagger.io/v2/user")
//                .basePath("/Antuanetta")
//                .contentType(ContentType.JSON)
//                .when().delete()
//                .then()
//                .extract().response();
//
//        Assert.assertEquals(response_del_user.statusCode(), 404);
//    }

    @Затем("Выходим из системы")
    public void Logout_user() {
        Response response_logout_user = given()
                .baseUri("https://petstore.swagger.io/v2/user")
                .basePath("/logout")
                .contentType(ContentType.JSON)
                .when().get()
                .then()
                .extract().response();

        Assert.assertEquals(response_logout_user.jsonPath().getString("message"), "ok");
    }

//    @И("Повторно выходим из системы")
//    public void Second_logout_user() {
//        Response response_second_logout_user = given()
//                .baseUri("https://petstore.swagger.io/v2/user")
//                .basePath("/logout")
//                .contentType(ContentType.JSON)
//                .when().get()
//                .then()
//                .extract().response();
//
//        Assert.assertNotEquals(response_second_logout_user.statusCode(), 200);
//    }
}
