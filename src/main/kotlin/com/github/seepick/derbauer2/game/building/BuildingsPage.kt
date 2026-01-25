package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.HomePage
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.view.Back
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.Result
import com.github.seepick.derbauer2.game.view.ResultHandler
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Prompt
import com.github.seepick.derbauer2.textengine.SelectOption
import com.github.seepick.derbauer2.textengine.Textmap

class BuildingsPage(
    private val user: User,
    private val builder: Builder,
    private val currentPage: CurrentPage,
    private val gameRenderer: GameRenderer,
    private val resultHandler: ResultHandler,
) : Page {

    private val back = Back {
        currentPage.page = HomePage::class
    }
    private val prompt = Prompt.Select(
        title = "What shall we build next, Sire?",
        user.buildings.map { building ->
            SelectOption({ "Build ${building.labelSingular} - ${Gold.EMOJI} ${building.costsGold} | ${Land.Text.emoji} ${building.landUse} (owned: ${building.owned})" }) {
                resultHandler.handleBuilding(builder.build(building))
            }
        }
    )

    private fun ResultHandler.handleBuilding(result: BuildResult) {
        handle(
            when (result) {
                BuildResult.Success -> Result.Success
                BuildResult.InsufficientResources -> Result.Fail
            }
        )
    }

    override fun renderText(textmap: Textmap) {
        gameRenderer.render(textmap, prompt.inputIndicator, listOf(back.option)) {
            textmap.printLine("Your builders are ready for work.")
            textmap.printEmptyLine()
            prompt.render(textmap)
        }
    }

    override fun onKeyPressed(key: KeyPressed): Boolean =
        listOf(back, prompt).any { it.onKeyPressed(key) }
}
