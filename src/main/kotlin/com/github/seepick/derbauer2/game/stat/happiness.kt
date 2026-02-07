package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.DoubleMin1To1
import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.common.emoji
import com.github.seepick.derbauer2.game.common.rangeOfMin1To1
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.feature.Feature
import com.github.seepick.derbauer2.game.feature.FeatureDescriptor
import com.github.seepick.derbauer2.game.feature.FeatureDescriptorType
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.citizen
import com.github.seepick.derbauer2.game.view.AsciiArt

val Emoji.Companion.happiness get() = Happiness.emoji

class Happiness(initialValue: DoubleMin1To1 = DoubleMin1To1(0.0)) : Stat {

    var value: DoubleMin1To1 = initialValue
        private set

    private val emojiRange = rangeOfMin1To1(
        listOf(
            -1.0 to "🤬",
            -0.9 to "😡",
            -0.8 to "😤",
            -0.5 to "☹️",
            -0.3 to "😕",
            -0.1 to "😐",
            0.1 to "🙂",
            0.2 to "😊",
            0.4 to "😃",
            0.6 to "🤗",
            0.8 to "😍",
            0.9 to "🥰",
        ).map { it.first to it.second.emoji }
    )

    val emoji = emojiRange.map(value)

    override fun deepCopy() = this

    override val labelSingular = "Happiness"

    companion object {
        val emoji = "🥹".emoji
    }
}

object HappinessFeatureDescriptor : FeatureDescriptor(
    label = "Happiness",
    asciiArt = AsciiArt.smiley,
    multilineDescription = "Your regular Homo Sapiens became Homo Irrationalis: They can feel ${Emoji.happiness}",
) {
    override val enumIdentifier = FeatureDescriptorType.Happiness

    override fun check(user: User) =
        user.hasEntity(Citizen::class) &&
                user.citizen >= Mechanics.featureHappinessCitizenThresholdGreater

    override fun build() = HappinessFeature(this)
}

class HappinessFeature(descriptor: HappinessFeatureDescriptor) : Feature(descriptor) {
    override val discriminator = Discriminator.Happiness(this)
    override fun deepCopy() = this // immutable
    override fun toString() = "${javaClass.simpleName}[label=$label]"
    override fun execute(user: User) {
        user.add(Happiness(Mechanics.initHappiness))
    }
}
