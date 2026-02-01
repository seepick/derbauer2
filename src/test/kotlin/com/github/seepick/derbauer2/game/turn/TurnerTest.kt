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
import com.github.seepick.derbauer2.game.happening.happenings.DefaultHappeningDescriptorRepo
import com.github.seepick.derbauer2.game.probability.ProbabilitiesImpl
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.ResourceTurner
import com.github.seepick.derbauer2.game.testInfra.User
import com.github.seepick.derbauer2.game.testInfra.enableAndSet
import com.github.seepick.derbauer2.game.testInfra.shouldContainChange
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.types.shouldBeSameInstanceAs
import kotlin.math.ceil

class TurnerTest : DescribeSpec({
    lateinit var user: User
    lateinit var turner: Turner
    lateinit var reports: ReportIntelligence
    lateinit var probabilities: ProbabilitiesImpl
    beforeTest {
        user = User()
        reports = ReportIntelligence()
        probabilities = ProbabilitiesImpl()
        val happeningTurner = HappeningTurner(user, probabilities, DefaultHappeningDescriptorRepo)
        happeningTurner.registerProbabilities()
        turner = Turner(
            happeningTurner = happeningTurner,
            resourceTurner = ResourceTurner(user),
            citizenTurner = CitizenTurner(user),
            featureTurner = FeatureTurner(user),
            reports = reports,
            user = user,
            resourceTurnSteps = emptyList(),
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
    describe("When produce food") {
        it("Given producer and storage When produce Then owned increased and report contains") {
            user.enableAndSet(Land(), 10.z)
            val farm = user.enableAndSet(Farm(), 1.z)
            user.enableAndSet(Granary(), 1.z)
            val food = user.enable(Food())

            val report = turner.collectAndExecuteNextTurnReport()

            food.owned shouldBeEqual farm.totalProducingResourceAmount
            report.resourceChanges shouldContainChange (food to farm.totalProducingResourceAmount.asZz)
        }
        it("Given no food storage When produce Then stay 0") {
            user.enableAndSet(Land(), 10.z)
            user.enableAndSet(Farm(), 1.z)
            val food = user.enable(Food())

            val report = turner.collectAndExecuteNextTurnReport()

            food.owned shouldBeEqual 0.z
            report.resourceChanges shouldContainChange (food to 0.zz)
        }
        it("Given 1 space left When produce 2 Then max capped") {
            user.enableAndSet(Land(), 10.z)
            val granary = user.enableAndSet(Granary(), 1.z)
            val diff = 1
            val food = user.enableAndSet(Food(), granary.totalStorageAmount - diff)
            user.enableAndSet(Farm(), 1.z)

            val report = turner.collectAndExecuteNextTurnReport()

            food.owned shouldBeEqual granary.totalStorageAmount
            report.resourceChanges shouldContainChange (food to diff.zz)
        }
    }
    describe("citizens pay taxes") {
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

            report.resourceChanges shouldContainChange (gold to 1.zz)
            gold.owned shouldBeEqual 1.z
        }
    }
    describe("game over") {
        it("Given no entity Then game cant be over") {
            val report = turner.collectAndExecuteNextTurnReport()

            report.isGameOver shouldBeEqual false
        }
        it("Given >0 citizens When take turn Then game is not over") {
            val house = House()
            user.enableAndSet(Land(), house.landUse)
            user.enableAndSet(house, 1.z)
            user.enableAndSet(Citizen(), 1.z)

            val report = turner.collectAndExecuteNextTurnReport()

            report.isGameOver shouldBeEqual false
        }
        it("Given no citizens When take turn Then game is over") {
            user.enableAndSet(Citizen(), 0.z)

            val report = turner.collectAndExecuteNextTurnReport()

            report.isGameOver shouldBeEqual true
        }
    }
})
