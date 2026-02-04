package com.github.seepick.derbauer2.game.citizen

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
import com.github.seepick.derbauer2.game.resource.ResourceChange
import com.github.seepick.derbauer2.game.resource.addResource
import com.github.seepick.derbauer2.game.resource.shouldBeEmpty
import com.github.seepick.derbauer2.game.resource.shouldContainChange
import io.kotest.core.spec.style.DescribeSpec

class CitizenTurnStepTest : DescribeSpec({
    lateinit var user: User
    lateinit var turner: CitizenTurnStep
    lateinit var probs: ProbsImpl
    beforeTest {
        user = User()
        probs = ProbsImpl()
        probs.setDiffuser(ProbDiffuserKey.eatKey, PassThroughDiffuser)
        turner = CitizenTurnStep(user, probs)
    }

    fun CitizenTurnStep.calc(foodChange: ResourceChange? = null) =
        calcTurnChanges(foodChange)

    fun CitizenTurnStep.calcShouldContain(resource: Resource, expected: Zz, foodChange: ResourceChange? = null) {
        calcTurnChanges(foodChange).shouldContainChange(resource, expected)
    }

    context("misc") {
        describe("Edgecase") {
            it("Given nothing Then empty") {
                val changes = turner.calc()

                changes.shouldBeEmpty()
            }
            it("Given 0 citizens Then 0 citizens change") {
                val citizen = user.addResource(Citizen(), 0.z)

                turner.calcShouldContain(citizen, 0.zz)
            }
        }
    }
    context("food eaten") {
        describe("Given some 🍖") {
            it("Given 1 🙎🏻‍♂️and sufficient 🍖️Then minimum 🍖 eaten") {
                val food = user.addResource(Food(), 10.z)
                user.addResource(Citizen(), 1.z)

                turner.calcShouldContain(food, (-1).zz)
            }
            it("Given some 🙎🏻‍♂️and exactly enough 🍖 Then all 🍖 eaten") {
                val citizen = user.addResource(Citizen(), 100.z)
                val expectEaten = citizen.owned * Mechanics.citizenEatAmount
                val food = user.addResource(Food(), expectEaten)

                turner.calcShouldContain(food, -expectEaten)
            }
        }

    }

    describe("When birth without 🍖 food") {
        it("Given 0 🙎🏻‍♂️ Then change 0") {
            val citizen = user.addResource(Citizen(), 0.z)

            turner.calcShouldContain(citizen, 0.zz)
        }
        it("Given 1 🙎🏻‍♂️ Then +1 minimum birth") {
            val citizen = user.addResource(Citizen(), 1.z)

            turner.calcShouldContain(citizen, 1.zz)
        }
        it("Given many 🙎🏻‍♂️ Then +many by reproduction rate") {
            val citizen = user.addResource(Citizen(), 100.z)

            turner.calcShouldContain(citizen, (citizen.owned * Mechanics.citizenBirthRate).zz)
        }
    }
    context("Semi eat Semi starve") {
        describe("Foo") {
            it("Given many 🙎🏻‍♂️ and some 🍖 Then +change") {
                val citizen = user.addResource(Citizen(), Mechanics.citizensStarve.neededToGetTo(2))
                val food = user.addResource(Food(), 1.z)

                val changes = turner.calc()
                changes.shouldContainChange(food, -(citizen.owned * Mechanics.citizenEatAmount).zz)
                changes.shouldContainChange(citizen, (-2).zz) // in the future will be starving...
            }
        }
    }

    context("starvation") {
        describe("Given no 🍖") {
            beforeTest {
                user.addResource(Food(), 0.z)
            }
            it("Given many 🙎🏻‍♂️ Then proportional starvation ☠️") {
                val expectedStarve = 2
                val citizenAmount = Mechanics.citizensStarve.neededToGetTo(expectedStarve)
                val citizen = user.addResource(Citizen(), citizenAmount)

                turner.calcShouldContain(citizen, -expectedStarve.z)
            }
            it("Given few 🙎🏻‍♂️ Then minimum starvation ☠️") {
                val citizen = user.addResource(Citizen(), 2.z)

                turner.calcShouldContain(citizen, -Mechanics.citizensStarveMinimum)
            }
        }
    }
})

