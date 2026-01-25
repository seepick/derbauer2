package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.logic.Asset
import com.github.seepick.derbauer2.game.logic.Units
import com.github.seepick.derbauer2.game.logic.User

val User.resources get() = all.filterIsInstance<Resource>()

interface Resource : Asset {
    val emojiAndUnitsFormatted: String get() = "${emojiWithSpaceSuffixOrEmpty}${owned}"
}

interface StorableResource : Resource

data class Citizen(override var owned: Units) : StorableResource {
    companion object {
        const val EMOJI = "🧑"
    }
    override val labelSingular = "Citizen"
    override val emoji = EMOJI
}

data class Gold(override var owned: Units) : Resource {
    companion object {
        const val EMOJI = "💰"
    }
    override val labelSingular = "Gold"
    override val labelPlural = labelSingular
    override val emoji = EMOJI
}

data class Food(override var owned: Units) : StorableResource {
    companion object {
        const val EMOJI = "🍖"
    }
    override val labelSingular = "Food"
    override val labelPlural = labelSingular
    override val emoji = EMOJI
}

data class Land(override var owned: Units) : Resource {
    companion object {
        const val EMOJI = "🌍"
    }
    override val labelSingular = "Land"
    override val labelPlural = labelSingular
    override val emoji = EMOJI
}
