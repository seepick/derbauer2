package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.engine.CurrentPage
import com.github.seepick.derbauer2.engine.KeyPressed
import com.github.seepick.derbauer2.engine.Page
import com.github.seepick.derbauer2.engine.Prompt
import com.github.seepick.derbauer2.engine.SelectOption
import com.github.seepick.derbauer2.engine.Textmap
import com.github.seepick.derbauer2.game.logic.BuyResult
import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.game.view.MetaOption

class BuildingsPage(
    private val game: Game,
    private val user: User,
    private val currentPage: CurrentPage,
    private val gameRenderer: GameRenderer,
) : Page {

    private val backKey = KeyPressed.Command.Escape

    private val prompt = Prompt.Select(
        title = "What shall we build next?",
        user.buildings.map { building ->
            SelectOption({ "Buy ${building.labelSingular} for ${building.costsGold} (got ${building.units.formatted})" }) {
                handleBuyResult(game.buyBuilding(building))
            }
        }
    )

    private fun handleBuyResult(result: BuyResult) {
        // TODO do something with it...?
    }

    override fun renderText(textmap: Textmap) {
        gameRenderer.render(textmap, prompt.inputIndicator + "/" + backKey.key, listOf(MetaOption(backKey, "Back"))) {
            textmap.printLine("Read for work.")
            textmap.printEmptyLine()
            prompt.render(textmap)
        }
    }

    override fun onKeyPressed(key: KeyPressed): Boolean {
        if (key == backKey) {
            currentPage.page = HomePage::class
            return true
        }
        return prompt.onKeyPressed(key)
    }
}