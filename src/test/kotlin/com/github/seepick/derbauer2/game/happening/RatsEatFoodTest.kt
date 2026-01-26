package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.food
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.z
import com.github.seepick.derbauer2.game.resource.Food
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

class RatsEatFoodTest : StringSpec({
    context("build") {
        "should eat 15 food" {
            val user = User()
            val happening = HappeningDescriptor.RatsEatFood.build(user)

            happening.foodEaten shouldBeEqual 15.z
        }
    }
    context("execute") {
        "should change user food amount" {
            val user = User()
            val origAmount = 20.z
            user.add(Food(origAmount))
            user.add(Granary(10.z))
            val happening = HappeningDescriptor.RatsEatFood.build(user)
            happening.execute(user)

            user.food shouldBeEqual (origAmount - happening.foodEaten)
        }
    }
})
