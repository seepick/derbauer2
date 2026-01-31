package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.testInfra.PageTest
import com.github.seepick.derbauer2.game.testInfra.pageParser.SetupGamePageContext
import com.github.seepick.derbauer2.game.testInfra.pageParser.renderGamePage
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldContainIgnoringCase

class BuildingsPageTest : PageTest, StringSpec({

    fun SetupGamePageContext.buildBuildingsPage() =
        BuildingsPage(user, currentPage, gameRenderer, resultHandler)

    "Given some Then check whole screen" {
        renderGamePage({ ctx ->
            ctx.user.enable(House())
            ctx.user.enable(Farm())
            ctx.buildBuildingsPage()
        }) {
            fullPage shouldBeEqual """
                                                                                      Turn 1
            ================================================================================
            Your builders are ready for work.                                               
                                                                                            
            What shall we build next, Sire?                                                 
                                                                                            
            [1] Build House - 💰 40 | 🌍 1 (owned: 0)                                       
            [2] Build Farm - 💰 120 | 🌍 4 (owned: 0)                                       
                                                                                            
                                                                                            
                                                                                            
                                                                                            
                                                                                            
                                                                                            
                                                                                            
                                                                                            
                                                                                            
                                                                                            
                                                                                            
                                                                                            
                                                                                            
                                                                                            
                                                                                            
            ================================================================================
            [1-2]> ▉                                                             ENTER: Back
        """.trimIndent()
        }
    }
    "Given nothing Then not a single thing to build" {
        renderGamePage({ ctx ->
            ctx.buildBuildingsPage()
        }) {
            contentLinesString shouldContainIgnoringCase "Not a single thing to build"
            promptLeft shouldContain "[ENTER]"
            promptRight shouldContain "ENTER: Back"
        }
    }
})
