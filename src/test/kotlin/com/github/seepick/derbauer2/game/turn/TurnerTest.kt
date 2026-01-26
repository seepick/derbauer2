package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.citizen.CitizenTurner
import com.github.seepick.derbauer2.game.feature.FeatureTurner
import com.github.seepick.derbauer2.game.happening.HappeningTurner
import com.github.seepick.derbauer2.game.logic.Mechanics
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.units
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.ResourceTurner
import com.github.seepick.derbauer2.game.shouldContainChange
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class TurnerTest : DescribeSpec({
    // FIXME avoid duplication
    var user = User()
    var turner = Turner(
        happeningTurner = HappeningTurner(user),
        resourceTurner = ResourceTurner(user),
        citizenTurner = CitizenTurner(user),
        featureTurner = FeatureTurner(user),
    )
    beforeTest {
        user = User()
        turner = Turner(
            happeningTurner = HappeningTurner(user),
            resourceTurner = ResourceTurner(user),
            citizenTurner = CitizenTurner(user),
            featureTurner = FeatureTurner(user),
        )
    }

    describe("turn") {
        it("changed") {
            val old = turner.turn
            turner.collectAndExecuteNextTurnReport()

            turner.turn shouldBeEqual old + 1
        }
    }
    describe("produce resource") {
        it("Given producer and storage When produce Then owned increased and report contains") {
            val food = user.addEntity(Food(0.units))
            val farm = user.addEntity(Farm(1.units))
            user.addEntity(Granary(1.units))

            val report = turner.collectAndExecuteNextTurnReport()

            food.owned shouldBeEqual farm.resourceProductionAmount
            report.resourceChanges shouldContainChange(food to farm.resourceProductionAmount)
        }
        it("Given no food storage When produce Then stay 0") {
            val food = user.addEntity(Food(0.units))
            user.addEntity(Farm(1.units))

            val report = turner.collectAndExecuteNextTurnReport()

            food.owned shouldBeEqual 0.units
            report.resourceChanges shouldContainChange(food to 0.units)
        }
        it("Given 1 space left When produce 2 Then max capped") {
            val granary = user.addEntity(Granary(1.units))
            val diff = 1
            val food = user.addEntity(Food(granary.totalStorageAmount - diff))
            user.addEntity(Farm(1.units))

            val report = turner.collectAndExecuteNextTurnReport()

            food.owned shouldBeEqual granary.totalStorageAmount
            report.resourceChanges shouldContainChange(food to diff.units)
        }
    }
    describe("citizens") {
        it("Given sufficient citizens Then increase gold") {
            val gold = user.addEntity(Gold(0.units))
            user.addEntity(Citizen((1 / Mechanics.citizenTaxPercentage).units))
            user.addEntity(Food(0.units))

            val report = turner.collectAndExecuteNextTurnReport()

            report.resourceChanges shouldContainChange (gold to 1.units)
            gold.owned shouldBeEqual 1.units
        }
    }
})

val Double.units get() = toLong().units
