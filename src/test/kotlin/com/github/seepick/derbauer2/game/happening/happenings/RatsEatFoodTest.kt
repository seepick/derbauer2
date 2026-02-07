package com.github.seepick.derbauer2.game.happening.happenings

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.addBuilding
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.food
import com.github.seepick.derbauer2.game.prob.ProbThresholderKey
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.addResource
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk

class RatsEatFoodTest : DescribeSpec({
    lateinit var user: User
    lateinit var probs: Probs
    beforeTest {
        user = User()
        probs = mockk()
    }

    describe("can happen") {
        fun canHappen() = RatsEatFoodDescriptor.canHappen(user, probs)

        it("Given no 🍖 Then cant") {
            canHappen().shouldBeFalse()
        }
        it("Given 0 🍖 Then cant") {
            user.addResource(Food(), 0.z)
            canHappen().shouldBeFalse()
        }
        it("Given some 🍖 Then can") {
            user.addResource(Food(), 1.z)
            canHappen().shouldBeTrue()
        }
    }
    describe("will happen") {
        fun willHappen() = RatsEatFoodDescriptor.willHappen(user, probs)

        it("Given threshold no Then wont") {
            every { probs.getThresholder(ProbThresholderKey.ratsWillHappenForSeason) } returns false

            willHappen().shouldBeFalse()
        }
        it("Given threshold yes Then will") {
            every { probs.getThresholder(ProbThresholderKey.ratsWillHappenForSeason) } returns true

            willHappen().shouldBeTrue()
        }
    }
    describe("build happening") {
        val maxFoodEaten = 15.z

        it("Given no food Then fail") {
            shouldThrow<IllegalArgumentException> {
                RatsEatFoodDescriptor.buildHappening(user)
            }
        }
        it("Given more than max food Then should eat only max amount") {
            user.addResource(Food(), maxFoodEaten + 5)
            val happening = RatsEatFoodDescriptor.buildHappening(user)

            happening.amountFoodEaten shouldBeEqual maxFoodEaten
        }
        it("Given less food than max Then should eat all food") {
            user.addResource(Food(), maxFoodEaten - 1.z)
            val happening = RatsEatFoodDescriptor.buildHappening(user)

            happening.amountFoodEaten shouldBeEqual maxFoodEaten - 1.z
        }
    }
    describe("execute") {
        it("should change user food amount") {
            val origAmount = 20.z
            user.addResource(Land(), 10.z)
            user.addBuilding(Granary(), 1.z)
            user.addResource(Food(), origAmount)
            val happening = RatsEatFoodDescriptor.buildHappening(user)

            happening.execute(user)

            user.food shouldBeEqual origAmount - happening.amountFoodEaten
        }
    }
})
