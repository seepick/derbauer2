package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.Texts
import com.github.seepick.derbauer2.game.feature.FeatureImpl
import com.github.seepick.derbauer2.game.feature.FeatureRef
import com.github.seepick.derbauer2.game.view.AsciiArt

class SeasonFeature(descriptor: Ref = Ref) : FeatureImpl(descriptor) {
    object Ref : FeatureRef(
        label = "The Four Season",
        asciiArt = AsciiArt.sun,
        multilineDescription = Texts.featureSeasonMultilineDescription,
        checkIt = { _, reports ->
            reports.lastTurnNumber() >= (Mechanics.featureSeasonUnlockTurn - 2) // necessary adjustment
        },
        buildIt = { SeasonFeature() }
    )
}
