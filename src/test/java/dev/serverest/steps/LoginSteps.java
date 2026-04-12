package dev.serverest.steps;

import dev.serverest.constants.ApiEndpoints;
import dev.serverest.models.LoginRequest;
import dev.serverest.utils.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginSteps {

    private final ScenarioContext context;

    public LoginSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("eu faço login com as credenciais do usuário")
    public void loginWithStoredCredentials() {
        LoginRequest request = new LoginRequest(
                context.getLastUserEmail(),
                context.getLastUserPassword()
        );
        Response response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(ApiEndpoints.LOGIN)
                .then()
                .extract().response();
        context.setLastResponse(response);

        String token = response.jsonPath().getString("authorization");
        if (token != null) {
            context.setAuthToken(token);
        }
    }

    @When("eu faço login com email {string} e senha {string}")
    public void loginWithCredentials(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);
        Response response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(ApiEndpoints.LOGIN)
                .then()
                .extract().response();
        context.setLastResponse(response);
    }

    @When("eu faço login sem informar o email")
    public void loginWithoutEmail() {
        LoginRequest request = new LoginRequest();
        request.setPassword("qualquersenha");
        Response response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(ApiEndpoints.LOGIN)
                .then()
                .extract().response();
        context.setLastResponse(response);
    }

    @When("eu faço login sem informar a senha")
    public void loginWithoutPassword() {
        LoginRequest request = new LoginRequest();
        request.setEmail("qualquer@test.com");
        Response response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(ApiEndpoints.LOGIN)
                .then()
                .extract().response();
        context.setLastResponse(response);
    }

    @And("o token de autorização deve ser retornado")
    public void validateTokenReturned() {
        String token = context.getLastResponse().jsonPath().getString("authorization");
        assertNotNull(token, "O token de autorização não foi retornado na resposta");
    }
}
