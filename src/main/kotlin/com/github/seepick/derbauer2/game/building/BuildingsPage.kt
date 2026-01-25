package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.HomePage
import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.view.Back
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.textengine.Beeper
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Prompt
import com.github.seepick.derbauer2.textengine.SelectOption
import com.github.seepick.derbauer2.textengine.Textmap

class BuildingsPage(
    private val game: Game,
    private val user: User,
    private val builder: Builder,
    private val currentPage: CurrentPage,
    private val gameRenderer: GameRenderer,
    private val beeper: Beeper,
) : Page {

    private val back = Back {
        currentPage.page = HomePage::class
    }
    private val prompt = Prompt.Select(
        title = "What shall we build next?",
        user.buildings.map { building ->
            SelectOption({ "Buy ${building.labelSingular} for ${building.costsGold} (got ${building.owned})" }) {
                handleBuildingResult(builder.build(building))
            }
        }
    )

    private fun handleBuildingResult(result: BuildResult) {
        when(result) {
            BuildResult.Success -> {} // do nothing
            BuildResult.InsufficientResources -> beeper.beep("Not enough resources to build.")
        }
        // TODO solve central place; display message
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
