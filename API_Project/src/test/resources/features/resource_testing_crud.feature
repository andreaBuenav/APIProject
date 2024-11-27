@active
Feature: Resource testing CRUD

  @smoke @test1
  Scenario: Read all the active resources
    Given there are registered clients in the system
    When I retrieve the details of the client with ID "1"
    Then the response should have a status code of 200
    And the response should have the following details:
      | Name    | LastName  | Country  | City  |Email |Phone| Id |
      | Laura | Perez      | Colombia | Bogota |laura3@gmail.com|1800-233-244| 1  |
    And validates the response with client JSON schema



  @smoke
  Scenario: Update client details
    Given there are registered clients in the system
    And I retrieve the details of the client with ID "1"
    When I send a PUT request to update the client with ID "1"
    """
    {
      "name": "Laura",
      "lastName": "Perez",
      "country": "Colombia",
      "city": "Bogota",
      "email": "laura3@gmail.com",
      "phone":"1800-233-555"
    }
    """
    Then the response should have a status code of 200
    And the response should have the following details:
      | Name  | LastName  | Country | City     |Email |Phone| Id |
      | Laura | Perez     | Colombia   | Bogota| laura3@gmail.com|1800-233-555| 1  |
    And validates the response with client JSON schema

  @smoke @regression
  Scenario: Delete all registered clients
    Given there are registered clients in the system
    When I send a DELETE request to delete all the clients
    Then the response should have a status code of 200
    And validates the response with client list JSON schema

