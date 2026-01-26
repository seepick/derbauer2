package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.food
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.zp
import com.github.seepick.derbauer2.game.resource.Food
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

class RatsEatFoodTest : StringSpec({
    context("build") {
        "should eat 15 food" {
            val user = User()
            val happening = HappeningDescriptor.RatsEatFood.build(user)

            happening.foodEaten shouldBeEqual 15.zp
        }
    }
    context("execute") {
        "should change user food amount" {
            val user = User()
            val origAmount = 20.zp
            user.add(Food(origAmount))
            user.add(Granary(10.zp))
            val happening = HappeningDescriptor.RatsEatFood.build(user)
            happening.execute(user)

            user.food shouldBeEqual (origAmount - happening.foodEaten)
        }
    }
})
