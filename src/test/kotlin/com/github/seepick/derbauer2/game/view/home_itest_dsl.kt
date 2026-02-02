package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.building.BuildingPage
import com.github.seepick.derbauer2.game.building.WhenBuildPageDsl
import com.github.seepick.derbauer2.game.testInfra.dsl.TestDsl
import com.github.seepick.derbauer2.game.testInfra.dsl.WhenDsl
import com.github.seepick.derbauer2.game.trading.TradingPage
import com.github.seepick.derbauer2.game.turn.ReportPage
import com.github.seepick.derbauer2.game.turn.WhenReportPageDsl
import com.github.seepick.derbauer2.textengine.KeyInput
import io.kotest.matchers.types.shouldBeInstanceOf

@TestDsl
interface HomePageDsl {
    fun nextTurnToReport(code: WhenReportPageDsl.() -> Unit = {})
    fun selectTrade()
    fun selectBuild(code: WhenBuildPageDsl.() -> Unit)
}

class WhenHomePageDsl(private val whenDsl: WhenDsl) : WhenDsl by whenDsl, HomePageDsl {
    override fun nextTurnToReport(code: WhenReportPageDsl.() -> Unit) {
        input(KeyInput.Enter)
        page.shouldBeInstanceOf<ReportPage>()
        WhenReportPageDsl(whenDsl).code()
    }

    fun nextTurnToGameOver() {
        input(KeyInput.Enter)
        page.shouldBeInstanceOf<GameOverPage>()
    }

    override fun selectTrade() {
        selectPrompt("trade")
        page.shouldBeInstanceOf<TradingPage>()
    }

    override fun selectBuild(code: WhenBuildPageDsl.() -> Unit) {
        selectPrompt("build")
        page.shouldBeInstanceOf<BuildingPage>()
        WhenBuildPageDsl(this).code()
    }
}

