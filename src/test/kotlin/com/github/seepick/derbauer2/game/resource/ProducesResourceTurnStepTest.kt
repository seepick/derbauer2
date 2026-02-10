package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.Field
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.addBuilding
import com.github.seepick.derbauer2.game.common.`%`
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import com.github.seepick.derbauer2.game.testInfra.zz
import com.github.seepick.derbauer2.game.transaction.DefaultTxValidatorRegistry
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.comparables.shouldBeGreaterThan
import kotlin.math.floor

class ProducesResourceTurnStepTest : DescribeSpec({

    data class SetupContext(
        val step: ProducesResourceTurnStep,
        val user: User,
        val food: Food,
        val field: Field,
        val granary: Granary,
    )

    fun `user with 0 🍖, 1 granary, 1 field`(
        test: SetupContext.() -> Unit,
    ) {
        val user = User(DefaultTxValidatorRegistry.validators)
        val food = user.add(Food())
        val field = user.addBuilding(Field(), 1.z)
        val granary = user.addBuilding(Granary(), 1.z)

        test(SetupContext(ProducesResourceTurnStep(user), user, food, field, granary))
    }

    describe("happy cases") {
        it("Given ok setup Then produce And resource untouched") {
            `user with 0 🍖, 1 granary, 1 field` {
                step.calcResourceChanges().shouldContainChange(
                    food, 1.z * field.produceResourceAmount
                )
            }
        }
        it("Given 2 fields Then produce both") {
            `user with 0 🍖, 1 granary, 1 field` {
                field.ownedForTest = 2.z

                step.calcResourceChanges().shouldContainChange(
                    food, 2.z * field.produceResourceAmount
                )
            }
        }
        // TEST two distinct entities both producing same resource taken into account
    }
    describe("edge cases") {
        it("Given nothing Then do nothing") {
            val user = User()

            val actualChanges = ProducesResourceTurnStep(user).calcResourceChanges()

            actualChanges.shouldBeEmpty()
        }
        it("Producing above the storage capacity As limit caps are done in Turner") {
            `user with 0 🍖, 1 granary, 1 field` {
                food.ownedForTest = granary.totalStorageAmount - 1.z
                step.calcResourceChanges().shouldBeSingleton().first().amount.value shouldBeGreaterThan 1
            }
        }
    }

    describe("modifier interactions") {
        it("When bonus 20% Then produce 100x") {
            `user with 0 🍖, 1 granary, 1 field` {
                val bonus = 20.`%`
                user.add(ResourceProductionBonusEntityStub(Food::class, bonus))

                step.calcResourceChanges()
                    .shouldContainChange(food, field.produceResourceAmount.timesFloor(bonus + 1.0))
            }
        }
        it("When 2 bonuses given Then apply them on base value together") {
            `user with 0 🍖, 1 granary, 1 field` {
                val baseProduction = field.totalProduceResourceAmount
                val bonus1 = 50.`%`
                val bonus2 = 100.`%`
                user.add(ResourceProductionBonusEntityStub1(Food::class, bonus1))
                user.add(ResourceProductionBonusEntityStub2(Food::class, bonus2))

                val expectedFinalProduction = floor(baseProduction.toDouble() * (1.0 + (bonus1 + bonus2).number))
                step.calcResourceChanges().shouldContainChange(food, expectedFinalProduction.zz)
            }
        }
    }
})
