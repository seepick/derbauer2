package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.ResourceChange
import com.github.seepick.derbauer2.game.testInfra.User
import com.github.seepick.derbauer2.game.testInfra.enableAndSet
import com.github.seepick.derbauer2.game.testInfra.z
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class CitizenResourceTurnerTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }

    describe("When reproduce") {
        it("Given no citizens Then stay zero") {
            val citizen = user.enableAndSet(Citizen(), 0.z)

            val change = CitizenReproduceResourceTurnStep(user).calcResourceChange()

            change shouldBeEqual ResourceChange(citizen, 0.z)
        }
        it("Given 1 citizen Then get minimum of 1") {
            val citizen = user.enableAndSet(Citizen(), 1.z)

            val change = CitizenReproduceResourceTurnStep(user).calcResourceChange()

            change shouldBeEqual ResourceChange(citizen, Mechanics.citizenReproductionMinimum)
        }
        it("Given 100 citizen Then increase by birth rate") {
            val citizen = user.enableAndSet(Citizen(), 100.z)

            val change = CitizenReproduceResourceTurnStep(user).calcResourceChange()

            val expected = citizen.owned * Mechanics.citizenReproductionRate
            change shouldBeEqual ResourceChange(citizen, expected)
        }
    }
    describe("When food eaten") {
        it("Given citizens and food Then food consumed") {
            val citizen = user.enableAndSet(Citizen(), 10.z)
            val food = user.enableAndSet(Food(), 10.z)

            val change = CitizenFoodEatenResourceTurnStep(user).calcResourceChange()

            val eatenFood = citizen.owned * Mechanics.citizenFoodConsume
            change shouldBeEqual ResourceChange(food, -eatenFood)
        }
        it("Given citizens and too little food Then food consumption is capped And no people starved") {
            user.enableAndSet(Citizen(), 10.z)
            val food = user.enableAndSet(Food(), 1.z)

            val change = CitizenFoodEatenResourceTurnStep(user).calcResourceChange()

            change shouldBeEqual ResourceChange(food, -food.owned)
        }
        it("Given few citizens and no food Then minimum starvation") {
            val citizen = user.enableAndSet(Citizen(), 3.z)
            user.enableAndSet(Food(), 0.z)

            val change = CitizenFoodEatenResourceTurnStep(user).calcResourceChange()

            change shouldBeEqual ResourceChange(citizen, -Mechanics.citizensStarveMinimum)
        }
        it("Given many citizens and no food Then proportional starvation") {
            val expectedStarve = 2
            val citizen = user.enableAndSet(
                Citizen(),
                (1.0 / Mechanics.citizensStarve.value * expectedStarve).z
            )
            user.enableAndSet(Food(), 0.z)

            val change = CitizenFoodEatenResourceTurnStep(user).calcResourceChange()

            change shouldBeEqual ResourceChange(citizen, -expectedStarve.z)
        }
    }
    describe("When taxes paid") {
        it("Given some citizens Then report contains taxes But user gold unchanged") {
            val gold = user.enableAndSet(Gold(), 0.z)
            val citizen = user.enableAndSet(Citizen(), 10.z)

            val change = CitizenTaxesResourceTurnStep(user).calcResourceChange()

            val expectedTax = citizen.owned * Mechanics.citizenTax
            change shouldBeEqual ResourceChange(gold, expectedTax.asZz)
        }
    }
})
