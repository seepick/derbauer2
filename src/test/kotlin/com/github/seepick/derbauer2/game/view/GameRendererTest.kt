package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.building.addBuilding
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.addResource
import com.github.seepick.derbauer2.game.testInfra.PageTest
import com.github.seepick.derbauer2.game.testInfra.pageParser.renderGamePage
import com.github.seepick.derbauer2.game.turn.CurrentTurn
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

@Suppress("MaxLineLength")
class GameRendererTest : PageTest, StringSpec({
    "Given some assets and page data Then render full page" {
        renderGamePage({ ctx ->
            ctx.user.addResource(Gold(), 999.z)
            ctx.user.addResource(Land(), 30.z)
            ctx.user.addBuilding(House(), 1.z)
            ctx.user.addResource(Citizen(), 1.z)
            ctx.user.addBuilding(Granary(), 1.z)
            ctx.user.addResource(Food(), 3.z)
            DummyPage(
                user = ctx.user,
                turn = ctx.currentTurn,
                pageContent = "pageContent",
                promptIndicator = "promptIndicator",
                metaOptions = listOf(MetaOptionImpl(KeyPressed.Command.Escape, "metaLabel")),
            )
        }) {
            fullPage shouldBeEqual """
                💰 999 | 🍖 3 / 80 | 🌍 3 / 30 | 🙎🏻‍♂️ 1 / 10                                                                🌸  W1 Y1
                ==============================================================================================================
                                                                                                                              
                pageContent                                                                                                   
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                                                                                                                              
                ==============================================================================================================
                [promptIndicator]> ▉                                                                         ESCAPE: metaLabel
                """.trimIndent()
        }
    }
})

@Suppress("LongParameterList")
class DummyPage(
    user: User,
    turn: CurrentTurn,
    private val pageContent: String = "pageContent",
    private val promptIndicator: String = "promptIndicator",
    private val metaOptions: List<MetaOption> = listOf(MetaOptionImpl(KeyPressed.Command.Escape, "metaLabel")),
) : Page {
    private val gameRenderer = GameRenderer(user, turn)
    override fun render(textmap: Textmap) {
        gameRenderer.render(textmap, promptIndicator, metaOptions) {
            textmap.line(pageContent)
        }
    }

    override fun onKeyPressed(key: KeyPressed) = false
}
