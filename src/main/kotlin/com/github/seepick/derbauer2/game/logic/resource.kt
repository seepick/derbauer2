package com.github.seepick.derbauer2.game.logic

interface Resource : Entity {
    var units: Units
    val emojiAndUnits: String get() = "${emoji?.let { "$it " } ?: ""} ${units.formatted}"
}

class Gold(override var units: Units) : Resource {
    override val labelSingular = "Gold"
    override val labelPlural = labelSingular
    override val emoji = "💰"
}

class Food(override var units: Units) : Resource {
    override val labelSingular = "Food"
    override val labelPlural = labelSingular
    override val emoji = "🍖"
}
