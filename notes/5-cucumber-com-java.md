# Cucumber com Java

Guia prático de como conectar os arquivos `.feature` ao código Java neste projeto.

### Estrutura do Projeto

```
src/test/
  java/dev/serverest/
    runners/         CucumberRunner.java
    steps/           *Steps.java  (step definitions)
    utils/           BaseAPI.java (hook @Before), ScenarioContext.java
    models/          User.java, Product.java, ...
    constants/       ApiEndpoints.java, ApiMessages.java
    factories/       UserDataFactory.java, ...
  resources/
    features/        *.feature
    config.properties
    junit-platform.properties
```

### Step Definitions

Cada método Java anotado com `@Given`, `@When`, `@Then` ou `@And` mapeia um passo do Gherkin.

```java
@When("eu faço login com email {string} e senha {string}")
public void loginComCredenciais(String email, String senha) {
    // código da requisição
}

@Then("o status code deve ser {int}")
public void validarStatusCode(int statusCode) {
    context.getLastResponse().then().statusCode(statusCode);
}
```

O texto entre aspas na anotação é uma **expressão Cucumber** que casa com o passo no `.feature`. O nome do método é irrelevante para o mapeamento.

### Expressões de Parâmetro

| Expressão   | Tipo Java | Exemplo no Gherkin              |
|-------------|-----------|----------------------------------|
| `{string}`  | String    | `"valor entre aspas"`           |
| `{int}`     | int       | `200`                           |
| `{double}`  | double    | `3.14`                          |
| `{word}`    | String    | `palavraSemEspaço`              |
| `{}`        | String    | qualquer coisa (sem tipo)       |

Para capturar múltiplos grupos, cada `{expressão}` vira um parâmetro no método Java, na ordem em que aparecem.

### Hooks

Métodos executados automaticamente antes ou depois de cada cenário.

```java
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.AfterStep;

@Before
public void setUp() {
    // executado antes de CADA cenário
}

@After
public void tearDown(Scenario scenario) {
    if (scenario.isFailed()) {
        // ação em caso de falha
    }
}
```

Para controlar a ordem de execução quando há múltiplos hooks:

```java
@Before(order = 1)  // menor número = executa primeiro
public void configurarBaseUrl() { ... }

@Before(order = 2)
public void configurarHeaders() { ... }
```

Para hooks que só rodam em cenários com determinada tag:

```java
@Before("@autenticado")
public void fazerLogin() { ... }
```

### Configuração do Cucumber

Toda a configuração fica em `src/test/resources/junit-platform.properties`:

```properties
# Pacote raiz onde o Cucumber procura steps e hooks
cucumber.glue=dev.serverest

# Plugins de relatório ativos
cucumber.plugin=io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm,pretty

# Exibe nome completo dos cenários no relatório Gradle
cucumber.junit-platform.naming-strategy=long
```

O `CucumberRunner` apenas declara o engine e onde estão as features:

```java
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class CucumberRunner {}
```

Sem `@ConfigurationParameter` no runner - isso conflitaria com o properties file.

### Compartilhando Estado com PicoContainer

Em BDD, um único cenário frequentemente envolve múltiplas classes de steps. Para compartilhar estado entre elas (ex: a response de um step precisa ser validada em outro), o Cucumber usa injeção de dependência.

Este projeto usa **PicoContainer** (dependência `cucumber-picocontainer`). O mecanismo é simples: qualquer classe declarada como parâmetro de construtor é automaticamente instanciada e injetada pelo Cucumber, e a mesma instância é compartilhada entre todos os steps do mesmo cenário.

```java
// ScenarioContext.java - classe de contexto compartilhado
public class ScenarioContext {
    private Response lastResponse;
    private String authToken;

    public Response getLastResponse() { return lastResponse; }
    public void setLastResponse(Response r) { this.lastResponse = r; }
    // ...
}
```

```java
// UsuariosSteps.java - injeta o contexto via construtor
public class UsuariosSteps {
    private final ScenarioContext context;

    public UsuariosSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("eu listo todos os usuários")
    public void listarUsuarios() {
        Response response = given().when().get("/usuarios");
        context.setLastResponse(response);
    }
}
```

```java
// CommonSteps.java - valida usando o mesmo contexto
public class CommonSteps {
    private final ScenarioContext context;

    public CommonSteps(ScenarioContext context) {
        this.context = context;
    }

    @Then("o status code deve ser {int}")
    public void validarStatusCode(int codigo) {
        context.getLastResponse().then().statusCode(codigo);
    }
}
```

O PicoContainer cria uma instância de `ScenarioContext` por cenário e injeta a **mesma instância** em todos os steps. Isso garante que `UsuariosSteps` e `CommonSteps` compartilhem o mesmo estado.

### Filtrando por Tags na Execução

```bash
# Rodar apenas cenários com @smoke
gradle test -Dcucumber.filter.tags="@smoke"

# Rodar usuarios, excluindo negativos
gradle test -Dcucumber.filter.tags="@usuarios and not @negativo"

# Rodar smoke ou login
gradle test -Dcucumber.filter.tags="@smoke or @login"
```

### Fluxo Completo de Execução

```
gradle test
  └─ JUnit Platform
       └─ CucumberRunner (engine: cucumber, features: classpath:features)
            └─ junit-platform.properties (glue: dev.serverest, plugins: allure, pretty)
                 └─ Para cada .feature encontrado:
                      └─ Para cada Scenario:
                           1. @Before hooks (BaseAPI.setUp)
                           2. Executa os passos (Given/When/Then)
                           3. @After hooks
                 └─ Relatórios: allure-results/, build/reports/tests/
```

<br>

---

<p align="center"><i>Notas baseadas no curso de <a href="https://www.youtube.com/playlist?list=PLhJTa4U57yUuoZLHqiXXR97sMfy_Ia_3Q">BDD com Java do QAOps.</a></i></p>
