package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.logic.BuyResult
import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.view.Back
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.viewer.CurrentPage
import com.github.seepick.derbauer2.viewer.KeyPressed
import com.github.seepick.derbauer2.viewer.Page
import com.github.seepick.derbauer2.viewer.Prompt
import com.github.seepick.derbauer2.viewer.SelectOption
import com.github.seepick.derbauer2.viewer.Textmap

class BuildingsPage(
    private val game: Game,
    private val user: User,
    private val currentPage: CurrentPage,
    private val gameRenderer: GameRenderer,
) : Page {

    private val back = Back {
        currentPage.page = HomePage::class
    }
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
        gameRenderer.render(textmap, prompt.inputIndicator, listOf(back.option)) {
            textmap.printLine("Read for work.")
            textmap.printEmptyLine()
            prompt.render(textmap)
        }
    }

    override fun onKeyPressed(key: KeyPressed): Boolean =
        listOf(back, prompt).any { it.onKeyPressed(key) }
}
