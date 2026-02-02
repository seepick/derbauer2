package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.view.BackButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.game.view.InteractionResultHandler
import com.github.seepick.derbauer2.game.view.PromptGamePage
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.prompt.EmptyPagePromptProvider
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import com.github.seepick.derbauer2.textengine.prompt.SelectPrompt

class BuildingPage(
    private val user: User,
    private val currentPage: CurrentPage,
    gameRenderer: GameRenderer,
    private val resultHandler: InteractionResultHandler,
) : PromptGamePage(
    gameRenderer = gameRenderer,
    promptBuilder = {
        if (user.buildings.isEmpty()) {
            EmptyPagePromptProvider("Not a single thing to build, pah ☹️")
        } else {
            SelectPrompt(
                title = "What shall we build next, Sire?",
                options = user.buildings.map { building ->
                    SelectOption({
                        "Build ${building.labelSingular} - " +
                                "${Gold.Data.emojiSpaceOrEmpty}${building.costsGold} | " +
                                "${Land.Data.emojiSpaceOrEmpty}${building.landUse} " +
                                "(owned: ${building.owned})"
                    }) {
                        resultHandler.handle(user.buildBuilding(building::class))
                    }
                }
            )
        }
    },
    buttons = listOf(BackButton {
        currentPage.pageClass = HomePage::class
    }),
    contentRenderer = { textmap ->
        textmap.line("Your builders are ready for work.")
    }
)
