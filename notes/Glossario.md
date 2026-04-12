# Glossário - BDD com Java

Referência rápida dos principais termos e conceitos utilizados neste projeto.

## BDD (Behavior Driven Development)

Desenvolvimento Orientado por Comportamento. Metodologia de colaboração que aproxima negócio e tecnologia por meio de exemplos concretos escritos em linguagem natural. Não é uma ferramenta - é um processo.

- [Testes de API - Estratégias e Tipos](./notes/0-testes-de-api.md)
- [O que é BDD](./1-o-que-e-bdd.md)
- [BDD Vale ou Não a Pena?](./2-bdd-vale-ou-nao-a-pena.md)
- [Especificação por Exemplo](3-especificacao-por-exemplo.md)

## Gherkin

Linguagem estruturada usada para escrever cenários BDD em arquivos `.feature`. É legível por humanos e interpretável por ferramentas como o Cucumber.

```gherkin
Feature: Nome da funcionalidade

  Scenario: Descrição do cenário
    Given  pré-condição
    When   ação
    Then   resultado esperado
```

## Feature

Arquivo `.feature` que agrupa cenários relacionados a uma mesma funcionalidade do sistema. É o ponto de partida do BDD - deve ser escrito **antes** da implementação.


## Scenario (Cenário)

Exemplo concreto de uma regra de negócio. Descreve **o que** o sistema faz em uma situação específica, não **como** ele faz.

- Veja: [Especificação por Exemplo](./3-especificacao-por-exemplo.md)


## Given / When / Then (Dado / Quando / Então)

Estrutura padrão de um cenário:

| Palavra-chave | Papel | Exemplo |
|---|---|---|
| `Given` | Pré-condição | Estado inicial do sistema |
| `When` | Ação | O que o usuário ou sistema faz |
| `Then` | Resultado esperado | O que deve ser verificado |
| `And` / `But` | Continuação | Encadeia passos do mesmo tipo |

## Step Definition

Classe Java que mapeia cada passo do Gherkin para código executável. As anotações `@Given`, `@When`, `@Then` e `@And` fazem a ligação entre o texto do cenário e o método Java.

```java
@When("eu faço uma requisição GET para {string}")
public void sendGetRequest(String path) { ... }
```

## Runner

Classe responsável por configurar e iniciar a execução dos testes Cucumber. Define onde estão as features, o glue code e os plugins de relatório.

```java
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "org.httpbin")
public class CucumberRunner {}
```

## Glue Code

Caminho(s) de pacote onde o Cucumber procura as classes de step definitions e hooks. Configurado no runner via `GLUE_PROPERTY_NAME`.


## Hook

Método executado antes ou depois de cada cenário. Usado para setup e teardown. No Cucumber Java, declarado com `@Before` e `@After`.

```java
@Before
public void setUp() { ... }
```

## Documentação Viva

Conceito onde os cenários BDD substituem documentação estática. Por serem executáveis, refletem sempre o comportamento real do sistema - nunca ficam desatualizados.

## Especificação por Exemplo

Prática de usar exemplos concretos para descrever regras de negócio, evitando ambiguidades. Base conceitual do BDD.

- Veja: [Especificação por Exemplo](./3-especificacao-por-exemplo.md)

## Flake Test

Teste instável que falha aleatoriamente sem mudança no código. Comum em testes funcionais e de UI. Um dos pontos negativos do BDD quando mal aplicado.

## REST Assured

Biblioteca Java para testes de APIs REST. Permite escrever requisições HTTP de forma fluente e encadeada, com validações integradas.

```java
given()
    .contentType(ContentType.JSON)
.when()
    .get("/endpoint")
.then()
    .statusCode(200);
```

## Allure

Framework de relatórios de teste. Gera relatórios visuais com histórico de execuções, capturas de tela e detalhes de cada cenário. Integrado ao Cucumber via `allure-cucumber7-jvm`.
