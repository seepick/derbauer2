package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.common.getKoinBeansByType
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun statModule() = module {
    singleOf(::StatCompositeGlobalTurnStep)

    singleOf(::HappinessCitizenPreModifier)
    singleOf(::HappinessSeasonPreModifier)
    singleOf(::HappinessDeathPenaltyPostModifier)

    single { GlobalPreStatModifierRepoImpl(modifiers = getKoinBeansByType()) } bind GlobalPreStatModifierRepo::class
    single { GlobalPostStatModifierRepoImpl(modifiers = getKoinBeansByType()) } bind GlobalPostStatModifierRepo::class
}
