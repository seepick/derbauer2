package com.github.seepick.derbauer2.game.trading

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun gameTradingModule() = module {
    singleOf(::Trader)
    singleOf(::TradingPage)
}
