package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.addAndSet
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.FeatureTurner
import com.github.seepick.derbauer2.game.happening.DefaultHappeningDescriptorRepo
import com.github.seepick.derbauer2.game.happening.HappeningTurner
import com.github.seepick.derbauer2.game.prob.ProbsImpl
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.shouldContainChange
import com.github.seepick.derbauer2.game.resource.totalLandUse
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty

class TurnerTest : StringSpec({
    lateinit var user: User
    lateinit var probabilities: ProbsImpl
    beforeTest {
        user = User()
        probabilities = ProbsImpl()
    }

    fun turner(steps: List<TurnStep> = emptyList()) = Turner(
        user = user,
        steps = steps,
        happeningTurner = HappeningTurner(user, probabilities, DefaultHappeningDescriptorRepo).apply { initProb() },
        featureTurner = FeatureTurner(user),
    )

    "User's reports stay untouched" {
        turner().executeAndGenerateReport()

        user.reports.all.shouldBeEmpty()
    }
    // FIXME implement this; shift limitting/capping functionality into turner (out of single steps)
    // first merge ResourceChanges, then compute limit :)
    "Given almost full storage and 2 steps opposite change Then both merged and limitted".config(enabled = false) {
        val foodStorageAvailable = 1.z
        val granary = user.addAndSet(Granary(), 1.z)
        val land = user.add(Land())
        land.ownedForTest = user.totalLandUse
        val food = user.add(Food())
        food.ownedForTest = granary.totalStorageAmount - foodStorageAvailable // almost full

        val turner = turner(
            steps = listOf(
                TurnStep.build(food, 5.zz), // first over-increase but not be capped yet!
                TurnStep.build(food, (-1).zz), // then decrease
            )
        )

        turner.executeAndGenerateReport().resourceChanges.shouldContainChange(food, foodStorageAvailable)
    }
})
