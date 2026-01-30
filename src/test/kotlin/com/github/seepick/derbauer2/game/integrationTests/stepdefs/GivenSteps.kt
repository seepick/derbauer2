package com.github.seepick.derbauer2.game.integrationTests.stepdefs

import com.github.seepick.derbauer2.game.integrationTests.TestWorld
import com.github.seepick.derbauer2.game.integrationTests.lookupAssetBy
import io.cucumber.java.en.Given

class GivenSteps(private val world: TestWorld) {

    @Given("user has {int} {word}")
    fun userHasAsset(amount: Int, assetName: String) {
        val assetClass = lookupAssetBy(assetName)
        world.setOwnedFor(assetClass, amount)
    }

}
