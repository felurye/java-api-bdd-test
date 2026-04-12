package dev.serverest.steps;

import dev.serverest.utils.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommonSteps {

    private final ScenarioContext context;

    public CommonSteps(ScenarioContext context) {
        this.context = context;
    }

    @Then("o status code deve ser {int}")
    public void validateStatusCode(int statusCode) {
        context.getLastResponse()
                .then()
                .log().all()
                .statusCode(statusCode);
    }

    @And("o campo {string} deve ser {string}")
    public void validateFieldEquals(String field, String expectedValue) {
        context.getLastResponse()
                .then()
                .log().ifValidationFails();
        String actual = context.getLastResponse().jsonPath().getString(field);
        org.junit.jupiter.api.Assertions.assertEquals(expectedValue, actual);
    }

    @And("o campo {string} deve conter {string}")
    public void validateFieldContains(String field, String expectedValue) {
        context.getLastResponse()
                .then()
                .log().ifValidationFails();
        String actual = context.getLastResponse().jsonPath().getString(field);
        org.junit.jupiter.api.Assertions.assertTrue(
                actual != null && actual.contains(expectedValue),
                "Esperado que o campo \"" + field + "\" contivesse \"" + expectedValue
                        + "\", mas foi: \"" + actual + "\""
        );
    }

    @And("o campo {string} deve ser retornado")
    public void validateFieldNotNull(String field) {
        String actual = context.getLastResponse().jsonPath().getString(field);
        assertNotNull(actual, "Esperado que o campo \"" + field + "\" não fosse nulo");
    }
}
