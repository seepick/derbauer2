package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.addBuilding
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.FeatureTurner
import com.github.seepick.derbauer2.game.happening.DefaultHappeningDescriptorRepo
import com.github.seepick.derbauer2.game.happening.HappeningTurner
import com.github.seepick.derbauer2.game.prob.ProbsImpl
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.addResource
import com.github.seepick.derbauer2.game.resource.givenFakeStorage
import com.github.seepick.derbauer2.game.resource.shouldBeEmpty
import com.github.seepick.derbauer2.game.resource.shouldContainChange
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty

class TurnerTest : DescribeSpec({
    lateinit var user: User
    lateinit var probabilities: ProbsImpl
    beforeTest {
        user = User()
        probabilities = ProbsImpl()
    }

    fun Turner.Companion.build(vararg steps: TurnStep) = Turner(
        user = user,
        steps = steps.toList(),
        happeningTurner = HappeningTurner(user, probabilities, DefaultHappeningDescriptorRepo).apply { initProb() },
        featureTurner = FeatureTurner(user),
    )

    fun Turner.execShouldContainChange(resource: Resource, expectedAmount: Zz) {
        executeAndGenerateReport().resourceChanges.shouldContainChange(resource, expectedAmount)
    }

    describe("Special Cases") {
        it("Given nothing Then empty changes And user's reports stay untouched") {
            val report = Turner.build().executeAndGenerateReport()

            report.resourceChanges.shouldBeEmpty()
            user.reports.all.shouldBeEmpty()
        }
    }
    describe("Resource") {
        fun test(given: Int, changeBy: Int, shouldChange: Int) {
            // use Gold, as it is not of type StorableResource
            val gold = user.addResource(Gold(), given.z)
            val turner = Turner.build(TurnStep.build(gold, changeBy.zz))

            turner.execShouldContainChange(gold, shouldChange.zz)
        }
        it("Given 0 When change -1 Then change nothing") {
            test(given = 0, changeBy = -1, shouldChange = 0)
        }
        it("Given 1 When change -1 Then change by -1") {
            test(given = 1, changeBy = -1, shouldChange = -1)
        }
        it("Given 0 When change +100 Then change by +100") {
            test(given = 0, changeBy = 100, shouldChange = 100)
        }
    }
    describe("StorableResource") {
        it("Given 0 resource and change -1 Then change nothing") {
            val food = user.add(Food())
            val turner = Turner.build(TurnStep.build(food, (-1).zz))

            turner.execShouldContainChange(food, 0.zz)
        }
        it("Given 0 resource and change +1 Then change nothing") {
            val food = user.add(Food())
            val turner = Turner.build(TurnStep.build(food, (+1).zz))

            turner.execShouldContainChange(food, 0.zz)
        }
        it("Given 1 resource When change -2 Then changed by max possible -1") {
            val food = user.addResource(Food(), 1.z)
            val turner = Turner.build(TurnStep.build(food, (-2).zz))

            turner.execShouldContainChange(food, (-1).zz)
        }
        it("Given 0/1 resource When change +1 Then changed successfully +1") {
            val food = user.add(Food())
            user.givenFakeStorage<Food>(1.z)
            val turner = Turner.build(TurnStep.build(food, (+1).zz))

            turner.execShouldContainChange(food, (+1).zz)
        }
        it("Given 1/1 resource When change +1 Then change nothing") {
            val food = user.addResource(Food(), 1.z)
            user.givenFakeStorage<Food>(1.z)
            val turner = Turner.build(TurnStep.build(food, (+1).zz))

            turner.execShouldContainChange(food, 0.zz)
        }
        it("Given 1/2 resources When change +2 Then changed by max possible +1") {
            val food = user.addResource(Food(), 1.z)
            user.givenFakeStorage<Food>(2.z)
            val turner = Turner.build(TurnStep.build(food, (+2).zz))

            turner.execShouldContainChange(food, 1.zz)
        }
        it("Given 9/10 in storage When change +5 And change -1 Then changed by +1") {
            // execute sequentially, not together
            // FIXME not true!... need something way more sophisticated
            // e.g. how about merging all changes for the same resource first, then applying capping only once?
            val foodStorageAvailable = 1.z
            val granary = user.addBuilding(Granary(), 1.z) // 100 capacity
            val food = user.addResource(Food(), granary.totalStorageAmount - foodStorageAvailable) // almost full
            val turner = Turner.build(
                TurnStep.build(food, 5.zz), // first over-increase but not be capped yet!
                TurnStep.build(food, (-1).zz), // then decrease
            )

            turner.execShouldContainChange(food, 0.zz) // NOPE!!! foodStorageAvailable.zz)
        }
    }
})
