package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.NotFoundEntityException
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.PassThroughDiffuser
import com.github.seepick.derbauer2.game.prob.ProbDiffuserKey
import com.github.seepick.derbauer2.game.prob.ProbsImpl
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.addResource
import com.github.seepick.derbauer2.game.resource.shouldContainChange
import com.github.seepick.derbauer2.game.testInfra.z
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec

class CitizenFoodEatenTurnStepTest : DescribeSpec({
    lateinit var user: User
    lateinit var probs: ProbsImpl
    lateinit var turner: CitizenFoodEatenTurnStep

    beforeTest {
        user = User()
        probs = ProbsImpl()
        probs.setDiffuser(ProbDiffuserKey.eatKey, PassThroughDiffuser)
        turner = CitizenFoodEatenTurnStep(user, probs)
    }
    fun calc() = turner.calcResourceChanges()
    fun expectResourceChange(resource: Resource, changeAmount: Zz) {
        calc().shouldContainChange(resource, changeAmount)
    }

    describe("Edgecase") {
        it("Given nothing Then fail") {
            shouldThrow<NotFoundEntityException> { calc() }
        }
        it("Given 0 citizens Then 0 citizens change") {
            val citizen = user.addResource(Citizen(), 0.z)

            expectResourceChange(citizen, 0.zz)
        }
    }
    describe("Given some 🍖") {
        it("Given 1 🙎🏻‍♂️and sufficient 🍖️Then minimum 🍖 eaten") {
            val food = user.addResource(Food(), 10.z)
            user.addResource(Citizen(), 1.z)

            expectResourceChange(food, (-1).zz)
        }
        it("Given some 🙎🏻‍♂️and exactly enough 🍖 Then all 🍖 eaten") {
            val citizen = user.addResource(Citizen(), 100.z)
            val expectEaten = citizen.owned * Mechanics.citizenEatAmount
            val food = user.addResource(Food(), expectEaten)

            expectResourceChange(food, -expectEaten)
        }
    }

    describe("Given no 🍖") {
        beforeTest {
            user.addResource(Food(), 0.z)
        }
        it("Given many 🙎🏻‍♂️ Then proportional starvation ☠️") {
            val expectedStarve = 2
            val citizen = user.addResource(
                Citizen(),
                (1.0 / Mechanics.citizensStarve.value * expectedStarve).z
            )

            expectResourceChange(citizen, -expectedStarve.z)
        }
        it("Given few 🙎🏻‍♂️ Then minimum starvation ☠️") {
            val citizen = user.addResource(Citizen(), 2.z)

            expectResourceChange(citizen, -Mechanics.citizensStarveMinimum)
        }
    }
})
