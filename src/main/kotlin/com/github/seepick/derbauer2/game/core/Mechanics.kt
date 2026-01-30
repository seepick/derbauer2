package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.`%`
import com.github.seepick.derbauer2.game.common.z

/**
 * All game mechanics constants; a magic constant provider.
 */
@Suppress("MayBeConstant", "MagicNumber")
object Mechanics {

    // INITIAL VALUES
    // ========================================================================
    // resources
    val startingLand = 10.z
    val startingGold = 500.z
    val startingCitizens = 4.z
    val startingFood = 50.z
    // buildings
    val startingGranaries = 1.z
    val startingHouses = 1.z
    val startingFarms = 0.z

    // BUILDINGS
    // ========================================================================
    val houseCostsGold = 40
    val houseLandUse = 1
    val houseStoreCitizen = 5

    val farmCostsGold = 120
    val farmProduceFood = 3
    val farmLandUse = 4

    val granaryCostsGold = 80
    val granaryCapacity = 100
    val granaryLanduse = 2

    // TRADE
    // ========================================================================
    val buyFoodCostGold = 5
    val sellFoodGainGold = 3
    val buyLandCostGold = 50

    // TECHNOLOGY
    // ========================================================================
    val technologyFeatureUnlockWithGold = 1.z //startingGold * 2

    // END TURN
    // ========================================================================
    val citizenTax = 0.5.`%` // multiplied by citizens.amount
    val citizenFoodConsume = 0.3.`%`
    val citizensStarve = 0.01.`%`
    val citizensStarveMinimum = 1.z

    val turnProbHappeningIsNegative = 0.1
}
