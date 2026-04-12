# Guia de Contribuição

## Antes de começar

- Verifique se já existe uma issue ou PR aberto para o que você quer fazer
- Para mudanças significativas, abra uma issue primeiro para discutir a abordagem

## Fluxo de trabalho

1. Faça um fork do repositório
2. Crie um branch a partir de `main` com um nome descritivo:
   - `feat/nome-da-feature`
   - `fix/descricao-do-bug`
   - `docs/descricao-da-mudanca`
3. Faça suas alterações
4. Execute os testes e garanta que todos passam: `./gradlew test`
5. Abra um Pull Request para `main`

## Adicionando novos testes

1. Escreva o cenário em `src/test/resources/features/<recurso>.feature` antes do código
2. Crie a step definition em `src/test/java/dev/serverest/steps/<Recurso>Steps.java`
3. Novos steps são descobertos automaticamente - não é necessário alterar o runner

Ao nomear métodos nas step definitions, use **inglês e camelCase** (`sendPostRequest`, `validateResponseBody`). O texto da anotação Cucumber é o contrato com o Gherkin.

## Adicionando notas de estudo

Use o template em [`.github/note-template.md`](./note-template.md) e adicione o arquivo numerado em `notes/`.

## Padrões de código

- Pacotes em minúsculo (`dev.serverest.steps`, não `dev.serverest.Steps`)
- URL base sempre via `config.properties`, nunca hardcoded
- Um arquivo `.feature` por recurso de API

## Commits

Use mensagens de commit em português, no imperativo e sem ponto final:

```
feat: adiciona cenário de POST para criação de usuário
fix: corrige asserção do campo url no cenário GET
docs: adiciona glossário em notes/
```

| Prefixo    | Uso                                      |
| ---------- | ---------------------------------------- |
| `feat`     | Nova feature ou cenário                  |
| `fix`      | Correção de bug ou cenário quebrado      |
| `docs`     | Alterações em documentação e notas       |
| `refactor` | Refatoração sem mudança de comportamento |
| `test`     | Ajustes em infraestrutura de testes      |
