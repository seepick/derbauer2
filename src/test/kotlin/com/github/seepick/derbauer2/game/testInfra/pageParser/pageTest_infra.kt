package com.github.seepick.derbauer2.game.testInfra.pageParser

import com.github.seepick.derbauer2.game.building.BuildingPage
import com.github.seepick.derbauer2.game.building.BuildingService
import com.github.seepick.derbauer2.game.core.ActionBusImpl
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.CurrentTurnImpl
import com.github.seepick.derbauer2.game.turn.ReportsImpl
import com.github.seepick.derbauer2.game.turn.Turner
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.TxResultHandler
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.compose.MainWin
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import io.mockk.mockk

fun renderGamePage(buildPage: (GamePageContext) -> Page, pageTestCode: GamePageParser.(GamePageContext) -> Unit) {
    val ctx = GamePageContext()
    val page = buildPage(ctx)
    page.render(ctx.textmap)
    val parser = GamePageParser(ctx.textmap.toFullString())
    parser.pageTestCode(ctx)
}

class GamePageContext {
    val user = User()
    val currentPage = CurrentPage(BuildingPage::class)
    val turner = mockk<Turner> {}
    val reports = ReportsImpl()
    val currentTurn = CurrentTurnImpl()
    val gameRenderer = GameRenderer(user, currentTurn)
    val resultHandler = mockk<TxResultHandler>()
    val textmap = Textmap(MainWin.matrixSize)
    val actionBus = ActionBusImpl(emptyList())
    val buildingService = BuildingService(user, actionBus)
}
