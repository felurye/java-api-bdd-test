# REST Assured

Biblioteca Java para testes de APIs REST. Oferece uma DSL fluente no estilo Given/When/Then que se integra naturalmente ao BDD com Cucumber.

### Estrutura Básica

```java
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

given()
    .contentType(ContentType.JSON)
    .body(payload)
.when()
    .post("/usuarios")
.then()
    .statusCode(201)
    .body("message", containsString("Cadastro realizado com sucesso"))
    .body("_id", notNullValue());
```

- `given()` - configuração da requisição (headers, body, params)
- `when()` - ação HTTP (get, post, put, delete, patch)
- `then()` - validações e asserções sobre a resposta

### Configuração Global

Definida uma vez no hook `@Before`, vale para todas as requisições:

```java
import io.restassured.RestAssured;

RestAssured.baseURI = "https://serverest.dev"; // URL base
RestAssured.basePath = "/";                    // path base (opcional)
```

Com `baseURI` configurado, os endpoints podem ser relativos:

```java
given().when().get("/usuarios");           // GET https://serverest.dev/usuarios
given().when().post("/login");             // POST https://serverest.dev/login
given().when().delete("/produtos/" + id); // DELETE https://serverest.dev/produtos/{id}
```

### Enviando Requisições

```java
// GET com query parameter
given()
    .queryParam("nome", "João")
.when()
    .get("/usuarios");

// POST com body em JSON (objeto Java serializado pelo Jackson)
User user = new User("João", "joao@test.com", "senha123", "false");
given()
    .contentType(ContentType.JSON)
    .body(user)
.when()
    .post("/usuarios");

// PUT com path parameter
given()
    .contentType(ContentType.JSON)
    .body(updatedUser)
.when()
    .put("/usuarios/" + userId);

// DELETE com header de autenticação
given()
    .header("Authorization", token)
.when()
    .delete("/produtos/" + productId);
```

### Validando Respostas

```java
.then()
    .statusCode(200)                                       // valida status HTTP
    .body("message", equalTo("Login realizado com sucesso")) // campo exato
    .body("message", containsString("Cadastro"))           // campo contém
    .body("_id", notNullValue())                           // campo não nulo
    .body("quantidade", greaterThan(0))                    // comparação numérica
    .body("usuarios", not(empty()))                        // lista não vazia
    .header("Content-Type", containsString("application/json")); // header
```

Os matchers vêm de `org.hamcrest.Matchers`. Os mais usados:

| Matcher | Uso |
|---------|-----|
| `equalTo(valor)` | Igualdade exata |
| `containsString(texto)` | Contém a substring |
| `notNullValue()` | Não é nulo |
| `nullValue()` | É nulo |
| `greaterThan(n)` | Maior que |
| `hasSize(n)` | Lista com tamanho n |
| `not(empty())` | Coleção não vazia |

### Extraindo Dados da Resposta

Para usar valores da response em passos seguintes:

```java
// Extrair um campo
String userId = given()
    .contentType(ContentType.JSON)
    .body(user)
.when()
    .post("/usuarios")
.then()
    .statusCode(201)
    .extract().path("_id");     // extrai o campo _id como String

// Extrair o objeto Response completo
Response response = given()
    .contentType(ContentType.JSON)
    .body(loginRequest)
.when()
    .post("/login")
.then()
    .extract().response();      // guarda a response inteira

// Usar a response depois
response.then().statusCode(200);
String token = response.jsonPath().getString("authorization");
int status   = response.getStatusCode();
```

### Logging

Para debug, adicionar `.log()` antes de `when()` ou dentro de `.then()`:

```java
given()
    .log().all()           // loga toda a requisição
.when()
    .post("/login")
.then()
    .log().all()           // loga toda a resposta
    .statusCode(200);

// Logar apenas em caso de falha:
.then()
    .log().ifValidationFails()
    .statusCode(200);
```

### Serialização de Objetos

REST Assured usa Jackson automaticamente quando `jackson-databind` está no classpath. Para serializar corretamente:

```java
// Objeto Java -> JSON na requisição
User user = new User();
user.setNome("João");
user.setEmail("joao@test.com");

given()
    .contentType(ContentType.JSON)
    .body(user)             // serializado como {"nome":"João","email":"joao@test.com"}
.when()
    .post("/usuarios");
```

Para excluir campos nulos do JSON (ex: enviar apenas email, sem senha):

```java
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequest {
    private String email;
    private String password;
    // se password = null, não aparece no JSON
}
```

### Integração com Cucumber

Em projetos BDD, a response é armazenada no contexto do cenário e validada em steps separados:

```java
// UsuariosSteps.java
@When("eu cadastro um novo usuário válido")
public void cadastrarUsuario() {
    User user = userFactory.validUser();
    Response response = given()
        .contentType(ContentType.JSON)
        .body(user)
    .when()
        .post("/usuarios")
    .then()
        .extract().response();
    context.setLastResponse(response);
}

// CommonSteps.java
@Then("o status code deve ser {int}")
public void validarStatusCode(int codigo) {
    context.getLastResponse()
        .then()
        .statusCode(codigo);
}
```

<br>

---

<p align="center"><i>Notas baseadas no curso de <a href="https://www.youtube.com/playlist?list=PLhJTa4U57yUuoZLHqiXXR97sMfy_Ia_3Q">BDD com Java do QAOps.</a></i></p>
