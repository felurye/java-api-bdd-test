![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=testing-library&logoColor=white)
![Cucumber](https://img.shields.io/badge/Cucumber-23D96C?style=for-the-badge&logo=cucumber&logoColor=white)
![Rest Assured](https://img.shields.io/badge/RestAssured-000000?style=for-the-badge)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

# Java API BDD Test

Automação de testes de API REST com BDD contra a API [ServeRest](https://serverest.dev). Os cenários são escritos em Gherkin, mapeados para Java via Cucumber 7 e executados pelo JUnit Platform.

## Objetivo

Este projeto foi criado para praticar e consolidar conhecimentos em:

- Automação de testes de API REST;
- Escrita de cenários BDD com Gherkin;
- Estruturação de frameworks de teste com step definitions reutilizáveis;
- Compartilhamento de estado entre steps com PicoContainer;
- Geração de relatórios com Allure.

## Tecnologias

| Tecnologia     | Versão  | Papel                                 |
| -------------- | ------- | ------------------------------------- |
| Java           | 21      | Linguagem                             |
| Gradle         | 9.x     | Build e gerenciamento de dependências |
| Cucumber       | 7.21.1  | Engine BDD - vincula Gherkin ao Java  |
| JUnit Platform | 5.11.4  | Execução dos testes                   |
| REST Assured   | 5.5.0   | Requisições e validações HTTP         |
| Allure         | 2.29.0  | Relatórios de execução                |
| Lombok         | 1.18.36 | Geração de getters/setters nos models |
| JavaFaker      | 1.0.2   | Geração de dados dinâmicos nos testes |
| PicoContainer  | 7.21.1  | Injeção de dependência entre steps    |

## Estrutura

```
src/test/
  java/dev/serverest/
    runners/      CucumberRunner.java
    steps/        CommonSteps, UsuariosSteps, LoginSteps, ProdutosSteps
    utils/        BaseAPI.java (@Before), ScenarioContext.java (estado do cenário)
    models/       User, LoginRequest, Product
    constants/    ApiEndpoints, ApiMessages
    factories/    UserDataFactory, ProductDataFactory
  resources/
    features/     usuarios.feature, login.feature, produtos.feature
    config.properties
    junit-platform.properties
```

## Exemplo de Cenário

```gherkin
Feature: Autenticação na API ServeRest

  Scenario: Login com credenciais válidas
    Given que existe um usuário cadastrado no sistema
    When eu faço login com as credenciais do usuário
    Then o status code deve ser 200
    And o campo "message" deve ser "Login realizado com sucesso"
    And o token de autorização deve ser retornado
```

## Pré-requisitos

- Java 21+

## Como Executar

```bash
# Clonar o repositório
git clone https://github.com/felurye/java-api-bdd-test.git

# Executar todos os testes
./gradlew test

# Limpar build anterior e executar
./gradlew clean test

# Filtrar por tag
./gradlew test -Dcucumber.filter.tags="@smoke"
```

## Notas de Estudo

A pasta [`notes/`](./notes/) contém anotações sobre BDD, Gherkin, Cucumber e REST Assured, com um [índice](./notes/README.md) e um [glossário](./notes/Glossario.md) dos principais conceitos do projeto.

## Material de Apoio

- [Curso BDD com Java - QAOps](https://www.youtube.com/playlist?list=PLhJTa4U57yUuoZLHqiXXR97sMfy_Ia_3Q)
- [Documentação ServeRest](https://serverest.dev)
