package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.Reports
import com.github.seepick.derbauer2.game.view.AsciiArt

abstract class FeatureRef(
    override val label: String,
    override val asciiArt: AsciiArt,
    override val multilineDescription: String,
) : FeatureData {

    @Suppress("unused")
    abstract fun check(user: User, reports: Reports): Boolean
    abstract fun build(): Feature
}
