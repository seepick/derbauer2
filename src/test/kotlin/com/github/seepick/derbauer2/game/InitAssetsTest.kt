package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.core.food
import com.github.seepick.derbauer2.game.core.gold
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.equals.shouldBeEqual

class InitAssetsTest : StringSpec({
    "When initiating Then some starting assets given" {
        val user = com.github.seepick.derbauer2.game.testInfra.User()
        user.initAssets()

        user.all shouldHaveSize 7
        user.gold.value shouldBeGreaterThan 0
        user.food.value shouldBeGreaterThan 0
    }
    "When initiating two users Then they are equal but independent" {
        val user1 = com.github.seepick.derbauer2.game.testInfra.User()
        user1.initAssets()
        user1.all shouldHaveSize 7
        val initialGold = user1.gold

        val user2 = com.github.seepick.derbauer2.game.testInfra.User()
        user2.initAssets()
        user2.gold shouldBeEqual initialGold
    }
})
