package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.logic.Mechanics
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
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
    log.info { "Initializing user assert" }
    listOf(
        Land(Mechanics.startingLand),
        Gold(Mechanics.startingGold),
        House(Mechanics.startingHouses),
        Citizen(Mechanics.startingCitizens),
        Granary(Mechanics.startingGranaries),
        Food(Mechanics.startingFood),
        Farm(Mechanics.startingFarms),
    ).forEach(::add)
}
