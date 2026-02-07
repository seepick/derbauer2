package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.Texts
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Knowledge
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.resource.toFormattedList
import com.github.seepick.derbauer2.game.view.BackButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.game.view.PromptGamePage
import com.github.seepick.derbauer2.game.view.TxResultHandler
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.prompt.EmptyPagePromptProvider
import com.github.seepick.derbauer2.textengine.prompt.OptionLabel
import com.github.seepick.derbauer2.textengine.prompt.Options
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import com.github.seepick.derbauer2.textengine.prompt.SelectPrompt
import com.github.seepick.derbauer2.textengine.textmap.emptyLine

@Suppress("LongParameterList") // it's ok ;)
class TechPage(
    private val user: User,
    private val currentPage: CurrentPage,
    gameRenderer: GameRenderer,
    private val techTree: TechTree,
    private val resultHandler: TxResultHandler,
    private val techService: TechService,
) : PromptGamePage(gameRenderer = gameRenderer, promptBuilder = {
    val techs = techTree.filterResearchableItems()
    if (techs.isEmpty()) {
        EmptyPagePromptProvider(Texts.techPageEmpty)
    } else {
        val maxColCount = techs.maxOf { it.costs.size }
        SelectPrompt(Options.Tabled(techs.map {
            it.toSelectOption(user, maxColCount, techService, resultHandler)
        }))
    }
}, buttons = listOf(BackButton {
    currentPage.pageClass = HomePage::class
}), contentRenderer = { textmap ->
    textmap.line(Texts.techPage)
    textmap.emptyLine()
    textmap.line("Your genius wisdom expands ${user.findResource<Knowledge>().emojiAndOwned} units of knowledge.")
})

private fun TechRef.toSelectOption(
    user: User,
    maxColCount: Int,
    techService: TechService,
    resultHandler: TxResultHandler,
): SelectOption<OptionLabel.Table> = SelectOption(
    label = OptionLabel.Table(
        buildList {
            add("Research $label")
            with(user) {
                addAll(costs.toFormattedList())
            }
            addAll(List(maxColCount - costs.size) { "" })
            add("... $description")
        }), onSelected = {
        resultHandler.handle(techService.researchTech(this))
    })
