Feature: End Turn Logic
When a player ends their turn
Then the game state should update accordingly

  Scenario: Increase gold by taxes
    Given user has 0 gold
    And user has 20 land
    And user has 20 houses
    And user has 100 citizens
    When press enter
    Then user should have 50 gold


  Scenario: Decreases food by consumption
    Given user has 100 food
    And user has 50 citizens
    When press enter
    Then user should have 75 food

#        test("skip next turn brings you to the report page") {
#            Given {} When {
#                input(KeyInput.Enter)
#            } Then {
#                page.shouldBeInstanceOf<ReportPage>()
#            }

#        test("Given no gold When buy resource Then beep no gold") {
#            Given {
#                setOwned<Gold>(0.z)
#            } When {
#                selectPrompt("trade")
#                selectPrompt("buy 1 🍖")
#            } Then {
#                verify(exactly = 1) {
#                    get<Beeper>().beep(match { it.contains("Insufficient resources") })
#                }
#            }
