package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.building.buildingModule
import com.github.seepick.derbauer2.game.citizen.citizenModule
import com.github.seepick.derbauer2.game.feature.featureModule
import com.github.seepick.derbauer2.game.happening.happeningModule
import com.github.seepick.derbauer2.game.interaction.interactionModule
import com.github.seepick.derbauer2.game.logic.logicModule
import com.github.seepick.derbauer2.game.resource.resourceModule
import com.github.seepick.derbauer2.game.trading.tradingModule
import com.github.seepick.derbauer2.game.turn.turnModule
import com.github.seepick.derbauer2.game.view.viewModule
import org.koin.dsl.module

fun gameModule() = module {
    includes(
        resourceModule(),
        buildingModule(),
        citizenModule(),
        tradingModule(),
        happeningModule(),
        interactionModule(),
        turnModule(),
        featureModule(),
        logicModule(),
        viewModule(),
    )
}
