Feature: Autenticação na API ServeRest

  Scenario: Login com credenciais válidas
    Given que existe um usuário cadastrado no sistema
    When eu faço login com as credenciais do usuário
    Then o status code deve ser 200
    And o campo "message" deve ser "Login realizado com sucesso"
    And o token de autorização deve ser retornado

  Scenario: Login com credenciais inválidas
    When eu faço login com email "naoexiste@test.com" e senha "senhaerrada"
    Then o status code deve ser 401
    And o campo "message" deve ser "Email e/ou senha inválidos"

  Scenario: Login sem informar o email
    When eu faço login sem informar o email
    Then o status code deve ser 400

  Scenario: Login sem informar a senha
    When eu faço login sem informar a senha
    Then o status code deve ser 400
