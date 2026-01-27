package com.github.seepick.derbauer2.game.happening.happenings

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.food
import com.github.seepick.derbauer2.game.resource.Food
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.equals.shouldBeEqual

class RatsEatFoodTest : StringSpec({
    context("can happen") {
        "Given no food Then no" {
            val user = User()
            RatsEatFoodDescriptor.canHappen(user).shouldBeFalse()
        }
        "Given zero food Then no" {
            val user = User()
            user.add(Food(0.z))
            RatsEatFoodDescriptor.canHappen(user).shouldBeFalse()
        }
        "Given non-zero food Then yes" {
            val user = User()
            user.add(Food(1.z))
            RatsEatFoodDescriptor.canHappen(user).shouldBeTrue()
        }
    }
    context("build") {
        "Given no food Then fail" {
            val user = User()
            shouldThrow<IllegalArgumentException> {
                RatsEatFoodDescriptor.build(user)
            }
        }
        "Given lots of food Then should eat max amount" {
            val user = User()
            user.add(Food(20.z))
            val happening = RatsEatFoodDescriptor.build(user)

            happening.amountFoodEaten shouldBeEqual 15.z
        }
        "Given less food than max Then should eat all food" {
            val user = User()
            user.add(Food(2.z))
            val happening = RatsEatFoodDescriptor.build(user)

            happening.amountFoodEaten shouldBeEqual 2.z
        }
    }
    context("execute") {
        "should change user food amount" {
            val user = User()
            val origAmount = 20.z
            user.add(Food(origAmount))
            val happening = RatsEatFoodDescriptor.build(user)

            happening.execute(user)

            user.food shouldBeEqual (origAmount - happening.amountFoodEaten)
        }
    }
})
