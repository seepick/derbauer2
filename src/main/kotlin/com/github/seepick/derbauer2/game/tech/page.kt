package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.view.BackButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.game.view.InteractionResultHandler
import com.github.seepick.derbauer2.game.view.PromptGamePage
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import com.github.seepick.derbauer2.textengine.prompt.SelectPrompt

class TechPage(
    private val user: User,
    private val currentPage: CurrentPage,
    gameRenderer: GameRenderer,
    private val techTree: TechTree,
    private val resultHandler: InteractionResultHandler,
) : PromptGamePage(
    gameRenderer = gameRenderer,
    promptBuilder = {

        SelectPrompt(
            title = "What do you want to research?",
            options = techTree.getAvailableToBeResearched().map { techTreeItem ->
                SelectOption({
                    "Research ${techTreeItem.label} - "
                }) {
//                    techTree.research(techTreeItem)
//                    resultHandler.handle(user.research(techTreeItem))
                }
            }
        )
    },
    buttons = listOf(BackButton {
        currentPage.pageClass = HomePage::class
    }),
    contentRenderer = { textmap ->
        textmap.line("Be smart - be a nerd.")
    }
)
