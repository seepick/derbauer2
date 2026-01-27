package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.HasEmoji
import com.github.seepick.derbauer2.game.core.HasLabel
import com.github.seepick.derbauer2.game.core.emoji
import kotlin.reflect.KClass

interface Resource : Asset {
    val emojiAndOwned: String get() = "${emojiSpaceOrEmpty}${owned}"
}

interface StorableResource : Resource

interface ResourceReference {
    val resourceClass: KClass<out Resource>
}

data class Citizen(override var _setOwnedOnlyByTx: Z) : StorableResource, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Citizen"
        override val emojiOrNull = "🧑".emoji
    }
}

data class Gold(override var _setOwnedOnlyByTx: Z) : Resource, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Gold"
        override val labelPlural = labelSingular
        override val emojiOrNull = "💰".emoji
    }
}

data class Food(override var _setOwnedOnlyByTx: Z) : StorableResource, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Food"
        override val labelPlural = labelSingular
        override val emojiOrNull = "🍖".emoji
    }
}

data class Land(override var _setOwnedOnlyByTx: Z) : Resource, HasLabel by Data, HasEmoji by Data {
    object Data : HasLabel, HasEmoji {
        override val labelSingular = "Land"
        override val labelPlural = labelSingular
        override val emojiOrNull = "🌍".emoji
    }
}
