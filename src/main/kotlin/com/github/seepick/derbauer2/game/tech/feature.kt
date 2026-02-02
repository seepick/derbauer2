package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.gold
import com.github.seepick.derbauer2.game.feature.Feature
import com.github.seepick.derbauer2.game.feature.FeatureDescriptor
import com.github.seepick.derbauer2.game.feature.FeatureDescriptorEnum
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.view.AsciiArt

object TechnologyFeatureDescriptor : FeatureDescriptor(
    label = "Technology",
    asciiArt = AsciiArt.book,
    multilineDescription = "Welcome to the age of enlightenment!\n" +
            "You can now research new technologies to advance your civilization.",
) {
    override val enumIdentifier = FeatureDescriptorEnum.Technology

    override fun check(user: User) =
        user.hasEntity(Gold::class) && user.gold >= Mechanics.technologyUnlockGoldThreshold

    override fun build() = TechnologyFeature(this)
}

class TechnologyFeature(descriptor: TechnologyFeatureDescriptor) : Feature(descriptor) {
    override val discriminator = Discriminator.Technology(this)
    override fun deepCopy() = this // immutable
    override fun toString() = "${javaClass.simpleName}[label=$label]"
}
