package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.testInfra.PageTest
import com.github.seepick.derbauer2.game.testInfra.pageParser.SetupGamePageContext
import com.github.seepick.derbauer2.game.testInfra.pageParser.renderGamePage
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldContainIgnoringCase

class BuildingPageTest : PageTest, StringSpec({

    fun SetupGamePageContext.buildBuildingsPage() =
        BuildingPage(user, currentPage, gameRenderer, resultHandler)

    "Given some Then check whole screen" {
        renderGamePage({ ctx ->
            ctx.user.add(House())
            ctx.user.add(Farm())
            ctx.buildBuildingsPage()
        }) {
            contentString shouldBeEqual """
                Your builders are ready for work.
                
                What shall we build next, Sire?
                
                [1] Build House (0) - 💰 40 | 🌍 1
                [2] Build Farm (0) - 💰 120 | 🌍 4
                """.trimIndent()
        }
    }
    "Given nothing Then not a single thing to build" {
        renderGamePage({ ctx ->
            ctx.buildBuildingsPage()
        }) {
            contentString shouldContainIgnoringCase "Not a single thing to build"
            promptLeft shouldContain "[ENTER]"
            promptRight shouldContain "ENTER: Back"
        }
    }
})
