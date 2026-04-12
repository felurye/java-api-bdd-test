package dev.serverest.steps;

import dev.serverest.constants.ApiEndpoints;
import dev.serverest.factories.UserDataFactory;
import dev.serverest.models.User;
import dev.serverest.utils.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class UsuariosSteps {

    private final ScenarioContext context;
    private final UserDataFactory userFactory = new UserDataFactory();

    public UsuariosSteps(ScenarioContext context) {
        this.context = context;
    }

    @Given("que existe um usuário cadastrado no sistema")
    public void createUser() {
        User user = userFactory.validUser();
        String userId = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(ApiEndpoints.USUARIOS)
                .then()
                .statusCode(201)
                .body("_id", notNullValue())
                .extract().path("_id");

        context.setLastCreatedId(userId);
        context.setLastUserEmail(user.getEmail());
        context.setLastUserPassword(user.getPassword());
    }

    @When("eu listo todos os usuários")
    public void listAllUsers() {
        Response response = given()
                .when()
                .get(ApiEndpoints.USUARIOS)
                .then()
                .extract().response();
        context.setLastResponse(response);
    }

    @And("a lista de usuários deve ser retornada")
    public void validateUsersList() {
        context.getLastResponse()
                .then()
                .body("quantidade", notNullValue())
                .body("usuarios", notNullValue());
    }

    @When("eu cadastro um novo usuário válido")
    public void createValidUser() {
        User user = userFactory.validUser();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(ApiEndpoints.USUARIOS)
                .then()
                .extract().response();
        context.setLastResponse(response);
    }

    @When("eu tento cadastrar outro usuário com o mesmo email")
    public void createUserWithDuplicateEmail() {
        User duplicate = userFactory.userWithEmail(context.getLastUserEmail());
        Response response = given()
                .contentType(ContentType.JSON)
                .body(duplicate)
                .when()
                .post(ApiEndpoints.USUARIOS)
                .then()
                .extract().response();
        context.setLastResponse(response);
    }

    @When("eu busco o usuário pelo ID")
    public void getUserById() {
        Response response = given()
                .when()
                .get(ApiEndpoints.USUARIOS + "/" + context.getLastCreatedId())
                .then()
                .extract().response();
        context.setLastResponse(response);
    }

    @When("eu busco um usuário com ID inexistente")
    public void getUserWithNonExistentId() {
        Response response = given()
                .when()
                .get(ApiEndpoints.USUARIOS + "/inexistente123")
                .then()
                .extract().response();
        context.setLastResponse(response);
    }

    @When("eu atualizo os dados do usuário")
    public void updateUser() {
        User updated = userFactory.validUser();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(updated)
                .when()
                .put(ApiEndpoints.USUARIOS + "/" + context.getLastCreatedId())
                .then()
                .extract().response();
        context.setLastResponse(response);
    }

    @When("eu excluo o usuário")
    public void deleteUser() {
        Response response = given()
                .when()
                .delete(ApiEndpoints.USUARIOS + "/" + context.getLastCreatedId())
                .then()
                .extract().response();
        context.setLastResponse(response);
    }
}
