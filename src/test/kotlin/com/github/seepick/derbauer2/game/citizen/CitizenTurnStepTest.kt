package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.common.unit.`%`
import com.github.seepick.derbauer2.common.unit.Z
import com.github.seepick.derbauer2.common.unit.Zz
import com.github.seepick.derbauer2.common.unit.double11
import com.github.seepick.derbauer2.common.unit.z
import com.github.seepick.derbauer2.common.unit.zz
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
import com.github.seepick.derbauer2.game.stat.Happiness
import com.github.seepick.derbauer2.game.stat.addStat
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.comparables.shouldBeLessThan

class CitizenTurnStepTest : DescribeSpec({
    lateinit var user: User
    lateinit var turner: CitizenTurnStep
    lateinit var probs: ProbsImpl
    beforeTest {
        user = User()
        probs = ProbsImpl()
        probs.setDiffuser(ProbDiffuserKey.eatKey, PassThroughDiffuser)
        probs.setDiffuser(ProbDiffuserKey.birthKey, PassThroughDiffuser)
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
        describe("Given some happiness And food non-existing") {
            fun givenSomeCitizensWhenTurnThenBirthRateShouldBe(
                givenHappiness: Double,
                expectedBirthMultiplier: Double,
            ) {
                val citizen = user.addResource(Citizen(), 100.z)
                user.addStat(Happiness(), givenHappiness.double11)
                val expectedBirth = citizen.owned * Mechanics.citizenBirthRate * expectedBirthMultiplier.`%`

                turner.calcShouldContain(citizen, expectedBirth.zz)
            }
            it("Given neutral happiness Then regular birth rate") {
                givenSomeCitizensWhenTurnThenBirthRateShouldBe(
                    givenHappiness = 0.0,
                    expectedBirthMultiplier = 1.0,
                )
            }
            it("Given positive happiness Then birth rate increased") {
                givenSomeCitizensWhenTurnThenBirthRateShouldBe(
                    givenHappiness = 1.0,
                    expectedBirthMultiplier = 1.3,
                )
            }
            it("Given negative happiness Then birth rate decreased") {
                givenSomeCitizensWhenTurnThenBirthRateShouldBe(
                    givenHappiness = -1.0,
                    expectedBirthMultiplier = 0.7,
                )
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

            turner.calcShouldContain(citizen, (-1).zz)
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
                return turner.calc().changes.single { it.resourceClass == Citizen::class }.amount
            }

            val starvedA = turnAndGetCitizenChangeWith(almostEnoughFood.z).toZAbs()
            val starvedB = turnAndGetCitizenChangeWith(0.z).toZAbs()

            starvedA shouldBeLessThan starvedB
        }
    }
    context("food producing") {
        describe("eat") {
            it("Given no 🍖 and few 🙎🏻‍♂️ and produce some 🍖 Then eat 0 🍖") {
                user.addResource(Citizen(), 1.z)
                val food = user.addResource(Food(), 0.z)
                val producedFood = 10.zz

                turner.calc(producedFood).shouldContainChange(food, (-1).zz)
            }
            it("Given few 🍖 and few 🙎🏻‍♂️ and produce some negative 🍖 Then nothing left to eat") {
                user.addResource(Citizen(), 1.z)
                val food = user.addResource(Food(), 1.z)
                val producedFood = (-10).zz

                turner.calc(producedFood).shouldContainChange(food, 0.zz)
            }
        }
        describe("starve ☠️") {
            it("Given no 🍖 and few 🙎🏻‍♂️ Then no starvation but birth") {
                val citizen = user.addResource(Citizen(), 1.z)
                user.addResource(Food(), 0.z)
                val producedFood = 10.zz

                turner.calc(producedFood).shouldContainChange(citizen, 1.zz)
            }
            it("Given few 🍖 and few 🙎🏻‍♂️ and produce some negative 🍖 Then starve ☠️") {
                val citizen = user.addResource(Citizen(), 1.z)
                user.addResource(Food(), 1.z)
                val producedFood = (-10).zz

                turner.calc(producedFood).shouldContainChange(citizen, (-1).zz)
            }
        }
    }
})
