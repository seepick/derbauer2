package com.github.seepick.derbauer2.game.happening

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
import com.github.seepick.derbauer2.game.testInfra.DslTest
import com.github.seepick.derbauer2.game.testInfra.dsl.Given
import com.github.seepick.derbauer2.game.testInfra.dsl.Then
import com.github.seepick.derbauer2.game.testInfra.dsl.When
import com.github.seepick.derbauer2.game.testInfra.installDslExtension
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.types.shouldBeInstanceOf

class HappeningDslTest : DslTest, DescribeSpec() {
    init {
        installDslExtension()
        describe("When turn and continue next page") {
            it("Given FoundGold is happening Then it happens And user gets 💰") {
                Given {
                    setOwned<Gold>(0.z)
                    mockHappeningDescriptorRepoReturns(FoundGoldDescriptor)
                    probs.fixateProvider(ProbProviderKey.happeningTurner, true)
                    probs.fixateProvider(ProbProviderKey.happeningIsNegative, false)
                    probs.fixateSelectorFirst(ProbSelectorKey.happeningChoice)
                } When {
                    nextTurnToReport {
                        nextPage()
                    }
                } Then {
                    val page = page.shouldBeInstanceOf<HappeningPage>()
                    val view = page.multiView.current().shouldBeInstanceOf<FoundGoldHappening>()
                    user.gold shouldBeEqual view.goldFound
                }
            }
        }
    }
}
