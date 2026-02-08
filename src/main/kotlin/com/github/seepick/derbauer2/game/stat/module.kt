package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.getKoinBeansByType
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun statModule() = module {
    singleOf(::StatCompositeGlobalTurnStep)
    singleOf(::HappinessCitizenModifier)
    singleOf(::HappinessSeasonModifier)
    single { GlobalStatModifierRepoImpl(modifiers = getKoinBeansByType()) } bind GlobalStatModifierRepo::class
}
