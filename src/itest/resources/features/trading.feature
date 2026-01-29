Feature: Complex Resource Trading
  As a player
  I want to perform complex resource transactions
  So that I can manage my economy effectively

  Scenario: Successful multi-resource trade
    Given a user with 200 gold, 50 land, and 100 citizens
    When the user trades 100 gold for 25 land
    Then the transaction should succeed
    And the user should have 100 gold and 75 land
    And the user should have 100 citizens

  Scenario: Initial state with all resources
    Given a user with 500 gold, 100 land, 50 citizens, and 200 food
    Then the user should have 500 gold
    And the user should have 100 citizens
