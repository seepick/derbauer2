package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.Double11
import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.common.emoji
import com.github.seepick.derbauer2.game.common.rangeOfMin1To1
import com.github.seepick.derbauer2.game.common.toFormatted
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.core.simpleNameEmojied
import com.github.seepick.derbauer2.game.feature.FeatureImpl
import com.github.seepick.derbauer2.game.feature.FeatureRef
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.citizen
import com.github.seepick.derbauer2.game.view.AsciiArt
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val happinessEmoji = "🥳".emoji
@Suppress("ObjectPropertyName", "NonAsciiCharacters")
val Emoji.Companion.`happiness 🥳` get() = happinessEmoji

class Happiness(
    initialValue: Double11 = Double11(0.0),
) : Stat<Double11> {

    private val log = logger {}
    override var currentValue = initialValue
        private set
    val currentEmoji get() = emojiRange.map(currentValue)
    override val label = "Happiness"

    override fun changeBy(amount: Double) { // reuse in the future, once a 2nd stat exists
        val newValue = Double11((currentValue.number + amount).coerceIn(-1.0, 1.0))
        log.debug { "changing ${Emoji.`happiness 🥳`} by ${amount.toFormatted()} => ${newValue.number.toFormatted()}" }
        currentValue = newValue
    }

    override fun changeTo(value: Double11) {
        log.trace { "Changing value from ${this.currentValue} to $value" }
        this.currentValue = value
    }

    override fun deepCopy() = Happiness(currentValue)
    override fun toString() = "${this::class.simpleNameEmojied}($currentValue)"

    companion object {
        val emoji = Emoji.`happiness 🥳`

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
                0.9 to Emoji.`happiness 🥳`.string,
            ).map { it.first to it.second.emoji },
        )
    }
}

class HappinessFeature(ref: Ref = Ref) : FeatureImpl(ref) {
    override fun mutate(user: User) {
        user.add(Happiness(Mechanics.startingHappiness))
        user.add(Theater())
    }

    object Ref : FeatureRef(
        label = "Happiness",
        asciiArt = AsciiArt.smiley,
        multilineDescription = "Your regular Homo Sapiens became Homo Irrationalis: They can feel ${Emoji.`happiness 🥳`}",
        checkIt = { user, _ ->
            user.hasEntity(Citizen::class) &&
                    user.citizen >= Mechanics.featureHappinessCitizenThresholdGreater
        },
        buildIt = {
            HappinessFeature()
        },
    )
}
