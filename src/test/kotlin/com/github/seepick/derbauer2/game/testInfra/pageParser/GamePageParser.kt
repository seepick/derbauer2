package com.github.seepick.derbauer2.game.testInfra.pageParser

import com.github.seepick.derbauer2.textengine.compose.MainWin
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual

class GamePageParser(val fullPage: String) {

    private val hrSymbol = "="

    val lines = fullPage.split("\n").map { it.trimEnd() }
    /** e.g. "🌍 10 | 💰 500 | 🍖 50 / 100 | 🌍 3 / 10 | 🙎🏻‍♂️ 4 / 5                  Turn 1" */
    val lineInfo = lines.first()
    val linePrompt = lines.last()

    val contentLines = lines.subList(2, lines.size - 2).dropWhile { it.isBlank() }.dropLastWhile { it.isBlank() }
    val contentString = contentLines.joinToString("\n")

    private val widthHack = MainWin.matrixSize.cols
    val promptLeft: String? = linePrompt.take(widthHack / 2).trim().ifEmpty { null }
    val promptRight: String? = linePrompt.takeLast(widthHack / 2).trim().ifEmpty { null }
    val infoLeft = lineInfo.take(widthHack - 10).trim()
    val infoRight = lineInfo.takeLast(10).trim()

    init {
        lines shouldHaveSize MainWin.matrixSize.rows
        lines[1] shouldBeEqual hrSymbol.repeat(MainWin.matrixSize.cols)
        lines[lines.size - 2] shouldBeEqual hrSymbol.repeat(MainWin.matrixSize.cols)
    }

    companion object {
        operator fun invoke(pageString: String, code: GamePageParser.() -> Unit) {
            GamePageParser(pageString).apply(code)
        }
    }
}
