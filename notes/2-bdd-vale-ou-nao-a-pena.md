# BDD Vale ou Não a Pena?

### Vantagens

1. Testes de aceitação
2. Especificações executáveis
3. Fácil de entender, torna testes mais legíveis e compreensíveis.
4. Pode ser usado para novos integrantes entenderem o sistema
5. Flexibilidade
6. Melhora comunicação entre áreas técnicas e negócio, servindo como documentação funcional
7. Ajuda na identificação de gaps de requisito antes da implementação.

### Desvantagens

1. Como são testes funcionais, podem falhar aleatoriamente (flake)
2. As features podem demorar demais para rodar
3. O pessoal do negócio pode não ler os cenários
4. Inserção de uma camada extra
5. Pode ser usado como estratégia para dados nos testes

> "Se você está usando o Cucumber como ferramenta de testes, você está fazendo o uso incorreto." - Aslak Hellesøy (Criador do Cucumber)

## Quando o BDD vale a pena?

Quando há um time unificado:

- QA normalmente faz validação e testes funcionais
- Quando há gargalos (QA ajudam DEVs e DEVs ajudam QA)
- O nível de comunicação e colaboração é muito grande
- Quando os cenários são usados como documentação viva
- Os testes fazem parte da estrategia de integração contínua CI.
- Time unico com cultura devops:
  - Todo mundo faz tudo, cada pessoa compartilha o seu conhecimento mais profundo
  - Não tem analista de negócio
  - Quando existe uma certa rotatividade

### Quando o BDD não vale a pena?

- Divisão
  - Equipe de Dev
  - Equipe de Automação
  - Equipe de QA de escrita de cenários e testes manuais
- Usado nos testes funcionais de UI
- Processo "BDD"
  - QA Manual + Analista de Negócio escrevem os cenários
  - Equipe de Automação automatiza

<br>

---

<p align="center"><i>Notas baseadas no curso de <a href="https://www.youtube.com/playlist?list=PLhJTa4U57yUuoZLHqiXXR97sMfy_Ia_3Q">BDD com Java do QAOps.</a></i></p>
