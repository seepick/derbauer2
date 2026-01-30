package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.House
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
import com.github.seepick.derbauer2.game.testInfra.User
import com.github.seepick.derbauer2.game.testInfra.enableAndSet
import com.github.seepick.derbauer2.game.testInfra.shouldContainLine
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.types.shouldBeSameInstanceAs
import kotlin.math.ceil

class TurnerTest : DescribeSpec({
    lateinit var user: User
    lateinit var turner: Turner
    lateinit var reports: ReportIntelligence
    beforeTest {
        user = User()
        reports = ReportIntelligence()
        turner = Turner(
            happeningTurner = HappeningTurner(user),
            resourceTurner = ResourceTurner(user),
            citizenTurner = CitizenTurner(user),
            featureTurner = FeatureTurner(user),
            reports = reports,
            user = user,
        )
    }
    describe("misc") {
        it("turn changed") {
            val old = turner.turn
            turner.collectAndExecuteNextTurnReport()

            turner.turn shouldBeEqual old + 1
        }
        it("intelligence updated") {
            val report = turner.collectAndExecuteNextTurnReport()

            reports.last() shouldBeSameInstanceAs report
        }
    }
    describe("produce resource") {
        it("Given producer and storage When produce Then owned increased and report contains") {
            user.enableAndSet(Land(), 10.z)
            val farm = user.enableAndSet(Farm(), 1.z)
            user.enableAndSet(Granary(), 1.z)
            val food = user.enable(Food())

            val report = turner.collectAndExecuteNextTurnReport()

            food.owned shouldBeEqual farm.totalProducingResourceAmount
            report.resourceReportLines shouldContainLine (food to farm.totalProducingResourceAmount.asZz)
        }
        it("Given no food storage When produce Then stay 0") {
            user.enableAndSet(Land(), 10.z)
            user.enableAndSet(Farm(), 1.z)
            val food = user.enable(Food())

            val report = turner.collectAndExecuteNextTurnReport()

            food.owned shouldBeEqual 0.z
            report.resourceReportLines shouldContainLine (food to 0.zz)
        }
        it("Given 1 space left When produce 2 Then max capped") {
            user.enableAndSet(Land(), 10.z)
            val granary = user.enableAndSet(Granary(), 1.z)
            val diff = 1
            val food = user.enableAndSet(Food(), granary.totalStorageAmount - diff)
            user.enableAndSet(Farm(), 1.z)

            val report = turner.collectAndExecuteNextTurnReport()

            food.owned shouldBeEqual granary.totalStorageAmount
            report.resourceReportLines shouldContainLine (food to diff.zz)
        }
    }
    describe("citizens") {
        it("Given sufficient citizens Then increase gold") {
            val gold = user.enable(Gold())
            val targetCitizens = (1.0 / Mechanics.citizenTax.value).toLong().z
            val house = House()
            val housesNeeded = ceil(targetCitizens.value.toDouble() / house.storageAmount.value).toLong().z
            user.enableAndSet(Land(), housesNeeded * house.landUse)
            user.enableAndSet(house, housesNeeded)
            user.enableAndSet(Citizen(), targetCitizens)
            user.enable(Food())

            val report = turner.collectAndExecuteNextTurnReport()

            report.resourceReportLines shouldContainLine (gold to 1.zz)
            gold.owned shouldBeEqual 1.z
        }
    }
})
