package com.github.seepick.derbauer2.game.integrationTests.stepdefs

import com.github.seepick.derbauer2.game.integrationTests.TestWorld
import com.github.seepick.derbauer2.game.integrationTests.lookupAssetBy
import io.cucumber.java.en.Then
import io.kotest.matchers.equals.shouldBeEqual

class ThenSteps(private val world: TestWorld) {

    @Then("user should have {int} {word}")
    fun userShouldHaveAsset(expectedAmount: Int, assetName: String) {
        val assetClass = lookupAssetBy(assetName)
        world.getOwnedFor(assetClass) shouldBeEqual expectedAmount
    }

}
