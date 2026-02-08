package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.common.strict11
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.happening.happenings.FoundGoldDescriptor
import com.github.seepick.derbauer2.game.happening.happenings.FoundGoldHappening
import com.github.seepick.derbauer2.game.prob.ProbProviderKey
import com.github.seepick.derbauer2.game.prob.ProbSelectorKey
import com.github.seepick.derbauer2.game.prob.fixateProvider
import com.github.seepick.derbauer2.game.prob.fixateSelectorFirst
import com.github.seepick.derbauer2.game.prob.probs
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.gold
import com.github.seepick.derbauer2.game.stat.Happiness
import com.github.seepick.derbauer2.game.stat.happiness
import com.github.seepick.derbauer2.game.testInfra.DslTest
import com.github.seepick.derbauer2.game.testInfra.dsl.Given
import com.github.seepick.derbauer2.game.testInfra.dsl.Then
import com.github.seepick.derbauer2.game.testInfra.dsl.When
import com.github.seepick.derbauer2.game.testInfra.installDslExtension
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.types.shouldBeInstanceOf


class HappeningDslTest : DslTest, StringSpec() {
    init {
        installDslExtension()
        "Given FoundGold is happening Then increased happiness and 💰" {
            Given {
                setOwned<Gold>(0.z)
                registerHappeningDescriptors(FoundGoldDescriptor)
                probs.fixateProvider(ProbProviderKey.happeningTurner, true)
                probs.fixateProvider(ProbProviderKey.happeningIsNegative, false)
                probs.fixateSelectorFirst(ProbSelectorKey.happeningChoice)
            } When {
                nextTurnToReport {
                    nextPage()
                }
            } Then {
                val page = page.shouldBeInstanceOf<HappeningPage>()
                val view = page.multiView.currentUnseen().shouldBeInstanceOf<FoundGoldHappening>()
                user.gold shouldBeEqual view.goldFound
            }
        }
        "Given positive happening Then +happiness" {
            val initialHappiness = 0.0.strict11
            Given {
                setOwned<Gold>(0.z)
                setStatD11<Happiness>(initialHappiness)
                registerHappeningDescriptors(HappeningDescriptorStub(HappeningNature.Positive, canHappen = true))
                probs.fixateProvider(ProbProviderKey.happeningTurner, true)
                println("111: ${user.happiness}")
                probs.fixateProvider(ProbProviderKey.happeningIsNegative, false)
                probs.fixateSelectorFirst(ProbSelectorKey.happeningChoice)
            } When {
                nextTurnToReport {
                    nextPage()
                    nextPage()
                }
            } Then {
                user.happiness shouldBeGreaterThan initialHappiness
            }
        }
    }
}
