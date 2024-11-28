@active
Feature: Client testing CRUD

  @smoke @test1
  Scenario: Read details of an existing client
    Given there are registered clients in the system
    When I retrieve the details of the client with ID "1"
    Then the response should have a status code of 200
    And the response should have the following details:
      | Name    | LastName  | Country  | City  |Email |Phone|Gender| Id |
      | Laura | Perez      | Colombia | Bogota |laura3@gmail.com|0985768594|Female| 1  |
    And validates the response with client JSON schema



  @smoke @test1
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
      "phone":"0948574938",
      "gender": "Female"
      }
    """
    Then the response should have a status code of 200
    And the response should have the following details:
      | Name  | LastName  | Country | City     |Email |Phone|Gender| Id |
      | Laura | Perez     | Colombia   | Bogota| laura3@gmail.com|0948574938|Female| 1  |
    And validates the response with client JSON schema

  @smoke @regression @test1


  Scenario: Delete all registered clients
    Given there are registered clients in the system
    When I send a DELETE request to delete all the clients
    Then the response should have a status code of 200
    And validates the response with client list JSON schema

    @smoke @test3
      Scenario: Update a new client
      Given I send a POST request to create a client
      And I retrieve the details of the client with ID "1"
      When I send a PUT request to update the client with ID "1"
        """
    {
      "name": "Jose",
      "lastName": "Benitez",
      "country": "Bolivia",
      "city": "La Paz",
      "email": "JoseOse@gmail.com",
      "phone":"2345-555-3322",
      "gender":"Male"
    }
    """
      Then the response should have a status code of 200
      And the response should have the following details:
        | Name  | LastName  | Country | City     |Email |Phone|Gender| Id |
        | Jose | Benitez     | Bolivia   | La Paz| JoseOse@gmail.com|2345-555-3322|Male| 1  |
      And validates the response with client JSON schema

      @smoke @regression @test3
        Scenario: Delete the new client
        Given there are registered clients in the system
        When I send a DELETE request to delete all the clients
        Then the response should have a status code of 200
        And validates the response with client list JSON schema


