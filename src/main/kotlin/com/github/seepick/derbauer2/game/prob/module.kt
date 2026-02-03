package com.github.seepick.derbauer2.game.prob

import com.github.seepick.derbauer2.game.common.getKoinBeansByType
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun probModule() = module {
    singleOf(::ProbsImpl) bind Probs::class
    single { ProbRegistrator(registrants = getKoinBeansByType<ProbInitializer>()) }
    single { RandomListShuffler } bind ListShuffler::class
}
