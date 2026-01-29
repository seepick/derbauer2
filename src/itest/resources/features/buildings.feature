Feature: Building Construction
  As a player
  I want to construct and manage buildings
  So that I can expand my settlement

  Scenario: Build a house
    Given a user with 5 houses
    When the user builds 1 house
    Then the building transaction should succeed
    And the user should have 6 houses

  Scenario: Build multiple farms
    Given a user with 2 farms and 1 granaries
    When the user builds 3 farms
    Then the building transaction should succeed
    And the user should have 5 farms

  Scenario: Build a granary
    Given a user with 1 granaries
    When the user builds 2 granaries
    Then the building transaction should succeed
    And the user should have 3 granaries

  Scenario: Demolish a house
    Given a user with 10 houses
    When the user demolishes 2 houses
    Then the building transaction should succeed
    And the user should have 8 houses

  Scenario: Cannot demolish more houses than available
    Given a user with 3 houses
    When the user demolishes 5 houses
    Then the building transaction should fail
    And the user should have 3 houses
