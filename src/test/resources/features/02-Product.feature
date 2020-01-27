@tag
Feature: Testing product REST API

  @Test
  Scenario: Get a product that its quantity is pair and lojista is pair
    Given Set Api
    When I GET to "/product/EMMS/lojistas/2"
    Then Status code should be "200"
    And Sum of list should be "1496.59"

  @Test
  Scenario: Get a product that its quantity is not pair lojista is pair
    Given Set Api
    When I GET to "/product/COP/lojistas/2"
    Then Status code should be "200"
    And Sum of list should be "369.36"

  @Test
  Scenario: Get a product that its quantity is pair and lojista is not pair
    Given Set Api
    When I GET to "/product/EMMS/lojistas/3"
    Then Status code should be "200"
    And Sum of list should be "1496.59"

  @Test
  Scenario: Get a product that its quantity is not pair lojista is not pair
    Given Set Api
    When I GET to "/product/COP/lojistas/3"
    Then Status code should be "200"
    And Sum of list should be "369.36"