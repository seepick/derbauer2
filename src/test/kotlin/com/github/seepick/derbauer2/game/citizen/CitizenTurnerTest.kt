package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.shouldContainLine
import com.github.seepick.derbauer2.game.z
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.equals.shouldBeEqual

class CitizenTurnerTest : DescribeSpec() {
    private lateinit var user: User
    private lateinit var turner: CitizenTurner

    override suspend fun beforeTest(testCase: TestCase) {
        user = User()
        turner = CitizenTurner(user)
    }

    init {
        describe("When simple cases") {
            it("Given nothing Then report is empty") {
                val report = turner.buildReport()
                report.lines.shouldBeEmpty()
            }
        }
        describe("When taxing citizens") {
            it("Given some citizens Then report contains taxes But user gold unchanged") {
                val gold = user.add(Gold(0.z))
                val citizen = user.add(Citizen(10.z))

                val report = turner.buildReport()

                val expectedTax = citizen.owned * Mechanics.citizenTax
                report.shouldContainLine(gold, expectedTax.asSigned)
                gold.owned shouldBeEqual 0.z
            }
        }
        describe("When food eaten") {
            it("Given citizens and food Then food consumed") {
                val citizen = user.add(Citizen(10.z))
                val food = user.add(Food(10.z))

                val report = turner.buildReport()

                val eatenFood = citizen.owned * Mechanics.citizenFoodConsume
                report.shouldContainLine(food, -eatenFood)
            }
            it("Given citizens and too little food Then food consumption is capped And no people starved") {
                user.add(Citizen(10.z))
                val food = user.add(Food(1.z))

                val report = turner.buildReport()

                report.shouldContainLine(food, -food.owned)
                report.lines.filter { it.resource is Citizen }.shouldBeEmpty() // TODO will fail once birth is enabled
            }
            it("Given few citizens and no food Then minimum starvation") {
                val citizen = user.add(Citizen(3.z))
                user.add(Food(0.z))

                val report = turner.buildReport()

                report.shouldContainLine(citizen, -Mechanics.citizensStarveMinimum)
                report.lines.filter { it.resource is Food }.shouldBeEmpty()
            }
            it("Given many citizens and no food Then proportional starvation") {
                val expectedStarve = 2
                val citizen = user.add(Citizen(((1.0 / Mechanics.citizensStarve.value) * expectedStarve).z))
                user.add(Food(0.z))

                val report = turner.buildReport()

                report.shouldContainLine(citizen, -expectedStarve.z)
                report.lines.filter { it.resource is Food }.shouldBeEmpty()
            }
        }
    }
}
