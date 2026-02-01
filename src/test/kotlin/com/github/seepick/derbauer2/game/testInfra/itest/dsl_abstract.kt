package com.github.seepick.derbauer2.game.testInfra.itest

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.building.BuildingsPage
import com.github.seepick.derbauer2.game.building.building
import com.github.seepick.derbauer2.game.trading.TradingPage
import com.github.seepick.derbauer2.game.turn.ReportPage
import com.github.seepick.derbauer2.game.view.GameOverPage
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.reflect.KClass

@DslMarker
annotation class TestEngineDsl

@TestEngineDsl
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
        page.shouldBeInstanceOf<BuildingsPage>()
        WhenBuildPageDsl(this).code()
    }
}

@TestEngineDsl
class WhenReportPageDsl(whenDsl: WhenDsl) : WhenDsl by whenDsl {
    fun nextPage() {
        input(KeyInput.Enter)
    }
}

@TestEngineDsl
class WhenBuildPageDsl(whenDsl: WhenDsl) : WhenDsl by whenDsl {
    fun build(buildingClass: KClass<out Building>) {
        val building = user.building(buildingClass)
        selectPrompt("build ${building.labelSingular}")
    }

    fun back() {
        input(KeyInput.Enter)
    }
}
