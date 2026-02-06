package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.toFormattedList
import com.github.seepick.derbauer2.game.view.BackButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.game.view.InteractionResultHandler
import com.github.seepick.derbauer2.game.view.PromptGamePage
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.prompt.EmptyPagePromptProvider
import com.github.seepick.derbauer2.textengine.prompt.OptionLabel
import com.github.seepick.derbauer2.textengine.prompt.Options
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
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
        val techs = techTree.filterResearchableItems()
        if (techs.isEmpty()) {
            EmptyPagePromptProvider("Your mind is empty...\nGo ahead and read some books first.")
        } else {
            SelectPrompt(
                title = "What do you want to research?",
                options = Options.Tabled(
                    items = techs.map { tech ->
                        SelectOption(
                            label = OptionLabel.Table(
                                buildList {
                                    add("Research ${tech.label}")
                                    with(user) {
                                        addAll(tech.costs.toFormattedList())
                                    }
                                }
                            ),
                            onSelected = { resultHandler.handle(user.researchTech(tech)) }
                        )
                    }
                )
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
