package com.github.seepick.derbauer2.game.testInfra

import com.github.seepick.derbauer2.game.building.BuildingsPage
import com.github.seepick.derbauer2.game.turn.Turner
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.InteractionResultHandler
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Textmap
import com.github.seepick.derbauer2.textengine.compose.MainWin
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk

fun renderGamePage(buildPage: (SetupGamePageContext) -> Page, validations: GamePageParser.() -> Unit) {
    val ctx = SetupGamePageContext()
    val page = buildPage(ctx)
    page.render(ctx.textmap)
    val parser = GamePageParser(ctx.textmap.toFullString())
    parser.validations()
}

class SetupGamePageContext {
    val user = User()
    val currentPage = CurrentPage(BuildingsPage::class)
    val turner = mockk<Turner> {
        every { turn } returns 1
    }
    val gameRenderer = GameRenderer(user, turner)
    val resultHandler = mockk<InteractionResultHandler>()
    val textmap = Textmap(MainWin.matrixSize)
}

class GamePageParser(val pageString: String) {
    val lines = pageString.split("\n").map { it.trim() }
    val contentLines = lines.subList(2, lines.size - 2).dropLastWhile { it.isBlank() }
    val contentLinesString = contentLines.joinToString("\n")
    private val hrSymbol = "="

    init {
        lines shouldHaveSize MainWin.matrixSize.rows
        lines[1] shouldBeEqual hrSymbol.repeat(MainWin.matrixSize.cols)
        lines[lines.size - 2] shouldBeEqual hrSymbol.repeat(MainWin.matrixSize.cols)
    }

    // some rough guess with size 10 ;)
    val promptLeft: String? = lines.last().take(30).trim().ifEmpty { null }
    val promptRight: String? = lines.last().takeLast(30).trim().ifEmpty { null }

    companion object {
        operator fun invoke(pageString: String, code: GamePageParser.() -> Unit) {
            GamePageParser(pageString).apply(code)
        }
    }
}
