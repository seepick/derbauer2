package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.citizen.CitizenTurner
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.FeatureTurner
import com.github.seepick.derbauer2.game.happening.HappeningTurner
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.ResourceTurner
import com.github.seepick.derbauer2.game.shouldContainLine
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
            user.add(Land(10.z))
            val farm = user.add(Farm(1.z))
            user.add(Granary(1.z))
            val food = user.add(Food(0.z))

            val report = turner.collectAndExecuteNextTurnReport()

            food.owned shouldBeEqual farm.totalProducingResourceAmount
            report.resourceReportLines shouldContainLine(food to farm.totalProducingResourceAmount.asSigned)
        }
        it("Given no food storage When produce Then stay 0") {
            user.add(Land(10.z))
            user.add(Farm(1.z))
            val food = user.add(Food(0.z))

            val report = turner.collectAndExecuteNextTurnReport()

            food.owned shouldBeEqual 0.z
            report.resourceReportLines shouldContainLine(food to 0.zz)
        }
        it("Given 1 space left When produce 2 Then max capped") {
            user.add(Land(10.z))
            val granary = user.add(Granary(1.z))
            val diff = 1
            val food = user.add(Food(granary.totalStorageAmount - diff))
            user.add(Farm(1.z))

            val report = turner.collectAndExecuteNextTurnReport()

            food.owned shouldBeEqual granary.totalStorageAmount
            report.resourceReportLines shouldContainLine(food to diff.zz)
        }
    }
    describe("citizens") {
        it("Given sufficient citizens Then increase gold") {
            val gold = user.add(Gold(0.z))
            user.add(Citizen((1.0 / Mechanics.citizenTax.value).toLong().z))
            user.add(Food(0.z))

            val report = turner.collectAndExecuteNextTurnReport()

            report.resourceReportLines shouldContainLine (gold to 1.zz)
            gold.owned shouldBeEqual 1.z
        }
    }
})
