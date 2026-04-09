package org.httpbin.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetSteps {

    private Response response;

    @When("eu faço uma requisição GET para {string}")
    public void sendGetRequest(String path) {
        response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(path);
    }

    @Then("o status code deve ser {int}")
    public void validateStatusCode(int statusCode) {
        response.then()
                .log().all()
                .statusCode(statusCode);
    }

    @And("o campo {string} deve ser {string}")
    public void validateField(String field, String expectedValue) {
        assertEquals(expectedValue, response.jsonPath().getString(field));
    }
}
