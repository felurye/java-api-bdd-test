Feature: Endpoints GET do httpbin.org

  Scenario: Validar URL retornada na requisição GET
    When eu faço uma requisição GET para "/get"
    Then o status code deve ser 200
    And o campo "url" deve ser "https://httpbin.org/get"
