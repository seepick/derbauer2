package com.github.seepick.derbauer2.game.happening.happenings

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.enableAndSet
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.food
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.enableAndSet
import com.github.seepick.derbauer2.game.testInfra.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.equals.shouldBeEqual

class RatsEatFoodTest : StringSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }

    context("can happen") {
        "Given no food Then no" {
            RatsEatFoodDescriptor.canHappen(user).shouldBeFalse()
        }
        "Given zero food Then no" {
            user.enableAndSet(Food(), 0.z)
            RatsEatFoodDescriptor.canHappen(user).shouldBeFalse()
        }
        "Given non-zero food Then yes" {
            val user = User()
            user.enableAndSet(Food(), 1.z)
            RatsEatFoodDescriptor.canHappen(user).shouldBeTrue()
        }
    }
    context("build") {
        "Given no food Then fail" {
            shouldThrow<IllegalArgumentException> {
                RatsEatFoodDescriptor.buildHappening(user)
            }
        }
        "Given lots of food Then should eat max amount" {
            user.enableAndSet(Food(), 20.z)
            val happening = RatsEatFoodDescriptor.buildHappening(user)

            happening.amountFoodEaten shouldBeEqual 15.z
        }
        "Given less food than max Then should eat all food" {
            user.enableAndSet(Food(), 2.z)
            val happening = RatsEatFoodDescriptor.buildHappening(user)

            happening.amountFoodEaten shouldBeEqual 2.z
        }
    }
    context("execute") {
        "should change user food amount" {
            val origAmount = 20.z
            user.enableAndSet(Land(), 10.z)
            user.enableAndSet(Granary(), 1.z)
            user.enableAndSet(Food(), origAmount)
            val happening = RatsEatFoodDescriptor.buildHappening(user)

            happening.execute(user)

            user.food shouldBeEqual origAmount - happening.amountFoodEaten
        }
    }
})
