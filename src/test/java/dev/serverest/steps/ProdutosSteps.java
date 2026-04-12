package dev.serverest.steps;

import dev.serverest.constants.ApiEndpoints;
import dev.serverest.factories.ProductDataFactory;
import dev.serverest.factories.UserDataFactory;
import dev.serverest.models.LoginRequest;
import dev.serverest.models.Product;
import dev.serverest.models.User;
import dev.serverest.utils.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ProdutosSteps {

    private final ScenarioContext context;
    private final UserDataFactory userFactory = new UserDataFactory();
    private final ProductDataFactory productFactory = new ProductDataFactory();

    public ProdutosSteps(ScenarioContext context) {
        this.context = context;
    }

    @Given("que estou autenticado como administrador")
    public void authenticateAsAdmin() {
        User admin = userFactory.adminUser();
        given()
                .contentType(ContentType.JSON)
                .body(admin)
                .when()
                .post(ApiEndpoints.USUARIOS)
                .then()
                .statusCode(201);

        String token = given()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(admin.getEmail(), admin.getPassword()))
                .when()
                .post(ApiEndpoints.LOGIN)
                .then()
                .statusCode(200)
                .extract().path("authorization");
        context.setAuthToken(token);
    }

    @Given("que estou autenticado como usuário comum")
    public void authenticateAsRegularUser() {
        User user = userFactory.validUser();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(ApiEndpoints.USUARIOS)
                .then()
                .statusCode(201);

        String token = given()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(user.getEmail(), user.getPassword()))
                .when()
                .post(ApiEndpoints.LOGIN)
                .then()
                .statusCode(200)
                .extract().path("authorization");
        context.setAuthToken(token);
    }

    @Given("que existe um produto cadastrado")
    public void createProduct() {
        Product product = productFactory.validProduct();
        String productId = given()
                .contentType(ContentType.JSON)
                .header("Authorization", context.getAuthToken())
                .body(product)
                .when()
                .post(ApiEndpoints.PRODUTOS)
                .then()
                .statusCode(201)
                .body("_id", notNullValue())
                .extract().path("_id");
        context.setLastCreatedId(productId);
    }

    @When("eu listo todos os produtos")
    public void listAllProducts() {
        Response response = given()
                .when()
                .get(ApiEndpoints.PRODUTOS)
                .then()
                .extract().response();
        context.setLastResponse(response);
    }

    @And("a lista de produtos deve ser retornada")
    public void validateProductsList() {
        context.getLastResponse()
                .then()
                .body("quantidade", notNullValue())
                .body("produtos", notNullValue());
    }

    @When("eu cadastro um novo produto válido")
    public void createValidProduct() {
        Product product = productFactory.validProduct();
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", context.getAuthToken())
                .body(product)
                .when()
                .post(ApiEndpoints.PRODUTOS)
                .then()
                .extract().response();
        context.setLastResponse(response);
    }

    @When("eu tento cadastrar um produto")
    public void tryCreateProduct() {
        Product product = productFactory.validProduct();
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", context.getAuthToken())
                .body(product)
                .when()
                .post(ApiEndpoints.PRODUTOS)
                .then()
                .extract().response();
        context.setLastResponse(response);
    }

    @When("eu busco o produto pelo ID")
    public void getProductById() {
        Response response = given()
                .when()
                .get(ApiEndpoints.PRODUTOS + "/" + context.getLastCreatedId())
                .then()
                .extract().response();
        context.setLastResponse(response);
    }

    @When("eu excluo o produto")
    public void deleteProduct() {
        Response response = given()
                .header("Authorization", context.getAuthToken())
                .when()
                .delete(ApiEndpoints.PRODUTOS + "/" + context.getLastCreatedId())
                .then()
                .extract().response();
        context.setLastResponse(response);
    }
}
