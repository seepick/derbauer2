package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.feature.Feature
import com.github.seepick.derbauer2.game.feature.FeatureDescriptor
import com.github.seepick.derbauer2.game.feature.FeatureDescriptorType
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Knowledge
import com.github.seepick.derbauer2.game.resource.citizen
import com.github.seepick.derbauer2.game.turn.Reports
import com.github.seepick.derbauer2.game.view.AsciiArt

class TechnologyFeature(descriptor: Descriptor = Descriptor) : Feature(descriptor) {
    override val discriminator = Discriminator.Technology(this)
    override fun deepCopy() = this // immutable
    override fun toString() = "${javaClass.simpleName}[label=$label]"
    override fun mutate(user: User) {
        user.add(Knowledge())
        user.add(School())
    }

    object Descriptor : FeatureDescriptor(
        label = "Technology",
        asciiArt = AsciiArt.book,
        multilineDescription = "Welcome to the age of enlightenment!\n" +
                "You can now research new technologies to advance your civilization.",
    ) {
        override val enumIdentifier = FeatureDescriptorType.Technology

        override fun check(user: User, reports: Reports) =
            user.hasEntity(Citizen::class) &&
                    user.citizen >= Mechanics.featureTechCitizenThresholdGreater

        override fun build() = TechnologyFeature()
    }
}
