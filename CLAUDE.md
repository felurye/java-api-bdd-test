# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Executar todos os testes
./gradlew test

# Limpar build anterior e executar
./gradlew clean test

# Executar cenários por tag
./gradlew test -Dcucumber.filter.tags="@smoke"
./gradlew test -Dcucumber.filter.tags="@usuarios and not @negativo"
```

## Arquitetura

Framework de testes de API REST com BDD contra `https://serverest.dev`. Os cenários são escritos em Gherkin (`.feature`), mapeados para Java via Cucumber 7, e executados pelo JUnit Platform.

### Fluxo de execução

```
CucumberRunner → features/*.feature → steps/*Steps.java → REST Assured → serverest.dev
                                            ↑                   ↑
                                     utils/BaseAPI.java    ScenarioContext.java
                                     (@Before: carrega      (estado compartilhado
                                      config.properties)     via PicoContainer)
```

### Compartilhamento de estado entre steps

O projeto usa **PicoContainer** (`cucumber-picocontainer`) para injeção de dependência. `ScenarioContext` é instanciado uma vez por cenário e injetado via construtor em todas as classes de steps que precisam de estado compartilhado (response da última requisição, token de auth, ID criado, credenciais do usuário).

Toda classe de steps que precise de estado declara o construtor:
```java
public class MinhaSteps {
    private final ScenarioContext context;
    public MinhaSteps(ScenarioContext context) { this.context = context; }
}
```

### Configuração do Cucumber

Toda a configuração do engine fica em `src/test/resources/junit-platform.properties`:
- `cucumber.glue=dev.serverest` - pacote raiz onde o Cucumber procura steps e hooks
- `cucumber.plugin` - Allure + pretty para output no terminal

O `CucumberRunner` declara apenas o engine e o classpath das features. Não deve conter `@ConfigurationParameter` - conflitaria com o properties file.

### Estrutura de pacotes (`src/test/java/dev/serverest/`)

- `runners/` - `CucumberRunner`
- `steps/` - uma classe por recurso de API; `CommonSteps` contém os steps de validação reutilizados (`o status code deve ser`, `o campo X deve conter Y`)
- `utils/` - `BaseAPI` (hook `@Before`) e `ScenarioContext` (estado do cenário)
- `models/` - POJOs com Lombok (`@Data @NoArgsConstructor @AllArgsConstructor`) e anotações Jackson (`@JsonInclude(NON_NULL)`, `@JsonProperty`)
- `constants/` - `ApiEndpoints` e `ApiMessages` com as strings fixas da API
- `factories/` - `UserDataFactory` e `ProductDataFactory` usando javafaker para dados dinâmicos

### Convenções

- Nomes de métodos nas step definitions em inglês, camelCase. O texto da anotação é o contrato com o Gherkin.
- URL base nunca hardcoded: sempre via `src/test/resources/config.properties` (`base.url`).
- Campos nulos são excluídos do JSON serializado via `@JsonInclude(NON_NULL)` nos models - necessário para steps como "login sem informar o email", onde o campo simplesmente não aparece no payload.
- Relatórios Allure gerados em `allure-results/` via plugin `allure-cucumber7-jvm`.

### Adicionando novos testes

1. Criar o cenário em `src/test/resources/features/<recurso>.feature`
2. Criar a step definition em `src/test/java/dev/serverest/steps/<Recurso>Steps.java` com `ScenarioContext` injetado no construtor
3. Steps de validação genéricos (`status code`, `campo X`) já existem em `CommonSteps` - reutilizar antes de criar novos
4. Nenhuma alteração no runner ou no properties file é necessária

## Notas de estudo

`notes/` contém anotações sobre BDD e as ferramentas do projeto. Ao criar novos arquivos `.md` nessa pasta, usar o template em `.github/NOTE_TEMPLATE.md`. O índice das notas está em `notes/README.md`.
