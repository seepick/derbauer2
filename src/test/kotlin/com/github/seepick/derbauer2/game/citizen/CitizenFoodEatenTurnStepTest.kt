package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.NotFoundEntityException
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.ResourceChange
import com.github.seepick.derbauer2.game.resource.enableAndSet
import com.github.seepick.derbauer2.game.testInfra.z
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class CitizenFoodEatenTurnStepTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = com.github.seepick.derbauer2.game.testInfra.User()
    }
    fun calc() = CitizenFoodEatenTurnStep(user).calcResourceChange()
    fun expectResourceChange(resource: Resource, changeAmount: Zz) {
        calc() shouldBeEqual ResourceChange(resource, changeAmount)
    }
    describe("Edgecase") {
        it("Given nothing Then fail") {
            shouldThrow<NotFoundEntityException> { calc() }
        }
        it("Given 0 citizens Then 0 citizens change") {
            val citizen = user.enableAndSet(Citizen(), 0.z)

            expectResourceChange(citizen, 0.zz)
        }
    }
    describe("Given some 🍖") {
        it("Given 1 🙎🏻‍♂️and sufficient 🍖️Then minimum 🍖 consumed") {
            val food = user.enableAndSet(Food(), 10.z)
            user.enableAndSet(Citizen(), 1.z)

            expectResourceChange(food, (-1).zz)
        }
        it("Given some 🙎🏻‍♂️and sufficient 🍖 Then some 🍖 consumed") {
            val food = user.enableAndSet(Food(), 10.z)
            val citizen = user.enableAndSet(Citizen(), 10.z)

            expectResourceChange(food, -(citizen.owned * Mechanics.citizenFoodConsume))
        }
        it("Given some 🙎🏻‍♂️ and too little 🍖 Then all available food 🍖 consumed") {
            val food = user.enableAndSet(Food(), 1.z)
            user.enableAndSet(Citizen(), 10.z)

            expectResourceChange(food, -food.owned)
        }
    }

    describe("Given no 🍖") {
        beforeTest {
            user.enableAndSet(Food(), 0.z)
        }
        it("Given many 🙎🏻‍♂️ Then proportional starvation ☠️") {
            val expectedStarve = 2
            val citizen = user.enableAndSet(
                Citizen(),
                (1.0 / Mechanics.citizensStarve.value * expectedStarve).z
            )

            expectResourceChange(citizen, -expectedStarve.z)
        }
        it("Given few 🙎🏻‍♂️ Then minimum starvation ☠️") {
            val citizen = user.enableAndSet(Citizen(), 2.z)

            expectResourceChange(citizen, -Mechanics.citizensStarveMinimum)
        }
    }
})
