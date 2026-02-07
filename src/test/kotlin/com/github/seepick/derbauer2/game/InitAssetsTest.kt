package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.food
import com.github.seepick.derbauer2.game.core.gold
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.ints.shouldBeGreaterThan

class InitAssetsTest : StringSpec({
    "When initiating Then some starting assets given" {
        val user = User()
        user.initAssets()

        user.all.size shouldBeGreaterThan 0
        user.gold.value shouldBeGreaterThan 0
        user.food.value shouldBeGreaterThan 0
    }
    "When initiating two users Then they are equal but independent" {
        val user1 = User()
        user1.initAssets()
        user1.all.size shouldBeGreaterThan 0
        val initialGold = user1.gold

        val user2 = User()
        user2.initAssets()
        user2.gold shouldBeEqual initialGold

        user1.findResource<Gold>().ownedForTest = initialGold + 100
        user2.gold shouldBeEqual initialGold
    }
})
