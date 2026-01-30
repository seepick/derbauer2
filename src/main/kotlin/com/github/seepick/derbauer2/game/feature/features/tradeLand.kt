package com.github.seepick.derbauer2.game.feature.features

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiAndLabelPlural
import com.github.seepick.derbauer2.game.feature.Feature
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.landAvailable
import com.github.seepick.derbauer2.game.view.AsciiArt

object TradeLandDescriptor : FeatureDescriptor(
    label = "Trade Land",
    asciiArt = AsciiArt.island,
    multilineDescription = "You can now buy ${Land.Data.emojiAndLabelPlural} for some other stuff.\nAnd some more... hehe 😅",
) {
    override val enumIdentifier = FeatureDescriptorEnum.TradeLand
    override fun check(user: User) = user.hasEntity(Land::class) && user.landAvailable <= 2
    override fun build() = TradeLandFeature(this)
}

class TradeLandFeature(
    descriptor: TradeLandDescriptor,
) : Feature(descriptor) {
    override val discriminator = Discriminator.TradeLand(this)
    override fun deepCopy() = this // immutable
}
