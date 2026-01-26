package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.HomePage
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.view.BackButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.InteractionResultHandler
import com.github.seepick.derbauer2.game.view.PromptGamePage
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.Prompt
import com.github.seepick.derbauer2.textengine.SelectOption

class BuildingsPage(
    private val user: User,
    private val currentPage: CurrentPage,
    private val gameRenderer: GameRenderer,
    private val resultHandler: InteractionResultHandler,
) : PromptGamePage(
    gameRenderer = gameRenderer,
    promptBuilder = {
        Prompt.Select(
            title = "What shall we build next, Sire?",
            user.buildings.map { building ->
                SelectOption({ "Build ${building.labelSingular} - ${Gold.Text.emoji} ${building.costsGold} | ${Land.Text.emoji} ${building.landUse} (owned: ${building.owned})" }) {
                    resultHandler.handle(user.build(building))
                }
            }
        )
    },
    buttons = listOf(BackButton {
        currentPage.pageClass = HomePage::class
    }),
    contentRenderer = { textmap ->
        textmap.line("Your builders are ready for work.")
    }
)
