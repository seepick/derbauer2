package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.ProbRegistrator
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.transaction.errorOnFail
import com.github.seepick.derbauer2.game.transaction.execTx
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.core.Koin

private val log = logger {}

fun Koin.initGame(initAssets: Boolean = true) {
    if (initAssets) {
        get<User>().initAssets()
    }
    get<ProbRegistrator>().registerAll()
}

fun User.initAssets() {
    log.info { "Initializing user assets." }
    val assets = createInitAssets()
    assets.forEach { add(it.first) }
    execTx(assets.map { (asset, amount) ->
        when (asset) {
            is Resource, is Building -> TxOwnable(asset::class, amount)

            else -> error("Unknown asset type: ${asset::class}")
        }
    }).errorOnFail()
}

/** Have to re-create instances; do NOT store in variable, as stored state will conflict with tests. */
private fun createInitAssets(): List<Pair<Asset, Zz>> = listOf(
    Pair(Gold(), Mechanics.startingGold.zz),
    Pair(Land(), Mechanics.startingLand.zz),
    Pair(House(), Mechanics.startingHouses.zz),
    Pair(Citizen(), Mechanics.startingCitizens.zz),
    Pair(Granary(), Mechanics.startingGranaries.zz),
    Pair(Farm(), Mechanics.startingFarms.zz),
    Pair(Food(), Mechanics.startingFood.zz)
)
