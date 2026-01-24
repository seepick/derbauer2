package com.github.seepick.derbauer2.game.logic

object Mechanics {
    val startingMoney = 1000.units
}

class User(
    var money: Units = Mechanics.startingMoney,
) {
}
