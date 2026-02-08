package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.common.getKoinBeansByType
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun turnModule() = module {
    single {
        Turner(
            globalSteps = getKoinBeansByType(),
            resSteps = getKoinBeansByType(),
            user = get(),
            happeningTurner = get(),
            featureTurner = get(),
            actionsCollector = get(),
            turn = get(),
        )
    }
    singleOf(::CurrentTurn)
    singleOf(::ProduceCitzenCompositeResourceStep)
    singleOf(::ProduceCitzenCompositeResourceStep)
    singleOf(::ReportsImpl) bind Reports::class
}
