package com.github.seepick.derbauer2.game.cucumber.stepdefs

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

class YourSteps(private val world: TestWorld) {
    // TODO use enum or maybe even data table?
    @Given("a user with {int} gold")
    fun `Given a user with {int} gold`(amount: Int) {
        println("set world amount...")
        world.amount = amount
    }

    @When("the user performs some action")
    fun `When the user performs some action`() {
        println("WHEN world.amount = ${world.amount}")
    }
}

class OtherSteps(private val world: TestWorld) {
    @Then("the user should have expected results")
    fun `Then the user should have expected results`() {
        println("THEN world.amount = ${world.amount}")
    }
}

class TestWorld {
    var amount = 0
}
