package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.testInfra.PageTest
import com.github.seepick.derbauer2.game.testInfra.pageParser.SetupGamePageContext
import com.github.seepick.derbauer2.game.testInfra.pageParser.renderGamePage
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

class HomePageTest : PageTest, StringSpec({

    fun SetupGamePageContext.buildHomePage() =
        HomePage(turner, currentPage, gameRenderer, user, reports)

    "Given init assets Then render home page content" {
        renderGamePage({ it.buildHomePage() }) {
            contentString shouldBeEqual """
                You are home... 🏠
                What shall we do next, Sir?

                [1] Build 🛠️
                """.trimIndent()
        }
    }
})
