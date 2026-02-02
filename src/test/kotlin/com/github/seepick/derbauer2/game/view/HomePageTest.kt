package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.initAssets
import com.github.seepick.derbauer2.game.testInfra.PageTest
import com.github.seepick.derbauer2.game.testInfra.pageParser.renderGamePage
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

class HomePageTest : PageTest, StringSpec({
    "Given init assets Then render home page".config(enabled = false) {
        renderGamePage({ ctx ->
            ctx.user.initAssets()
            HomePage(ctx.turner, ctx.currentPage, ctx.gameRenderer, ctx.user)
        }) {
            this.fullPage shouldBeEqual """
                🌍 10 | 💰 500 | 🍖 50 / 100 | 🌍 3 / 10 | 🙎🏻‍♂️ 4 / 5                      Turn 1
                ================================================================================
                You are home... 🏠                                                              
                                                                                                
                What shall we do next, Sir?                                                     
                                                                                                
                [1] Trade 💰                                                                    
                [2] Build 🛠️                                                                   
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                ================================================================================
                [1-2]> ▉                                                        ENTER: Next Turn
            """.trimIndent()
//            this.lineInfo shouldBeEqual "asd"
        }
    }
})
