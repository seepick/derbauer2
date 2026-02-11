package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.feature.addFeature
import com.github.seepick.derbauer2.game.testInfra.PageTest
import com.github.seepick.derbauer2.game.testInfra.pageParser.GamePageContext
import com.github.seepick.derbauer2.game.testInfra.pageParser.renderGamePage
import com.github.seepick.derbauer2.game.view.HomePage
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.string.shouldContain

class InfoBarPageTest : PageTest, DescribeSpec({

    fun GamePageContext.buildAnyGamePage() =
        HomePage(turner, currentPage, gameRenderer, user)

    describe("Turn info bar") {
        it("Given no season feature Then render turn") {
            renderGamePage(GamePageContext::buildAnyGamePage) {
                infoRight shouldContain "Turn 1"
            }
        }
        it("Given season feature Then render season details") {
            renderGamePage({ ctx ->
                ctx.user.addFeature(SeasonFeature())
                ctx.buildAnyGamePage()
            }) {
                infoRight shouldContain "🌸  W1 Y1"
            }
        }
    }
})
