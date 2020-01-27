@tag
Feature: Testing import of file by REST API

  @Test
  Scenario: Importing data_1.json
    Given Set Api
    When I POST to "/productJob/file/data_1.json/execute"
    Then Status code should be "201"

  @Test
  Scenario: Importing data_1.json that has already imported
    Given Set Api
    When I POST to "/productJob/file/data_1.json/execute"
    Then Status code should be "409"




