package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.building.buildingModule
import com.github.seepick.derbauer2.game.logic.logicModule
import com.github.seepick.derbauer2.game.trading.tradingModule
import com.github.seepick.derbauer2.game.view.viewModule
import org.koin.dsl.module

fun gameModule() = module {
    includes(
        logicModule(),
        tradingModule(),
        viewModule(),
        buildingModule(),
    )
}
