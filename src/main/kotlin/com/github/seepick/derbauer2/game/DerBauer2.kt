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

    @JvmStatic
    fun main(args: Array<String>) {
        log.info { "Starting Der Bauer 2" }
        showMainWindow(
            title = "Der Bauer 2",
            mainModule = gameModule(),
            initPage = HomePage::class,
        ) { game ->
            game.user.initAssets()
        }
    }

    private fun User.initAssets() {
        listOf(
            Land(Mechanics.startingLand),
            Gold(Mechanics.startingGold),
            Food(Mechanics.startingFood),
            House(Mechanics.startingHouses),
            Citizen(Mechanics.startingCitizens),
            Farm(Mechanics.startingFarms),
            Granary(Mechanics.startingGranaries),
        ).forEach(::addEntity)
    }
}
