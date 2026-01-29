Feature: Resource Management
  As a player
  I want to manage my resources
  So that I can build and sustain my empire

  Scenario: User gains gold
    Given a user with 100 gold
    When the user gains 50 gold
    Then the user should have 150 gold

  Scenario: User spends gold successfully
    Given a user with 100 gold
    When the user spends 30 gold
    Then the transaction should succeed
    And the user should have 70 gold

  Scenario: User cannot spend more gold than available
    Given a user with 50 gold
    When the user tries to spend 100 gold
    Then the transaction should fail due to insufficient resources
    And the user should have 50 gold

  Scenario: Trading gold for land
    Given a user with 100 gold and 10 land
    When the user trades 20 gold for 5 land
    Then the transaction should succeed
    And the user should have 80 gold and 15 land

  Scenario: Trading with insufficient gold fails
    Given a user with 10 gold and 10 land
    When the user trades 50 gold for 5 land
    Then the transaction should fail due to insufficient resources
    And the user should have 10 gold and 10 land
