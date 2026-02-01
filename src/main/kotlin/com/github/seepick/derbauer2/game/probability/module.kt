package com.github.seepick.derbauer2.game.probability

import com.github.seepick.derbauer2.game.common.getKoinBeansByType
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun probabilityModule() = module {
    singleOf(::ProbabilitiesImpl) bind Probabilities::class
    single { ProbabilityInitializer(registrants = getKoinBeansByType<ProbabilityRegistrant>()) }
    single { RandomListShuffler } bind ListShuffler::class
}
