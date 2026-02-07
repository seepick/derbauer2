package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.core.Texts
import com.github.seepick.derbauer2.game.testInfra.PageTest
import com.github.seepick.derbauer2.game.testInfra.pageParser.SetupGamePageContext
import com.github.seepick.derbauer2.game.testInfra.pageParser.renderGamePage
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldContainIgnoringCase

class BuildingPageTest : PageTest, StringSpec({

    fun SetupGamePageContext.buildBuildingsPage() =
        BuildingPage(user, currentPage, gameRenderer, resultHandler, buildingService)

    "Given some Then check content" {
        renderGamePage({ ctx ->
            ctx.user.add(House())
            ctx.user.add(Farm())
            ctx.buildBuildingsPage()
        }) {
            contentString shouldBeEqual """
                ${Texts.buildingPage}
                
                [1] Build House 💰 40  🌍 1 (0)
                [2] Build Farm  💰 120 🌍 4 (0)
                """.trimIndent()
        }
    }
    "Given nothing Then not a single thing to build" {
        renderGamePage({ it.buildBuildingsPage() }) {
            contentString shouldContainIgnoringCase "Not a single thing to build"
            promptLeft shouldContain "[ENTER]"
            promptRight shouldContain "ENTER: Back"
        }
    }
})
