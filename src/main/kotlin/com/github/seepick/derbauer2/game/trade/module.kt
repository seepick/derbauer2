package com.github.seepick.derbauer2.game.trade

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun tradeModule() = module {
    singleOf(::TradePage)
    singleOf(::TradePromptBuilder)
    singleOf(::TradeService)
}
