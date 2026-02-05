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
    val startingFood = 30.z
    // buildings
    val startingGranaries = 1.z
    val startingHouses = 4.z
    val startingFarms = 1.z

    // BUILDINGS
    // ========================================================================
    val houseCostsGold = 40.z
    val houseLandUse = 1.z
    val houseStoreCitizen = 10

    val farmCostsGold = 120.z
    val farmProduceFood = 3.z
    val farmLandUse = 4.z

    val granaryCostsGold = 80
    val granaryCapacity = 50.z
    val granaryLanduse = 2

    // TRADE
    // ========================================================================
    val buyFoodCostGold = 5
    val sellFoodGainGold = 3
    val buyLandCostGold = 50

    // FEATURE
    // ========================================================================
    val featureTradingThresholdFoodStorageUsedBigger = if (DEV) 40.`%` else 80.`%`
    val featureTradeLandThresholdLandAvailableLesser = 3.z
    val featureTechCitizenThresholdGreater = if (DEV) 0.z else startingCitizens * 4.z

    // TECHNOLOGY
    // ========================================================================
    val techAgricultureCostsGold = if (DEV) 10.z else 300.z
    val techAgricultureFoodProductionMultiplier = if (DEV) 200.`%` else 120.`%`
    val techIrrigationCostsGold = 400.z
    val techIrrigationFoodProductionMultiplier = if (DEV) 200.`%` else 120.`%`
    val techJunkFoodCostsGold = 200.z
    val techCapitalismCostsGold = if (DEV) 20.z else 300.z
    val techWarfareCostsGold = 300.z

    // END TURN
    // ========================================================================
    val taxRate = 20.`%`
    val taxGrowthVariation = 40.`%`
    val citizenEatAmount = 10.`%`
    val citizenEatVariation = 30.`%`
    val citizenBirthVariation = 20.`%`
    val citizenBirthRate = 10.`%`
    val citizensStarvePerUnfedCitizen = 30.`%`

    // HAPPENINGS
    val happeningGrowthRate = 1.`%`
    val happeningIsNegativeChance = 5.`%`
}
