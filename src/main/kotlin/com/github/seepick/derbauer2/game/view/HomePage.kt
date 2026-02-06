package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.building.BuildingPage
import com.github.seepick.derbauer2.game.building.`building 🛠️`
import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.isGameOver
import com.github.seepick.derbauer2.game.feature.hasFeature
import com.github.seepick.derbauer2.game.resource.`gold 💰`
import com.github.seepick.derbauer2.game.tech.TechPage
import com.github.seepick.derbauer2.game.tech.TechnologyFeature
import com.github.seepick.derbauer2.game.tech.`research 🔬`
import com.github.seepick.derbauer2.game.trading.TradingFeature
import com.github.seepick.derbauer2.game.trading.TradingPage
import com.github.seepick.derbauer2.game.turn.ReportPage
import com.github.seepick.derbauer2.game.turn.Turner
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.prompt.Options
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import com.github.seepick.derbauer2.textengine.prompt.SelectPrompt

class HomePage(
    turner: Turner,
    currentPage: CurrentPage,
    gameRenderer: GameRenderer,
    user: User,
) : PromptGamePage(
    buttons = listOf(
        ContinueButton("Next Turn") {
            val report = turner.executeAndGenerateReport()
            user.nextTurn(report)
            currentPage.pageClass = if (user.isGameOver()) GameOverPage::class else ReportPage::class
        }
    ),
    gameRenderer = gameRenderer,
    promptBuilder = {
        SelectPrompt(
            options = Options.Singled(buildList {
                if (user.hasFeature<TradingFeature>()) {
                    add(SelectOption("Trade ${Emoji.`gold 💰`}") {
                        currentPage.pageClass = TradingPage::class
                    })
                }
                add(SelectOption("Build ${Emoji.`building 🛠️`}") {
                    currentPage.pageClass = BuildingPage::class
                })
                if (user.hasFeature<TechnologyFeature>()) {
                    add(SelectOption("Research ${Emoji.`research 🔬`}") {
                        currentPage.pageClass = TechPage::class
                    })
                }
            })
        )
    }, contentRenderer = { textmap ->
        textmap.line("You are home... 🏠")
        textmap.line("What shall we do next, ${user.userTitle.label}?")
    })
