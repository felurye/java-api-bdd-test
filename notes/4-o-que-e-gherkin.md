# O que é Gherkin

Gherkin é uma linguagem de especificação estruturada usada para descrever comportamentos esperados de um sistema em linguagem natural. É o formato dos arquivos `.feature` lidos pelo Cucumber.

Seu objetivo é criar um contrato legível por todos os envolvidos - negócio, QA e desenvolvimento - antes mesmo de qualquer código ser escrito.

### Estrutura Básica

```gherkin
Feature: Autenticação de usuários

  Scenario: Login com credenciais válidas
    Given que existe um usuário cadastrado
    When eu faço login com email e senha corretos
    Then o sistema deve retornar um token de acesso
    And o status code deve ser 200
```

### Keywords Principais

| Keyword            | Papel                                                               |
| ------------------ | ------------------------------------------------------------------- |
| `Feature`          | Agrupa cenários de uma mesma funcionalidade. Descrita em uma linha. |
| `Scenario`         | Um exemplo concreto de comportamento. Deve ser independente.        |
| `Given`            | Pré-condição - estado inicial do sistema antes da ação.             |
| `When`             | Ação - o que o usuário ou sistema faz.                              |
| `Then`             | Resultado esperado - o que deve ser verificado.                     |
| `And` / `But`      | Encadeia passos do mesmo tipo (Given/When/Then anterior).           |
| `Background`       | Passos comuns a todos os cenários de uma Feature.                   |
| `Scenario Outline` | Cenário parametrizado com múltiplos conjuntos de dados.             |
| `Examples`         | Tabela de dados para o Scenario Outline.                            |

### Background

Usado para evitar repetição de passos Given em todos os cenários de uma Feature.

```gherkin
Feature: Gerenciamento de produtos

  Background:
    Given que estou autenticado como administrador

  Scenario: Cadastrar produto válido
    When eu cadastro um novo produto
    Then o status code deve ser 201

  Scenario: Buscar produto por ID
    Given que existe um produto cadastrado
    When eu busco o produto pelo ID
    Then o status code deve ser 200
```

O `Background` executa antes de **cada** cenário, não antes da feature toda.

### Scenario Outline

Permite executar o mesmo cenário com diferentes conjuntos de dados.

```gherkin
Feature: Validação de login

  Scenario Outline: Login com dados inválidos
    When eu faço login com email "<email>" e senha "<senha>"
    Then o status code deve ser <status>
    And o campo "message" deve ser "<mensagem>"

    Examples:
      | email                | senha      | status | mensagem                      |
      | naoexiste@test.com   | senhaerrada| 401    | Email e/ou senha inválidos    |
      | invalido             | 12345678   | 400    | email deve ser um email válido|
```

Cada linha da tabela `Examples` gera um cenário separado na execução.

### Tags

Tags são rótulos usados para organizar e filtrar cenários.

```gherkin
@smoke @usuarios
Feature: Gerenciamento de usuários

  @critico
  Scenario: Cadastrar usuário com dados válidos
    When eu cadastro um novo usuário válido
    Then o status code deve ser 201

  @negativo
  Scenario: Cadastrar usuário com email duplicado
    Given que existe um usuário cadastrado
    When eu tento cadastrar outro usuário com o mesmo email
    Then o status code deve ser 400
```

Para filtrar por tag na execução:

```bash
gradle test -Dcucumber.filter.tags="@smoke"
gradle test -Dcucumber.filter.tags="@smoke and not @negativo"
gradle test -Dcucumber.filter.tags="@usuarios or @login"
```

### Data Tables

Usadas para passar conjuntos de dados estruturados a um step.

```gherkin
Scenario: Cadastrar múltiplos usuários
  When eu cadastro os seguintes usuários:
    | nome        | email             | administrador |
    | João Silva  | joao@test.com     | false         |
    | Maria Admin | maria@test.com    | true          |
```

No Java, o step recebe um `DataTable`:

```java
@When("eu cadastro os seguintes usuários:")
public void cadastrarUsuarios(DataTable tabela) {
    List<Map<String, String>> rows = tabela.asMaps(String.class, String.class);
    for (Map<String, String> row : rows) {
        String nome  = row.get("nome");
        String email = row.get("email");
        // ...
    }
}
```

### Doc Strings

Para passar blocos de texto maiores a um step, como um JSON completo.

```gherkin
Scenario: Cadastrar usuário com payload específico
  When eu envio o seguinte payload:
    """
    {
      "nome": "Usuário Teste",
      "email": "teste@test.com",
      "password": "12345678",
      "administrador": "false"
    }
    """
```

### Diretiva de Idioma

Por padrão, Gherkin usa inglês. Para escrever em português:

```gherkin
#language: pt

Funcionalidade: Login
  Cenário: Login com sucesso
    Dado que existe um usuário cadastrado
    Quando eu faço login com as credenciais
    Então o status deve ser 200
```

Neste projeto os cenários são escritos em português usando as keywords em inglês (`Feature`, `Scenario`, `Given`, `When`, `Then`), que o Cucumber aceita independentemente do idioma.

### Boas Práticas

- Um cenário por comportamento - não teste duas regras no mesmo cenário.
- Evite detalhes técnicos: `When eu faço login`, não `When eu envio POST para /login`.
- Cenários devem ser independentes - um não deve depender do resultado de outro.
- Use `Background` apenas para contexto que se aplica a TODOS os cenários da Feature.
- Nomes de cenários devem descrever o resultado, não o processo.

<br>

---

<p align="center"><i>Notas baseadas no curso de <a href="https://www.youtube.com/playlist?list=PLhJTa4U57yUuoZLHqiXXR97sMfy_Ia_3Q">BDD com Java do QAOps.</a></i></p>
