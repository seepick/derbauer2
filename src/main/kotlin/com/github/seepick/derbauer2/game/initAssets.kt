package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.building.TxBuilding
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.TxResource
import com.github.seepick.derbauer2.game.transaction.errorOnFail
import com.github.seepick.derbauer2.game.transaction.execTx
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val log = logger {}

private fun createInitAssets(): List<Pair<Asset, Zz>> = listOf(
    Pair(Gold(), Mechanics.startingGold.asZz),
    Pair(Land(), Mechanics.startingLand.asZz),
    Pair(House(), Mechanics.startingHouses.asZz),
    Pair(Citizen(), Mechanics.startingCitizens.asZz),
    Pair(Granary(), Mechanics.startingGranaries.asZz),
    Pair(Farm(), Mechanics.startingFarms.asZz),
    Pair(Food(), Mechanics.startingFood.asZz)
)

fun User.initAssets() {
    log.info { "Initializing user assets." }
    val assets = createInitAssets()
    assets.forEach { enable(it.first) }
    execTx(assets.map { (asset, amount) ->
        when (asset) {
            is Resource -> TxResource(asset::class, amount)
            is Building -> TxBuilding(asset::class, amount)
            else -> error("Unknown asset type: ${asset::class}") // TODO use enum-indirection for exhaustive when
        }
    }).errorOnFail()
}
