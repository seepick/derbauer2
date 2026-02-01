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
import com.github.seepick.derbauer2.game.testInfra.shouldContainChange
import com.github.seepick.derbauer2.game.testInfra.z
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull

class CitizenTurnerTest : DescribeSpec({
    lateinit var user: User
    lateinit var turner: CitizenTurner
    beforeTest {
        user = User()
        turner = CitizenTurner(user)
    }
    describe("When simple cases") {
        it("Given nothing Then report is empty") {
            val report = turner.buildResourceChanges()
            report.changes.shouldBeEmpty()
        }
    }
    describe("When taxing citizens") {
        it("Given some citizens Then report contains taxes But user gold unchanged") {
            val gold = user.enableAndSet(Gold(), 0.z)
            val citizen = user.enableAndSet(Citizen(), 10.z)

            val report = turner.buildResourceChanges()

            val expectedTax = citizen.owned * Mechanics.citizenTax
            report.shouldContainChange(gold, expectedTax.asZz)
            gold.owned shouldBeEqual 0.z
        }
    }

    describe("When food eaten") {
        it("Given citizens and food Then food consumed") {
            val citizen = user.enableAndSet(Citizen(), 10.z)
            val food = user.enableAndSet(Food(), 10.z)

            val change = turner.consumeFoodOrStarve().shouldNotBeNull()

            val eatenFood = citizen.owned * Mechanics.citizenFoodConsume
            change shouldBeEqual ResourceChange(food, -eatenFood)
        }
        it("Given citizens and too little food Then food consumption is capped And no people starved") {
            user.enableAndSet(Citizen(), 10.z)
            val food = user.enableAndSet(Food(), 1.z)

            val report = turner.buildResourceChanges()

            report.shouldContainChange(food, -food.owned)
            report.changes.filter { it.resource is Citizen }.shouldBeEmpty()
        }
        it("Given few citizens and no food Then minimum starvation") {
            val citizen = user.enableAndSet(Citizen(), 3.z)
            user.enableAndSet(Food(), 0.z)

            val report = turner.buildResourceChanges()

            report.shouldContainChange(citizen, -Mechanics.citizensStarveMinimum)
            report.changes.filter { it.resource is Food }.shouldBeEmpty()
        }
        it("Given many citizens and no food Then proportional starvation") {
            val expectedStarve = 2
            val citizen = user.enableAndSet(
                Citizen(),
                (1.0 / Mechanics.citizensStarve.value * expectedStarve).z
            )
            user.enableAndSet(Food(), 0.z)

            val report = turner.buildResourceChanges()

            report.shouldContainChange(citizen, -expectedStarve.z)
            report.changes.filter { it.resource is Food }.shouldBeEmpty()
        }
    }
//    describe("Reproduction") {
//         it("Given food and citizens Then reproduce") {
//             val citizen = Citizen()
//             user.enableAndSet(Food(), 1.z)
//             user.enableAndSet(citizen, 10.z)
//
//             val report = turner.buildReport()
//
//             report.lines.single { it.resource is Citizen }.changeAmount shouldBeGreaterThan 0.zz
//         }
//    }
})
