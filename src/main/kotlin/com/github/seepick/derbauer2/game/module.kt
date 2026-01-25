package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.building.BuildingsPage
import com.github.seepick.derbauer2.game.logic.gameLogicModule
import com.github.seepick.derbauer2.game.trading.TradingPage
import com.github.seepick.derbauer2.game.view.gameViewModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun gameModule() = module {
    includes(gameLogicModule(), gameViewModule())
    singleOf(::BuildingsPage)
    singleOf(::TradingPage)
}
