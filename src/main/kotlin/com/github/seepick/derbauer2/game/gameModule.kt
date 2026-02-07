package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.building.buildingModule
import com.github.seepick.derbauer2.game.citizen.citizenModule
import com.github.seepick.derbauer2.game.core.coreModule
import com.github.seepick.derbauer2.game.feature.featureModule
import com.github.seepick.derbauer2.game.happening.happeningModule
import com.github.seepick.derbauer2.game.prob.probModule
import com.github.seepick.derbauer2.game.resource.resourceModule
import com.github.seepick.derbauer2.game.stat.statModule
import com.github.seepick.derbauer2.game.tech.techModule
import com.github.seepick.derbauer2.game.trading.tradingModule
import com.github.seepick.derbauer2.game.transaction.transactionModule
import com.github.seepick.derbauer2.game.turn.turnModule
import com.github.seepick.derbauer2.game.view.viewModule
import org.koin.dsl.module
import kotlin.reflect.KClass

fun gameModule(prefStatePath: KClass<*>) = module {
    includes(
        probModule(),
        coreModule(prefStatePath),
        resourceModule(),
        buildingModule(),
        citizenModule(),
        transactionModule(),
        tradingModule(),
        turnModule(),
        happeningModule(),
        featureModule(),
        statModule(),
        techModule(),
        viewModule(),
    )
}
