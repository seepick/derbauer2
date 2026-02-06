package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.toTextmapRendering
import com.github.seepick.derbauer2.game.view.BackButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.game.view.InteractionResultHandler
import com.github.seepick.derbauer2.game.view.PromptGamePage
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.prompt.EmptyPagePromptProvider
import com.github.seepick.derbauer2.textengine.prompt.Options
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import com.github.seepick.derbauer2.textengine.prompt.SelectOptionLabel
import com.github.seepick.derbauer2.textengine.prompt.SelectPrompt

@Suppress("LongParameterList") // it's ok ;)
class TechPage(
    private val user: User,
    private val currentPage: CurrentPage,
    gameRenderer: GameRenderer,
    private val techTree: TechTree,
    private val resultHandler: InteractionResultHandler,
) : PromptGamePage(
    gameRenderer = gameRenderer,
    promptBuilder = {
        val items = techTree.filterResearchableItems()
        if (items.isEmpty()) {
            EmptyPagePromptProvider("Your mind is empty...\nGo ahead and read some books first.")
        } else {
            SelectPrompt(
                title = "What do you want to research?",
                options = Options.Singled(items.map { item ->
                    // TODO simple-flexible-dynamic table: no columns; just rows of different column size :)
                    SelectOption(
                        label = SelectOptionLabel.Single.Dynamic {
                            with(user) {
                                "Research ${item.label} - ${item.costs.toTextmapRendering()}"
                            }
                        },
                        onSelected = {
                            resultHandler.handle(user.researchTech(item))
                        })
                })
            )
        }
    },
    buttons = listOf(BackButton {
        currentPage.pageClass = HomePage::class
    }),
    contentRenderer = { textmap ->
        textmap.line("Be smart - be a nerd.")
    }
)
