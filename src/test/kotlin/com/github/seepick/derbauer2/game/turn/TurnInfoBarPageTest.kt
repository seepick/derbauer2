package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.feature.addFeature
import com.github.seepick.derbauer2.game.testInfra.PageTest
import com.github.seepick.derbauer2.game.testInfra.pageParser.GamePageContext
import com.github.seepick.derbauer2.game.testInfra.pageParser.renderGamePage
import com.github.seepick.derbauer2.game.view.HomePage
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.string.shouldContain

class TurnInfoBarPageTest : PageTest, DescribeSpec({

    fun GamePageContext.buildPage() =
        HomePage(turner, currentPage, gameRenderer, user, reports)

    describe("Turn info bar") {
        it("Given no season feature Then render turn") {
            renderGamePage(GamePageContext::buildPage) {
                infoRight shouldContain "Turn 1"
            }
        }
        it("Given season feature Then render season details") {
            renderGamePage({ ctx ->
                ctx.user.addFeature(SeasonFeature())
                ctx.buildPage()
            }) {
                infoRight shouldContain "🌸  W1 Y1"
            }
        }
    }
})
