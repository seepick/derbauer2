package com.github.seepick.derbauer2.game.logic

/**
 * All game mechanics constants; a magic constant provider.
 */
object Mechanics {
    val startingLand = 10.units
    val startingGold = 500.units
    val startingCitizens = 4.units
    val startingFood = 50.units

    val startingGranaries = 1.units
    val startingHouses = 1.units
    val startingFarms = 0.units

    val houseCostsGold = 40
    val houseLandUse = 1
    val houseStoreCitizen = 5

    val farmCostsGold = 120
    val farmProduceFood = 3
    val farmLandUse = 4

    val granaryCostsGold = 80
    val granaryCapacity = 100
    val granaryLanduse = 2

    val buyFoodCostGold = 5
    val sellFoodGainGold = 3
    val buyLandCostGold = 50

    val citizenTaxPercentage = 0.5 // multiplied by citizens.amount
    val citizenFoodConsumePercentage = 0.3

    val turnProbHappeningIsNegative = 0.1
}
