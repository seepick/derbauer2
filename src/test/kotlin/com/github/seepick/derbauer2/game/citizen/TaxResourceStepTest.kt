package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.PassThroughDiffuser
import com.github.seepick.derbauer2.game.prob.ProbDiffuserKey
import com.github.seepick.derbauer2.game.prob.ProbsStub
import com.github.seepick.derbauer2.game.prob.StaticDiffuser
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.shouldContainChange
import com.github.seepick.derbauer2.game.tech.CapitalismTech
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class TaxResourceStepTest : DescribeSpec(
    {
        lateinit var user: User
        lateinit var probs: ProbsStub
        lateinit var step: TaxResourceTurnStep
        beforeTest {
            user = User()
            probs = ProbsStub()
            step = TaxResourceTurnStep(user, probs)
            step.initProb()
            probs.fixateDiffuser(ProbDiffuserKey.taxKey, PassThroughDiffuser)
        }

        fun calcChanges() = step.calcChanges()

        context("Given 💰 and 🙎🏻‍♂️") {
            lateinit var gold: Gold
            lateinit var citizen: Citizen
            beforeTest {
                gold = user.add(Gold())
                citizen = user.add(Citizen())
            }
            describe("Regular case") {
                it("Given some 🙎🏻‍♂️ Then change increases 💰 But user's  💰 stays unchanged") {
                    val goldBefore = gold.owned
                    citizen.ownedForTest = 10.z

                    calcChanges().shouldContainChange(Gold::class, (citizen.owned * Mechanics.taxRate).zz)
                    gold.owned shouldBeEqual goldBefore
                }
            }
            describe("Diffusion") {
                it("Given negative diffused value Then limit 💰 to 0") {
                    probs.fixateDiffuser(ProbDiffuserKey.taxKey, StaticDiffuser(staticValue = (-1).zz))

                    calcChanges().shouldContainChange(Gold::class, 0.zz)
                }
            }
            describe("capitalism") {
                it("Given capitalism Then tax increased") {
                    citizen.ownedForTest = 100.z
                    user.add(CapitalismTech())
                    val taxWithoutCapitalism = (citizen.owned * Mechanics.taxRate).zz
                    val expectedTax = taxWithoutCapitalism.timesFloor(Mechanics.techCapitalismTaxMultiplier)

                    calcChanges().shouldContainChange(Gold::class, expectedTax)
                }
            }
        }
    },
)
