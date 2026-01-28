package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.building.Barracks
import com.github.seepick.derbauer2.game.building.Castle
import com.github.seepick.derbauer2.game.building.Church
import com.github.seepick.derbauer2.game.building.University
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.features.FeatureDescriptor
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.view.AsciiArt

/**
 * Military feature - enables army recruitment and military actions.
 * Based on documentation/cleanup/feature.md and military.md
 */
object MilitaryFeatureDescriptor : FeatureDescriptor(
    label = "Military",
    asciiArt = AsciiArt.Happening,
    description = "Enables military units and combat. Build a barracks to recruit soldiers and defend your realm.",
) {
    override fun check(user: User) = user.hasEntity(Barracks::class)
    override fun build() = MilitaryFeature(this)
}

class MilitaryFeature(descriptor: MilitaryFeatureDescriptor) : Feature(descriptor) {
    override fun deepCopy() = this
}

/**
 * Advanced Trading feature - enables more trading options.
 * Based on documentation/cleanup/feature.md and trade.md
 */
object AdvancedTradingFeatureDescriptor : FeatureDescriptor(
    label = "Advanced Trading",
    asciiArt = AsciiArt.Happening,
    description = "Unlock advanced trading routes and better prices for your goods.",
) {
    override fun check(user: User) = user.get<Gold>().owned >= 500
    override fun build() = AdvancedTradingFeature(this)
}

class AdvancedTradingFeature(descriptor: AdvancedTradingFeatureDescriptor) : Feature(descriptor) {
    override fun deepCopy() = this
}

/**
 * Religious feature - enables religious buildings and benefits.
 * Based on documentation/cleanup/feature.md and building.md
 */
object ReligiousFeatureDescriptor : FeatureDescriptor(
    label = "Religious",
    asciiArt = AsciiArt.Happening,
    description = "Build churches to increase happiness and gain divine favor in happenings.",
) {
    override fun check(user: User) = user.hasEntity(Church::class)
    override fun build() = ReligiousFeature(this)
}

class ReligiousFeature(descriptor: ReligiousFeatureDescriptor) : Feature(descriptor) {
    override fun deepCopy() = this
}

/**
 * Noble feature - enables nobility quarters and castle.
 * Based on documentation/cleanup/feature.md and building.md
 */
object NobleFeatureDescriptor : FeatureDescriptor(
    label = "Nobility",
    asciiArt = AsciiArt.Happening,
    description = "Unlock the ability to build castles and establish a noble class.",
) {
    override fun check(user: User) = user.get<Citizen>().owned >= 50
    override fun build() = NobleFeature(this)
}

class NobleFeature(descriptor: NobleFeatureDescriptor) : Feature(descriptor) {
    override fun deepCopy() = this
}

/**
 * Research feature - enables technology and knowledge gathering.
 * Based on documentation/cleanup/feature.md and technology.md
 */
object ResearchFeatureDescriptor : FeatureDescriptor(
    label = "Research",
    asciiArt = AsciiArt.Happening,
    description = "Build a university to research new technologies and unlock upgrades.",
) {
    override fun check(user: User) = user.hasEntity(University::class)
    override fun build() = ResearchFeature(this)
}

class ResearchFeature(descriptor: ResearchFeatureDescriptor) : Feature(descriptor) {
    override fun deepCopy() = this
}

/**
 * Castle Actions feature - enables throne room visitors and special actions.
 * Based on documentation/cleanup/feature.md and building.md
 */
object CastleActionsFeatureDescriptor : FeatureDescriptor(
    label = "Castle Actions",
    asciiArt = AsciiArt.Happening,
    description = "Your castle now receives visitors who may offer quests and opportunities.",
) {
    override fun check(user: User) = user.hasEntity(Castle::class)
    override fun build() = CastleActionsFeature(this)
}

class CastleActionsFeature(descriptor: CastleActionsFeatureDescriptor) : Feature(descriptor) {
    override fun deepCopy() = this
}
