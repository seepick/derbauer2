package com.github.seepick.derbauer2.game.feature

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun featureModule() = module {
    singleOf(::FeatureTurner)
    singleOf(::FeatureMultiView)
    singleOf(::FeatureViewPage)
    single { DefaultFeatureRefRegistry } bind FeatureRefRegistry::class
}
