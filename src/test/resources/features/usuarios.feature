Feature: Gerenciamento de usuários no ServeRest

  Scenario: Listar todos os usuários
    When eu listo todos os usuários
    Then o status code deve ser 200
    And a lista de usuários deve ser retornada

  Scenario: Cadastrar usuário com dados válidos
    When eu cadastro um novo usuário válido
    Then o status code deve ser 201
    And o campo "message" deve conter "Cadastro realizado com sucesso"
    And o campo "_id" deve ser retornado

  Scenario: Cadastrar usuário com email duplicado
    Given que existe um usuário cadastrado no sistema
    When eu tento cadastrar outro usuário com o mesmo email
    Then o status code deve ser 400
    And o campo "message" deve conter "Este email já está sendo usado"

  Scenario: Buscar usuário por ID
    Given que existe um usuário cadastrado no sistema
    When eu busco o usuário pelo ID
    Then o status code deve ser 200

  Scenario: Buscar usuário com ID inexistente
    When eu busco um usuário com ID inexistente
    Then o status code deve ser 400

  Scenario: Atualizar dados do usuário
    Given que existe um usuário cadastrado no sistema
    When eu atualizo os dados do usuário
    Then o status code deve ser 200
    And o campo "message" deve conter "Registro alterado com sucesso"

  Scenario: Excluir usuário
    Given que existe um usuário cadastrado no sistema
    When eu excluo o usuário
    Then o status code deve ser 200
    And o campo "message" deve conter "Registro excluído com sucesso"
