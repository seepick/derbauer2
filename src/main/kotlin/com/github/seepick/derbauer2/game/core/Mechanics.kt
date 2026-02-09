package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.DerBauer2
import com.github.seepick.derbauer2.game.common.`%`
import com.github.seepick.derbauer2.game.common.Double01
import com.github.seepick.derbauer2.game.common.Double11
import com.github.seepick.derbauer2.game.common.double01
import com.github.seepick.derbauer2.game.common.k
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.happening.HappeningNature
import com.github.seepick.derbauer2.game.turn.Season

/**
 * All game mechanics constants; a magic constant provider.
 */
@Suppress("MayBeConstant", "MagicNumber", "VariableMaxLength")
object Mechanics {

    private val DEV = DerBauer2.isDevMode

    // INITIAL VALUES
    // ========================================================================

    // resources
    val startingGold = if (DEV) 1.k else 400.z
    val startingLand = 20.z
    val startingFood = 30.z
    val startingCitizens = 10.z
    // buildings
    val startingHouses = 4.z
    val startingGranaries = 1.z
    val startingFarms = 1.z

    // BUILDINGS
    // ========================================================================

    val houseCostsGold = 40.z
    val houseLandUse = 1.z
    val houseStoreCitizen = 10

    val farmCostsGold = 120.z
    val farmProduceFood = 3.z
    val farmLandUse = 4.z

    val granaryCostsGold = 60.z
    val granaryCapacity = 80.z
    val granaryLanduse = 2.z

    // TECH BUILDINGS
    val schoolLanduse = 2.z
    val schoolCostsGold = 200.z
    val schoolProduceKnowledge = 1.z

    // STAT BUILDINGS
    val theaterLanduse = 3.z
    val theaterCostsGold = 250.z

    // TRADE
    // ========================================================================

    val buyFoodCostGold = 5.z
    val sellFoodGainGold = if (DEV) 20.z else 2.z
    val buyLandCostGold = 50.z

    // FEATURE
    // ========================================================================

    val featureTradingThresholdFoodStorageUsedBigger = if (DEV) 40.`%` else 80.`%`
    val featureTradeLandThresholdLandAvailableLesser = 3.z
    val featureTechCitizenThresholdGreater = if (DEV) 0.z else startingCitizens * 3.z
    val featureHappinessCitizenThresholdGreater = if (DEV) 0.z else startingCitizens * 6.z

    // TECHNOLOGY
    // ========================================================================

    val techAgricultureCostsKnowledge = if (DEV) 4.z else 40.z
    val techAgricultureCostsGold = if (DEV) 10.z else 300.z
    val techAgricultureFoodProductionMultiplier = if (DEV) 200.`%` else 120.`%`

    val techIrrigationCostsKnowledge = if (DEV) 6.z else 60.z
    val techIrrigationCostsGold = 400.z
    val techIrrigationFoodProductionMultiplier = if (DEV) 200.`%` else 120.`%`

    val techCapitalismCostsKnowledge = if (DEV) 8.z else 80.z
    val techCapitalismCostsGold = if (DEV) 20.z else 400.z
    val techCapitalismTaxMultiplier = 120.`%`

    // STATS
    // ========================================================================
    val startingHappiness = Double11(if (DEV) 0.0 else 0.4)
    val statHappinessConsumedPerCitizen = if (DEV) Double.MIN_VALUE else 0.00001
    val theaterProducesHappiness = if (DEV) 0.1 else 0.01
    val citizenBirthHappinessEffect = 30.`%`
    val techCapitalismHappinessPerCitizenMultiplier = -0.001

    // END TURN
    // ========================================================================

    val taxRate = 20.`%`
    val taxGrowthVariation = 40.`%`
    val citizenEatAmount = 10.`%`
    val citizenEatVariation = 30.`%`
    val citizenBirthVariation = 30.`%`
    val citizenBirthRate = 6.`%`
    val citizensStarvePerUnfedCitizen = 30.`%`

    // HAPPENINGS
    val happeningGrowthRate = if (DEV) 90.`%` else 1.`%`
    val happeningIsNegativeChance = if (DEV) 90.`%` else 5.`%`
}

@Suppress("MagicNumber")
val Season.happinessChanger: Double
    get() = when (this) {
        Season.Spring -> 0.005
        Season.Summer -> 0.01
        Season.Autumn -> -0.005
        Season.Winter -> -0.01
    }

val Season.ratsWillEatFoodProb: Double01
    get() = when (this) {
        Season.Winter -> 1.0.double01
        Season.Autumn -> 0.75.double01
        Season.Spring -> 0.5.double01
        Season.Summer -> 0.25.double01
    }

val HappeningNature.happinessImpact
    get() = when (this) {
        HappeningNature.Negative -> -0.05
        HappeningNature.Mixed -> 0.0
        HappeningNature.Neutral -> 0.0
        HappeningNature.Positive -> 0.05
    }
