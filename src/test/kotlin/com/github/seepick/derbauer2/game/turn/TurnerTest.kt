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
        user = user,
    )
    beforeTest {
        user = User()
        turner = Turner(
            happeningTurner = HappeningTurner(user),
            resourceTurner = ResourceTurner(user),
            citizenTurner = CitizenTurner(user),
            featureTurner = FeatureTurner(user),
            user = user,
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
            val food = user.add(Food(0.units))
            val farm = user.add(Farm(1.units))
            user.add(Granary(1.units))

            val report = turner.collectAndExecuteNextTurnReport()

            food.owned shouldBeEqual farm.totalProducingResourceAmount
            report.resourceReportLines shouldContainChange(food to farm.totalProducingResourceAmount)
        }
        it("Given no food storage When produce Then stay 0") {
            val food = user.add(Food(0.units))
            user.add(Farm(1.units))

            val report = turner.collectAndExecuteNextTurnReport()

            food.owned shouldBeEqual 0.units
            report.resourceReportLines shouldContainChange(food to 0.units)
        }
        it("Given 1 space left When produce 2 Then max capped") {
            val granary = user.add(Granary(1.units))
            val diff = 1
            val food = user.add(Food(granary.totalStorageAmount - diff))
            user.add(Farm(1.units))

            val report = turner.collectAndExecuteNextTurnReport()

            food.owned shouldBeEqual granary.totalStorageAmount
            report.resourceReportLines shouldContainChange(food to diff.units)
        }
    }
    describe("citizens") {
        it("Given sufficient citizens Then increase gold") {
            val gold = user.add(Gold(0.units))
            user.add(Citizen((1 / Mechanics.citizenTaxPercentage).units))
            user.add(Food(0.units))

            val report = turner.collectAndExecuteNextTurnReport()

            report.resourceReportLines shouldContainChange (gold to 1.units)
            gold.owned shouldBeEqual 1.units
        }
    }
})

val Double.units get() = toLong().units
