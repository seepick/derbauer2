package com.github.seepick.derbauer2.game.testInfra.pageParser

import com.github.seepick.derbauer2.game.building.BuildingPage
import com.github.seepick.derbauer2.game.building.BuildingService
import com.github.seepick.derbauer2.game.core.ActionBusImpl
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.initCore
import com.github.seepick.derbauer2.game.turn.ReportsImpl
import com.github.seepick.derbauer2.game.turn.Turner
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.TxResultHandler
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.compose.MainWin
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import io.mockk.mockk

fun renderGamePage(buildPage: (SetupGamePageContext) -> Page, pageTestCode: GamePageParser.() -> Unit) {
    val ctx = SetupGamePageContext()
    ctx.user.initCore()
    val page = buildPage(ctx)
    page.render(ctx.textmap)
    val parser = GamePageParser(ctx.textmap.toFullString())
    parser.pageTestCode()
}

class SetupGamePageContext {
    val user = User()
    val currentPage = CurrentPage(BuildingPage::class)
    val turner = mockk<Turner> {}
    val reports = ReportsImpl()
    val gameRenderer = GameRenderer(user)
    val resultHandler = mockk<TxResultHandler>()
    val textmap = Textmap(MainWin.matrixSize)
    val actionBus = ActionBusImpl(emptyList())
    val buildingService = BuildingService(user, actionBus)
}
