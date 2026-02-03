package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.building.addBuilding
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.addResource
import com.github.seepick.derbauer2.game.testInfra.PageTest
import com.github.seepick.derbauer2.game.testInfra.pageParser.renderGamePage
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

class HomePageTest : PageTest, StringSpec({
    "Given init assets Then render home page" {
        renderGamePage({ ctx ->
            ctx.user.addResource(Gold(), 999.z)
            ctx.user.addResource(Land(), 30.z)
            ctx.user.addBuilding(House(), 1.z)
            ctx.user.addResource(Citizen(), 1.z)
            ctx.user.addBuilding(Granary(), 1.z)
            ctx.user.addResource(Food(), 3.z)
            HomePage(ctx.turner, ctx.currentPage, ctx.gameRenderer, ctx.user)
        }) {
            this.fullPage shouldBeEqual """
                🌍 30 | 💰 999 | 🍖 3 / 100 | 🌍 3 / 30 | 🙎🏻‍♂️ 1 / 5                             Turn 1
                ================================================================================
                You are home... 🏠                                                               
                                                                                                
                What shall we do next, Sir?                                                     
                                                                                                
                [1] Build 🛠️                                                                     
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                ================================================================================
                [1-1]> ▉                                                        ENTER: Next Turn
                """.trimIndent()
        }
    }
})
