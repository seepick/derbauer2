package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.getKoinBeansByType
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun statModule() = module {
    single { StatTurnStep(user = get(), staticModifiers = getKoinBeansByType()) }
    singleOf(::HappinessCitizenModifier)
    singleOf(::HappinessSeasonModifier)
}
