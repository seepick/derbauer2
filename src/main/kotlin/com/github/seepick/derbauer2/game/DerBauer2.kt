package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.building.TxBuilding
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.TxResource
import com.github.seepick.derbauer2.game.transaction.errorOnFail
import com.github.seepick.derbauer2.game.transaction.execTx
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.textengine.showMainWindow
import io.github.oshai.kotlinlogging.KotlinLogging.logger

object DerBauer2 {
    private val log = logger {}
    val initPageClass = HomePage::class

    @JvmStatic
    fun main(args: Array<String>) {
        log.info { "Starting Der Bauer 2" }
        showMainWindow(
            title = "DerBauer2",
            mainModule = gameModule(),
            initPageClass = initPageClass,
        ) { user ->
            user.initAssets()
        }
    }
}

private val log = logger {}

fun User.initAssets() {
    log.info { "Initializing user assets." }
    val house = House()
    val granary = Granary()
    val farm = Farm()
    val gold = Gold()
    val land = Land()
    val citizen = Citizen()
    val food = Food()
    listOf(gold, land, house, citizen, granary, food, farm).forEach(::enable)
    execTx(
        TxResource(gold::class, Mechanics.startingGold.asZz),
        TxResource(land::class, Mechanics.startingLand.asZz),
        TxBuilding(House::class, Mechanics.startingHouses.asZz),
        TxBuilding(Granary::class, Mechanics.startingGranaries.asZz),
        TxBuilding(Farm::class, Mechanics.startingFarms.asZz),
        TxResource(citizen::class, Mechanics.startingCitizens.asZz),
        TxResource(food::class, Mechanics.startingFood.asZz),
    ).errorOnFail()
}