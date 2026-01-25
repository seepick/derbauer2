package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.ifDo
import com.github.seepick.derbauer2.viewer.CurrentPage
import com.github.seepick.derbauer2.viewer.KeyPressed
import com.github.seepick.derbauer2.viewer.Page
import com.github.seepick.derbauer2.viewer.Textmap

class ReportPage(
    private val game: Game,
    private val gameRenderer: GameRenderer,
    private val currentPage: CurrentPage,
) : Page {

    private val continueKey = KeyPressed.Command.Space
    override fun renderText(textmap: Textmap) {
        gameRenderer.render(textmap, promptIndicator = "?", listOf(MetaOption(continueKey, "Continue"))) {
            textmap.printLine("Turn Report")
            textmap.printEmptyLine()
            textmap.printLine("Resources produced this turn:")
            val report = game.reports.last()
            report.resourceProduction.forEach {
                textmap.printLine("* ${it.first.emojiWithSpaceSuffixOrEmpty}${it.first.labelPlural}: ${it.second.formatted}")
            }
        }
    }

    override fun onKeyPressed(key: KeyPressed) =
        ifDo(key == continueKey) {
            currentPage.page = HomePage::class
        }
}