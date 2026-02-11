package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.testInfra.PageTest
import com.github.seepick.derbauer2.game.testInfra.pageParser.GamePageContext
import com.github.seepick.derbauer2.game.testInfra.pageParser.renderGamePage
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

class HomePageTest : PageTest, StringSpec({

    fun GamePageContext.buildHomePage() =
        HomePage(turner, currentPage, gameRenderer, user)

    "Given init assets Then render home page content" {
        renderGamePage({ it.buildHomePage() }) {
            contentString shouldBeEqual """
                You feel cozy in your ${it.user.cityTitle.label}... 🏠
                What shall we do next, ${it.user.userTitle.label}?
                
                [1] Build 🛠️
                """.trimIndent()
        }
    }
})
