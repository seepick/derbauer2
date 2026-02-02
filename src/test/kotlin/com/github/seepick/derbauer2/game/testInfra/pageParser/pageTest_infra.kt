package com.github.seepick.derbauer2.game.testInfra.pageParser

import com.github.seepick.derbauer2.game.building.BuildingPage
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.Turner
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.InteractionResultHandler
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Textmap
import com.github.seepick.derbauer2.textengine.compose.MainWin
import io.mockk.mockk

fun renderGamePage(buildPage: (SetupGamePageContext) -> Page, pageTestCode: GamePageParser.() -> Unit) {
    val ctx = SetupGamePageContext()
    val page = buildPage(ctx)
    page.render(ctx.textmap)
    val parser = GamePageParser(ctx.textmap.toFullString())
    parser.pageTestCode()
}

class SetupGamePageContext {
    val user = User()
    val currentPage = CurrentPage(BuildingPage::class)
    val turner = mockk<Turner> {
        // collectAndExecuteNextTurnReport()
        // isGameOver
    }
    val gameRenderer = GameRenderer(user)
    val resultHandler = mockk<InteractionResultHandler>()
    val textmap = Textmap(MainWin.matrixSize)
}
