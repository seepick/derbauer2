package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.common.getKoinBeansByType
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun turnModule() = module {
    single {
        Turner(
            get(), get(), get(),
            get(), get(), get(), getKoinBeansByType()
        )
    }
    singleOf(::ReportIntelligence)
}
