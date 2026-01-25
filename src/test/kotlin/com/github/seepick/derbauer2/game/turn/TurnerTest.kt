package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.logic.units
import com.github.seepick.derbauer2.game.resource.Food
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.equals.shouldBeEqual

class TurnerTest : DescribeSpec({
    var turner = Turner()
    var game = Game()
    beforeTest {
        turner = Turner()
        game = Game()
    }

    describe("turn") {
        it("changed") {
            val old = turner.turn
            turner.takeTurn(game)

            turner.turn shouldBeEqual old + 1
        }
    }
    describe("produce resource") {
        it("Given producer and storage When produce Then owned increased and report contains") {
            val food = game.user.addEntity(Food(0.units))
            val farm = game.user.addEntity(Farm(1.units))
            game.user.addEntity(Granary(1.units))

            val report = turner.takeTurn(game)

            food.owned shouldBeEqual farm.resourceProductionAmount
            report.resourceProduction.shouldContain(food to farm.resourceProductionAmount)
        }
        it("Given no food storage When produce Then stay 0") {
            val food = game.user.addEntity(Food(0.units))
            game.user.addEntity(Farm(1.units))

            val report = turner.takeTurn(game)

            food.owned shouldBeEqual 0.units
            report.resourceProduction.shouldContain(food to 0.units)
        }
        it("Given 1 space left When produce 2 Then max capped") {
            val granary = game.user.addEntity(Granary(1.units))
            val diff = 1
            val food = game.user.addEntity(Food(granary.totalStorageAmount - diff))
            game.user.addEntity(Farm(1.units))

            val report = turner.takeTurn(game)

            food.owned shouldBeEqual granary.totalStorageAmount
            report.resourceProduction.shouldContain(food to diff.units)
        }
    }
})
