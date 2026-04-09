# Especificação por Exemplo

### O que é um cenário?

- Um cenário é um exemplo concreto de uma regra de negócio
- Um cenário usa exemplos concretos para explicar ideias abstratas

### Formato

**Dado** > Pré-condição
**Quando** > Ação
**Então** > Resultado esperado

### Cenário Abstrato vs Concreto

```gherkin
#language: pt-br
#encoding: UTF-8

Funcionalidade: Login
    #Cenário concreto
    Cenário: Realizar login
    Dado que eu esteja na página de login
    Quando logo com o admin "admin@gmail.com"
    Então devo ver a mensagem de usuário logado com sucesso
    E o nome do usuário deve aparecer ao lado direito do menu

    #Cenário abstrato
    Cenário: Realizar login
    Quando logo com um usuário admin
    Então devo ver a mensagem de usuário logado com sucesso
    E o nome de usuário deve aparecer ao lado direito do menu
```

### O que especificar?

- Queremos responder a pergunta: "Como o sistema se comporta?"
- Queremos descrever o que o sistema faz e não como ele faz

```gherkin
#language: pt-br
#encoding: UTF-8

Funcionalidade: Buscar produto
    #Especificação de como o sistema faz
    Cenário: Buscar produto
    Dado que eu abra o Firefox
    Quando eu abro o site "www.amaxon.com.br"
    Então seleciono a busca
    E digito "smartphone"
    E clico no botão de buscar
    E clico em "Apple" no filtro de busca
    E clico em "Apple" no filtro de "Marca"
    Então devo ver somente os produtos da Apple

    #Especificação de o que o sistema faz
    Cenário: Buscar produto
    Quando pesquiso por "smartphone" na Amazon BR
    E filtro o resultado por "Apple"
    Então somente os produtos da Apple são mostrados
```

### Boas Práticas no Uso de BDD

- Descrever comportamento, não implementação técnica.
- Escrever cenários focados na regra de negócio.
- Evitar excesso de detalhes técnicos no Gherkin.
- Manter cenários curtos e objetivos.
- Cada cenário deve testar apenas um comportamento.

### BDD não é apenas Automação

- Um erro comum é pensar que BDD é apenas escrever testes com Gherkin.
- BDD é прежде de tudo uma **metodologia de colaboração e especificação**.
- A automação é consequência, não objetivo principal.
- O valor real está na discussão e alinhamento antes da implementação.

### Ferramentas Comuns para BDD em API Testing

- **Cucumber** → Framework mais popular para BDD.
- **Rest Assured** → Muito usado com Java para testes de API.
- **JUnit/TestNG** → Execução dos testes.
- **Postman/Newman** → Pode complementar testes exploratórios.

<br>

---

<p align="center"><i>Notas baseadas no curso de <a href="https://www.youtube.com/playlist?list=PLhJTa4U57yUuoZLHqiXXR97sMfy_Ia_3Q">BDD com Java do QAOps.</a></i></p>
