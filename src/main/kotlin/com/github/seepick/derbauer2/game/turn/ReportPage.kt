package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.HomePage
import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.MetaOption
import com.github.seepick.derbauer2.ifDo
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Textmap

class ReportPage(
    private val game: Game,
    private val gameRenderer: GameRenderer,
    private val currentPage: CurrentPage,
) : Page {

    private val continueKey = KeyPressed.Command.Space
    override fun renderText(textmap: Textmap) {
        gameRenderer.render(textmap, promptIndicator = continueKey.key, listOf(MetaOption(continueKey, "Continue"))) {
            textmap.printLine("Turn Report")
            textmap.printEmptyLine()
            textmap.printLine("Resources produced this turn:")
            textmap.printEmptyLine()
            val report = game.reports.last()
            report.resourceProduction.forEach {
                textmap.printLine("${it.first.emojiWithSpaceSuffixOrEmpty}${it.first.labelPlural}: ${it.second}")
            }
        }
    }

    override fun onKeyPressed(key: KeyPressed) =
        ifDo(key == continueKey) {
            currentPage.page = HomePage::class
        }
}