package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.DerBauer2
import com.github.seepick.derbauer2.game.common.`%`
import com.github.seepick.derbauer2.game.common.z

/**
 * All game mechanics constants; a magic constant provider.
 */
@Suppress("MayBeConstant", "MagicNumber", "VariableMaxLength")
object Mechanics {

    private val DEV = DerBauer2.isDevMode

    // INITIAL VALUES
    // ========================================================================
    // resources
    val startingLand = 15.z
    val startingGold = 500.z
    val startingCitizens = 4.z
    val startingFood = 50.z
    // buildings
    val startingGranaries = 1.z
    val startingHouses = 1.z
    val startingFarms = 0.z

    // BUILDINGS
    // ========================================================================
    val houseCostsGold = 40.z
    val houseLandUse = 1.z
    val houseStoreCitizen = 5

    val farmCostsGold = 120.z
    val farmProduceFood = 3.z
    val farmLandUse = 4.z

    val granaryCostsGold = 80
    val granaryCapacity = 100
    val granaryLanduse = 2

    // TRADE
    // ========================================================================
    val buyFoodCostGold = 5
    val sellFoodGainGold = 3
    val buyLandCostGold = 50

    // FEATURE
    // ========================================================================
    val featureTradingThresholdGoldLesser = if (DEV) 400.z else 100.z
    val featureTradeLandThresholdLandAvailableLesser = 2.z
    val featureTechGoldThresholdGreater = if (DEV) 0.z else (startingGold.value * 1.5).toLong().z

    // TECHNOLOGY
    // ========================================================================
    val techAgricultureCostsGold = if (DEV) 10.z else 300.z
    val techCapitalismCostsGold = if (DEV) 20.z else 800.z
    val techAgricultureFoodProductionMultiplier = if (DEV) 1.9.`%` else 1.1.`%`

    // END TURN
    // ========================================================================
    val citizenTax = 0.5.`%` // multiplied by citizens.amount
    val citizenFoodConsume = 0.3.`%`
    val citizensStarve = 0.01.`%`
    val citizensStarveMinimum = 1.z
    val citizenReproductionRate = 0.1.`%`
    val citizenReproductionMinimum = 1.z

    val happeningInitialProb = 0.0.`%`
    val happeningGrowthRate = 0.02.`%`
    val happeningIsNegativeChance = 0.1.`%`
}
