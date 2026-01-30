package com.github.seepick.derbauer2.game.technology

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.gold
import com.github.seepick.derbauer2.game.feature.Feature
import com.github.seepick.derbauer2.game.feature.features.FeatureDescriptor
import com.github.seepick.derbauer2.game.feature.features.FeatureDescriptorEnum
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.view.AsciiArt

object TechnologyDescriptor : FeatureDescriptor(
    label = "Technology",
    asciiArt = AsciiArt.book,
    multilineDescription = "Welcome to the age of enlightenment!\nYou can now research new technologies to advance your civilization.",
) {
    override val enumIdentifier = FeatureDescriptorEnum.Technology

    override fun check(user: User) =
        user.hasEntity(Gold::class) && user.gold >= Mechanics.technologyFeatureUnlockWithGold

    override fun build() = TechnologyFeature(this)
}

class TechnologyFeature(descriptor: TechnologyDescriptor) : Feature(descriptor) {
    override val discriminator = Discriminator.Technology(this)
    override fun deepCopy() = this // immutable
}
