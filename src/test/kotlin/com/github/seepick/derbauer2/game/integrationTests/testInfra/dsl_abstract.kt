package com.github.seepick.derbauer2.game.integrationTests.testInfra

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.building.BuildingsPage
import com.github.seepick.derbauer2.game.building.building
import com.github.seepick.derbauer2.game.trading.TradingPage
import com.github.seepick.derbauer2.game.turn.ReportPage
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.reflect.KClass

@DslMarker
annotation class TestEngineDsl

@TestEngineDsl
interface HomePageDsl {
    fun nextTurn()
    fun selectTrade()
    fun selectBuild(code: WhenBuildPageDsl.() -> Unit)
}

class WhenHomePageDsl(whenDsl: WhenDsl) : WhenDsl by whenDsl, HomePageDsl {
    override fun nextTurn() { // TODO report page
        input(KeyInput.Enter)
        page.shouldBeInstanceOf<ReportPage>()
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
class WhenBuildPageDsl(whenDsl: WhenDsl) : WhenDsl by whenDsl {
    fun build(buildingClass: KClass<out Building>) {
        val building = user.building(buildingClass)
        selectPrompt("build ${building.labelSingular}")
    }

    fun back() {
        input(KeyInput.Enter)
    }
}