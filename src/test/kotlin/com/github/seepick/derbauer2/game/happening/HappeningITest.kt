package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.gold
import com.github.seepick.derbauer2.game.happening.happenings.FoundGoldDescriptor
import com.github.seepick.derbauer2.game.happening.happenings.FoundGoldHappening
import com.github.seepick.derbauer2.game.happening.happenings.HappeningDescriptor
import com.github.seepick.derbauer2.game.probability.AlwaysFirstProbabilitySelector
import com.github.seepick.derbauer2.game.probability.AlwaysProbabilityCalculator
import com.github.seepick.derbauer2.game.probability.ProbabilityProviderSource.HappeningIsNeg
import com.github.seepick.derbauer2.game.probability.ProbabilityProviderSource.HappeningTurner
import com.github.seepick.derbauer2.game.probability.ProbabilitySelectorSource.Happenings
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.testInfra.itest.Given
import com.github.seepick.derbauer2.game.testInfra.itest.ITest
import com.github.seepick.derbauer2.game.testInfra.itest.Then
import com.github.seepick.derbauer2.game.testInfra.itest.When
import com.github.seepick.derbauer2.game.testInfra.itest.installGameKoinExtension
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.types.shouldBeInstanceOf

class HappeningITest : ITest, DescribeSpec() {
    init {
        installGameKoinExtension()
        describe("When turn and continue next page") {
            it("Given probability for GoldFound is guaranteed Then it happens") {
                Given {
                    setOwned<Gold>(0.z)
                    mockHappeningDescriptorRepoReturns(FoundGoldDescriptor)
                    probability {
                        providers += HappeningTurner to AlwaysProbabilityCalculator(true)
                        providers += HappeningIsNeg to AlwaysProbabilityCalculator(false)
                        selectors += Happenings to AlwaysFirstProbabilitySelector<HappeningDescriptor>()
                    }
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
