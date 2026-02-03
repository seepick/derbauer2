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

    // TODO fix test cases
//    it("Given some 🙎🏻‍♂️ and too little 🍖 Then all available food 🍖 consumed") {
//        val food = user.addAndSet(Food(), 1.z)
//        user.addAndSet(Citizen(), 10.z)
//
//        expectResourceChange(food, -food.owned)
//    }
//    it("Given 100 citizen and almost full Then increase capped") {
//        user.givenStorage<Citizen>(102.z)
//        val citizen = user.addAndSet(Citizen(), 100.z)
//
//        val changes = CitizenReproduceTurnStep(user).calcResourceChanges()
//
//        changes.shouldContainChange(citizen, 2.zz)
//    }

//    it("Given almost full storage Then produce diff") {
//        `user with 0 🍖, 1 granary, 1 farm` {
//            val diff = 1.z
//            food.ownedForTest = granary.totalStorageAmount - diff
//
//            calcStepChanges().shouldContainChange(food, diff)
//        }
//    }
//    it("Given full storage Then produce zero") {
//        `user with 0 🍖, 1 granary, 1 farm` {
//            food.ownedForTest = granary.totalStorageAmount
//
//            calcStepChanges().shouldContainChange(food, 0.z)
//        }
//    }
//    it("When 2x but storage limited Then produce up to free storage") {
//        `user with 0 🍖, 1 granary, 1 farm` {
//            val diff = 1.z
//            food.ownedForTest = granary.totalStorageAmount - diff
//            user.add(ResourceProductionMultiplierStub(Food::class, 2.0))
//
//            calcStepChanges().shouldContainChange(food, diff)
//        }
//    }
//    it("When modifier makes negative beyond owned Then clamp to -owned") {
//        `user with 0 🍖, 1 granary, 1 farm` {
//            farm.ownedForTest = 1.z
//            val ownedFood = 1.z
//            food.ownedForTest = ownedFood
//            user.add(ResourceProductionMultiplierStub(Food::class, -2.0))
//
//            calcStepChanges().shouldContainChange(food, -ownedFood)
//        }
//    }

    "Given storage 9/10 and steps +5, -1 Then food changes by +1 and not 0 (because of wrong cap order application)" {
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
