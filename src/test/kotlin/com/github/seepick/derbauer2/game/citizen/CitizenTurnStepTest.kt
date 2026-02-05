package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.`%`
import com.github.seepick.derbauer2.game.common.Z
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
import com.github.seepick.derbauer2.game.resource.shouldBeEmpty
import com.github.seepick.derbauer2.game.resource.shouldContainChange
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.equals.shouldBeEqual
import kotlin.math.ceil

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

    fun CitizenTurnStep.calc(foodChange: Zz? = null) =
        calcTurnChanges(foodChange)

    fun CitizenTurnStep.calcShouldContain(resource: Resource, expected: Zz, foodChange: Zz? = null) {
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

    context("eat 🍖") {
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
            it("Given 0 🙎🏻‍♂️and some  🍖 Then 0 🍖 eaten") {
                val citizen = user.addResource(Citizen(), 0.z)
                user.addResource(Food(), 10.z)

                turner.calcShouldContain(citizen, 0.zz)
            }
        }
    }
    context("give birth 👶") {
        describe("Given no 🍖 Then eat/starvation disabled") {
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
    }

    describe("starvation ☠️") {
        it("Given no 🍖 and many 🙎🏻‍♂️ Then proportional starvation ☠️") {
            user.addResource(Food(), 0.z)
            val expectedStarve = 2
            val citizenAmount = Mechanics.citizensStarvePerUnfedCitizen.neededToGetTo(expectedStarve)
            val citizen = user.addResource(Citizen(), citizenAmount)

            turner.calcShouldContain(citizen, -expectedStarve.z)
        }
        it("Given no 🍖 and few 🙎🏻‍♂️ Then minimum starvation ☠️") {
            user.addResource(Food(), 0.z)
            val citizen = user.addResource(Citizen(), 2.z)

            turner.calcShouldContain(citizen, -(1).z)
        }
        it("Given A) almost enough 🍖 and B) no 🍖 and When compare them Then A) starves less than B)") {
            val wouldNeedFoodToFullFed = 10
            val citizens = Mechanics.citizenEatAmount.neededToGetTo(wouldNeedFoodToFullFed)
            val almostEnoughFood = wouldNeedFoodToFullFed - 1
            val citizen = user.add(Citizen())
            val food = user.add(Food())
            fun turnAndGetCitizenChangeWith(givenFood: Z): Zz {
                citizen.ownedForTest = citizens
                food.ownedForTest = givenFood
                return turner.calc().changes.single { it.resourceClass == Citizen::class }.changeAmount
            }

            val starvedA = turnAndGetCitizenChangeWith(almostEnoughFood.z).toZAbs()
            val starvedB = turnAndGetCitizenChangeWith(0.z).toZAbs()

            starvedA shouldBeLessThan starvedB
        }
    }
})

class StarveComputeTest : DescribeSpec({
    describe("howManyStarve") {
        listOf(
            Triple(0, 0, 0),
            Triple(1, 0, 1),
            Triple(5, 0, 5),
            Triple(11, 0, 11),
            Triple(1, 1, 0),
            Triple(10, 1, 0),
            Triple(11, 1, 1),
            Triple(19, 1, 9),
            Triple(20, 1, 10),
            Triple(21, 1, 11),
        ).forEach { (citizen, food, expectedCitizenUnfed) ->
            it("Given 10% eat and $citizen 🙎🏻‍♂️ and $food 🍖 Then $expectedCitizenUnfed unfed") {
                val eatRatio = 10.`%`
                val eaten = ceil(citizen * eatRatio.value).toLong().z
                StarveCompute.howManyUnfed(citizen.z, food.z, eaten, eatRatio) shouldBeEqual expectedCitizenUnfed.z
            }
        }
        listOf(
            Triple(5, 2, 1), // eat: 2.5 -> ceil up: 3 -> left food: 2 -> 1 starve
            Triple(5, 3, 0),
        ).forEach { (citizen, food, expectedCitizenUnfed) ->
            it("Given 50% eat and $citizen 🙎🏻‍♂️ and $food 🍖 Then $expectedCitizenUnfed unfed") {
                val eatRatio = 50.`%`
                val eaten = ceil(citizen * eatRatio.value).toLong().z
                StarveCompute.howManyUnfed(citizen.z, food.z, eaten, eatRatio) shouldBeEqual expectedCitizenUnfed.z
            }
        }
    }
})
