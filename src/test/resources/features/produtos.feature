Feature: Gerenciamento de produtos no ServeRest

  Scenario: Listar todos os produtos
    When eu listo todos os produtos
    Then o status code deve ser 200
    And a lista de produtos deve ser retornada

  Scenario: Cadastrar produto como administrador
    Given que estou autenticado como administrador
    When eu cadastro um novo produto válido
    Then o status code deve ser 201
    And o campo "message" deve conter "Cadastro realizado com sucesso"

  Scenario: Tentar cadastrar produto sem ser administrador
    Given que estou autenticado como usuário comum
    When eu tento cadastrar um produto
    Then o status code deve ser 403
    And o campo "message" deve conter "Rota exclusiva para administradores"

  Scenario: Buscar produto por ID
    Given que estou autenticado como administrador
    And que existe um produto cadastrado
    When eu busco o produto pelo ID
    Then o status code deve ser 200

  Scenario: Excluir produto como administrador
    Given que estou autenticado como administrador
    And que existe um produto cadastrado
    When eu excluo o produto
    Then o status code deve ser 200
    And o campo "message" deve conter "Registro excluído com sucesso"
