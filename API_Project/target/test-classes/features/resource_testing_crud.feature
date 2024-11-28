Feature: Resource testing CRUD

  @smoke @test2
  Scenario: Change all the active resources to inactive
    Given there are resources available
    When I retrieve the details of the resources with active status true
    Then the resource response should have a status code of 200
    When I send a PUT request to update all the resources active status should be true
    And validates the resource response with resource JSON schema

    @smoke @test4
    Scenario: Update the last resource added
      Given there are resources available
      And I retrieve the details of the resource with ID "15"
      When I send a PUT request to update the resource with ID "15"
    """
    {
      "name": "Chocolate",
      "trademark": "Nestle",
      "stock": "200",
      "price": "1.5",
      "description": "Chocolate for pastries",
      "tags": "Food",
      "active": true
    }
    """

      Then the resource response should have a status code of 200
      And the resources response should have the following details:
      | Name | Trademark | Stock | Price | Description | Tags | Active | Id |
      | Chocolate | Nestle | 200 | 1.5   | Chocolate for pastries | Food | true | 15 |
      And validates the resource response with resource JSON schema

   @smoke @regression


   Scenario: Delete all registered resources
    Given there are resources available
    When I send a DELETE request to delete all the resources
    Then the resource response should have a status code of 200
    And validates the resource response with resource JSON schema








