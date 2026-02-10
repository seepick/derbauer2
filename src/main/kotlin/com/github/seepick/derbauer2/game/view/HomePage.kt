package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.building.BuildingPage
import com.github.seepick.derbauer2.game.building.`build 🛠️`
import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.isGameOver
import com.github.seepick.derbauer2.game.feature.hasFeature
import com.github.seepick.derbauer2.game.tech.TechPage
import com.github.seepick.derbauer2.game.tech.TechnologyFeature
import com.github.seepick.derbauer2.game.tech.`tech 🔬`
import com.github.seepick.derbauer2.game.trade.TradeFeature
import com.github.seepick.derbauer2.game.trade.TradePage
import com.github.seepick.derbauer2.game.trade.`trade 💸`
import com.github.seepick.derbauer2.game.turn.ReportPage
import com.github.seepick.derbauer2.game.turn.Reports
import com.github.seepick.derbauer2.game.turn.Turner
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.prompt.Options
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import com.github.seepick.derbauer2.textengine.prompt.SelectPrompt

@Suppress("LongParameterList") // it's okay ;)
class HomePage(
    turner: Turner,
    currentPage: CurrentPage,
    gameRenderer: GameRenderer,
    user: User,
    reports: Reports,
) : PromptGamePage(
    buttons = listOf(
        ContinueButton("Next Turn") {
            val report = turner.execTurnAndBuildReport()
            reports.add(report)
            currentPage.pageClass = if (user.isGameOver()) GameOverPage::class else ReportPage::class
        }
    ),
    gameRenderer = gameRenderer,
    promptBuilder = {
        SelectPrompt(
            options = Options.Singled(buildList {
                if (user.hasFeature<TradeFeature>()) {
                    add(SelectOption("Trade ${Emoji.`trade 💸`}") {
                        currentPage.pageClass = TradePage::class
                    })
                }
                add(SelectOption("Build ${Emoji.`build 🛠️`}") {
                    currentPage.pageClass = BuildingPage::class
                })
                if (user.hasFeature<TechnologyFeature>()) {
                    add(SelectOption("Research ${Emoji.`tech 🔬`}") {
                        currentPage.pageClass = TechPage::class
                    })
                }
            })
        )
    }, contentRenderer = { textmap ->
        textmap.line("You are home... 🏠")
        textmap.line("What shall we do next, ${user.userTitle.label}?")
    })
