# Testes de API - Estratégias e Tipos

Em sistemas baseados em APIs - especialmente distribuídos em microserviços - é necessário equilibrar cobertura, custo de manutenção e tempo de execução dos testes. A escolha da estratégia certa depende diretamente da arquitetura da aplicação.

> "BDD is about conversation and collaboration before automation." - Dan North

### Monólito vs Microserviços

- Em arquiteturas **monolíticas**, uma funcionalidade está centralizada em um único sistema, o que simplifica os testes pois há menos dependências externas.
- Em arquiteturas de **microserviços**, um único fluxo pode depender da comunicação entre diversos serviços, exigindo segmentação mais clara entre os níveis de teste.

### Pirâmide de Testes

Modelo que orienta a distribuição ideal dos testes: maior quantidade na base (rápidos e baratos) e menor quantidade no topo (lentos e custosos).

```text
            /\
           /E2E\
          /------\
         /  Func  \
        /----------\
       /  Contrato  \
      /--------------\
     /  Integração    \
    /------------------\
   /     Unitários      \
  /______________________\
```

Quanto mais alto na pirâmide: maior o custo de execução, maior o tempo de feedback e maior a complexidade de manutenção.

### Tipos de Testes de API

#### Teste Funcional

Valida uma funcionalidade específica exposta pela API, geralmente cobrindo uma única regra de negócio ou endpoint.

Exemplos: login de usuário, cadastro de cliente, atualização de perfil.

#### Teste de Fluxo de Usuário

Valida uma sequência de interações que representam uma jornada real, envolvendo múltiplas chamadas consecutivas.

Exemplo de fluxo: cadastrar usuário > autenticar > realizar compra.

#### Teste de Sanidade (Smoke/Sanity)

Validação rápida para verificar se a aplicação está minimamente funcional. Muito utilizado após deploys ou novas builds.

#### Teste de Contrato

Garante que consumidores e provedores mantenham compatibilidade na comunicação. Valida estrutura, tipos e formato dos payloads trocados entre serviços.

Em arquiteturas distribuídas, alterações indevidas em contratos podem quebrar múltiplas integrações. O teste de contrato valida:

- Estrutura de payload
- Tipagem de campos
- Formato de resposta
- Compatibilidade entre consumidor e provedor

#### Teste de Integração

Valida o comportamento real entre componentes integrados. Continua sendo fundamental mesmo com testes de contrato.

Exemplos: persistência em banco de dados, comunicação com filas/mensageria, integração com serviços externos.

#### Teste Ponta a Ponta (End-to-End)

Valida o comportamento completo de um fluxo de negócio, simulando a operação do sistema do início ao fim.

### Estratégia de Execução no Pipeline de CI

Uma pipeline eficiente organiza os testes por custo e velocidade para gerar feedback o mais cedo possível:

1. Testes unitários
2. Testes de integração e contrato
3. Testes funcionais
4. Testes ponta a ponta / fluxos críticos

### Boas Práticas

- Priorizar testes rápidos e confiáveis.
- Reduzir dependência excessiva de testes ponta a ponta.
- Utilizar testes de contrato em ambientes distribuídos.
- Garantir que cada tipo de teste tenha responsabilidade bem definida.
- Adaptar a estratégia conforme contexto e arquitetura do sistema.

<br>

---

<p align="center"><i>Notas baseadas no curso de <a href="https://www.youtube.com/playlist?list=PLhJTa4U57yUuoZLHqiXXR97sMfy_Ia_3Q">BDD com Java do QAOps.</a></i></p>
