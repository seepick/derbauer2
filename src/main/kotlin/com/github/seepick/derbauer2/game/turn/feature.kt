package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.feature.FeatureImpl
import com.github.seepick.derbauer2.game.feature.FeatureRef
import com.github.seepick.derbauer2.game.view.AsciiArt
import com.github.seepick.derbauer2.game.view.Texts

class SeasonFeature(ref: Ref = Ref) : FeatureImpl(ref) {
    object Ref : FeatureRef(
        label = "The Four Season",
        asciiArt = AsciiArt.sun,
        multilineDescription = Texts.featureSeasonMultilineDescription,
        checkIt = { _, reports ->
            reports.last().turn.number >= (Mechanics.featureSeasonUnlockTurn - 2) // necessary adjustment
        },
        buildIt = { SeasonFeature() }
    )
}
