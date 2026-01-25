package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.logic.Food
import com.github.seepick.derbauer2.game.logic.Gold
import com.github.seepick.derbauer2.game.logic.Mechanics
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.view.HomePage
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
        resources += Gold(Mechanics.startingGold)
        resources += Food(Mechanics.startingFood)
        buildings += House(Mechanics.startingHouses)
        buildings += Farm(Mechanics.startingFarms)
        buildings += Granary(Mechanics.startingGranaries)
    }

}
