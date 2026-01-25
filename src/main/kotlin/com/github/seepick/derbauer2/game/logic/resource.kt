package com.github.seepick.derbauer2.game.logic

interface Resource : Asset {
    var owned: Units
    val emojiAndUnitsFormatted: String get() = "${emojiWithSpaceSuffixOrEmpty}${owned.formatted}"
}

interface StorableResource : Resource {

}

class Gold(override var owned: Units) : Resource {
    override val labelSingular = "Gold"
    override val labelPlural = labelSingular
    override val emoji = "💰"
}

class Food(override var owned: Units) : StorableResource {
    override val labelSingular = "Food"
    override val labelPlural = labelSingular
    override val emoji = "🍖"
}
