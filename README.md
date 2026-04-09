![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=testing-library&logoColor=white)
![Cucumber](https://img.shields.io/badge/Cucumber-23D96C?style=for-the-badge&logo=cucumber&logoColor=white)
![Rest Assured](https://img.shields.io/badge/RestAssured-000000?style=for-the-badge)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

# 🧪 Java API BDD Test

Automação de testes de API REST com Java, Rest Assured, JUnit e Cucumber BDD, aplicando boas práticas de qualidade de software.

## 🎯 Objetivo

Este projeto foi criado com o objetivo de praticar e consolidar conhecimentos em:

- Automação de testes de API;
- Escrita de cenários BDD com Gherkin;
- Testes de contratos REST;
- Estruturação de frameworks de teste reutilizáveis;
- Boas práticas de qualidade de software.

## 🛠 Tecnologias Utilizadas

- **Java 21**
- **JUnit**
- **Rest Assured**
- **Cucumber**
- **Gherkin**
- **Gradle**

## 🧪 Exemplo de Cenário BDD

```gherkin
Feature: Validação de API de usuários

  Scenario: Buscar usuário com sucesso
    Given que possuo um endpoint válido
    When realizo uma requisição GET
    Then devo receber status code 200
```

## ▶️ Como Executar o Projeto

### Pré-requisitos

- Java 21+
- Gradle instalado

### Clone o repositório

```bash
git clone https://github.com/felurye/java-api-bdd-test.git
```

### Execute os testes

```bash
gradle test
```

## 📚 Material de Apoio

Este projeto foi desenvolvido com base no conteúdo complementar:

- [Curso do QAOps - BDD com Java](https://www.youtube.com/playlist?list=PLhJTa4U57yUuoZLHqiXXR97sMfy_Ia_3Q)