package com.github.seepick.derbauer2.game.integrationTests.stepdefs

import com.github.seepick.derbauer2.game.integrationTests.TestWorld
import com.github.seepick.derbauer2.game.integrationTests.testInfra.asKeyInput
import io.cucumber.java.en.When

class WhenSteps(private val world: TestWorld) {

    @When("press {word}")
    fun pressKey(key: String) {
        world.enterKeyOnCurrentPage(key.asKeyInput())
    }
}
