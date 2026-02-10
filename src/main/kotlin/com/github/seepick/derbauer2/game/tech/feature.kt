package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.feature.FeatureImpl
import com.github.seepick.derbauer2.game.feature.FeatureRef
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Knowledge
import com.github.seepick.derbauer2.game.resource.citizen
import com.github.seepick.derbauer2.game.view.AsciiArt

class TechnologyFeature(ref: Ref = Ref) : FeatureImpl(ref) {
    override fun mutate(user: User) {
        user.add(Knowledge())
        user.add(School())
    }

    object Ref : FeatureRef(
        label = "Technology",
        asciiArt = AsciiArt.book,
        multilineDescription = "Welcome to the age of enlightenment!\n" +
                "You can now research new technologies to advance your civilization.",
        checkIt = { user, _ ->
            user.hasEntity(Citizen::class) &&
                    user.citizen >= Mechanics.featureTechCitizenThresholdGreater
        },
        buildIt = {
            TechnologyFeature()
        },
    )
}
