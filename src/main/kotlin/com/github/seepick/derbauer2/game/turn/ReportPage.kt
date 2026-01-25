package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.HomePage
import com.github.seepick.derbauer2.game.happening.HappeningController
import com.github.seepick.derbauer2.game.happening.HappeningPage
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.MetaOption
import com.github.seepick.derbauer2.ifDo
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Textmap

class ReportPage(
    private val turner: Turner,
    private val gameRenderer: GameRenderer,
    private val currentPage: CurrentPage,
    private val happeningController: HappeningController,
) : Page {

    private val continueKey = KeyPressed.Command.Space
    override fun renderText(textmap: Textmap) {
        gameRenderer.render(textmap, promptIndicator = continueKey.label, listOf(MetaOption(continueKey, "Continue"))) {
            textmap.printLine("Turn Report")
            textmap.printEmptyLine()
            textmap.printLine("Resources produced this turn:")
            textmap.printEmptyLine()
            val report = turner.reports.last()
            report.resourceChanges.forEach {
                textmap.printLine("${it.resource.emojiWithSpaceSuffixOrEmpty}${it.resource.labelPlural}: ${it.change.toPlusString()}")
            }
        }
    }

    override fun onKeyPressed(key: KeyPressed) =
        ifDo(key == continueKey) {
            val happenings = turner.reports.last().happenings
            currentPage.page = if(happenings.isEmpty()) {
                HomePage::class
            } else {
                happeningController.process(happenings)
                HappeningPage::class
            }
        }
}