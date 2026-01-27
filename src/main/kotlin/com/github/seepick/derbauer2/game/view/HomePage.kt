package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.building.BuildingsPage
import com.github.seepick.derbauer2.game.trading.TradingPage
import com.github.seepick.derbauer2.game.turn.ReportPage
import com.github.seepick.derbauer2.game.turn.Turner
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.Prompt
import com.github.seepick.derbauer2.textengine.SelectOption

class HomePage(
    turner: Turner,
    currentPage: CurrentPage,
    gameRenderer: GameRenderer,
) : PromptGamePage(
    buttons = listOf(ContinueButton("Next Turn") {
        turner.collectAndExecuteNextTurnReport()
        currentPage.pageClass = ReportPage::class
    }),
    gameRenderer = gameRenderer,
    promptBuilder = {
        Prompt.Select(
            title = "What shall we do next?", listOf(
                SelectOption("Trade") {
                    currentPage.pageClass = TradingPage::class
                },
                SelectOption("Build") {
                    currentPage.pageClass = BuildingsPage::class
                },
            )
        )
    },
    contentRenderer = { textmap ->
        textmap.line("You are home... 🏠")
    }
)