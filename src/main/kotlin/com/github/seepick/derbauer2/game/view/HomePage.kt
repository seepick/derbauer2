package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.building.BuildingsPage
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.hasFeature
import com.github.seepick.derbauer2.game.technology.TechnologyFeature
import com.github.seepick.derbauer2.game.technology.TechnologyPage
import com.github.seepick.derbauer2.game.trading.TradingPage
import com.github.seepick.derbauer2.game.turn.ReportPage
import com.github.seepick.derbauer2.game.turn.Turner
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import com.github.seepick.derbauer2.textengine.prompt.SelectPrompt

class HomePage(
    turner: Turner,
    currentPage: CurrentPage,
    gameRenderer: GameRenderer,
    user: User,
) : PromptGamePage(buttons = listOf(ContinueButton("Next Turn") {
    turner.collectAndExecuteNextTurnReport()
    currentPage.pageClass = ReportPage::class
}), gameRenderer = gameRenderer, promptBuilder = {
    SelectPrompt(
        title = "What shall we do next, ${user.designator.label}?", buildList {
            add(SelectOption("Trade 💰") {
                currentPage.pageClass = TradingPage::class
            })
            add(SelectOption("Build 🛠️") {
                currentPage.pageClass = BuildingsPage::class
            })
            if (user.hasFeature(TechnologyFeature::class)) {
                add(SelectOption("Research 🔬") {
                    currentPage.pageClass = TechnologyPage::class
                })
            }
        }
    )
}, contentRenderer = { textmap ->
    textmap.line("You are home... 🏠")
})
