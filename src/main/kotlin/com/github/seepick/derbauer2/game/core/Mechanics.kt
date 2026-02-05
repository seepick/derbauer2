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
    val startingLand = 20.z
    val startingGold = 400.z
    val startingCitizens = 10.z
    val startingFood = 50.z
    // buildings
    val startingGranaries = 1.z
    val startingHouses = 4.z
    val startingFarms = 1.z

    // BUILDINGS
    // ========================================================================
    val houseCostsGold = 40.z
    val houseLandUse = 1.z
    val houseStoreCitizen = 5

    val farmCostsGold = 120.z
    val farmProduceFood = 3.z
    val farmLandUse = 4.z

    val granaryCostsGold = 80
    val granaryCapacity = 100.z
    val granaryLanduse = 2

    // TRADE
    // ========================================================================
    val buyFoodCostGold = 5
    val sellFoodGainGold = 3
    val buyLandCostGold = 50

    // FEATURE
    // ========================================================================
    val featureTradingThresholdFoodStorageUsedBigger = if (DEV) 40.`%` else 80.`%`
    val featureTradeLandThresholdLandAvailableLesser = 2.z
    val featureTechGoldThresholdGreater = if (DEV) 0.z else (startingGold.value * 1.5).toLong().z

    // TECHNOLOGY
    // ========================================================================
    val techAgricultureCostsGold = if (DEV) 10.z else 300.z
    val techAgricultureFoodProductionMultiplier = if (DEV) 200.`%` else 110.`%`
    val techIrrigationCostsGold = 450.z
    val techIrrigationFoodProductionMultiplier = if (DEV) 200.`%` else 115.`%`
    val techJunkFoodCostsGold = 100.z
    val techCapitalismCostsGold = if (DEV) 20.z else 800.z
    val techWarfareCostsGold = 100.z

    // END TURN
    // ========================================================================
    val taxRate = 10.`%`
    val taxGrowthVariation = 30.`%`
    val citizenEatAmount = 10.`%`
    val citizenEatGrowthVariation = 20.`%`
    val citizensStarvePerUnfedCitizen = 30.`%`
    val citizenBirthRate = 10.`%`

    // HAPPENINGS
    val happeningInitialProb = 0.`%`
    val happeningGrowthRate = 2.`%`
    val happeningIsNegativeChance = 1.`%`
}
