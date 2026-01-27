package com.github.seepick.derbauer2.game.feature.features

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.Feature
import com.github.seepick.derbauer2.game.feature.FeatureData
import com.github.seepick.derbauer2.game.view.AsciiArt

abstract class FeatureDescriptor(
    override val label: String,
    override val asciiArt: AsciiArt,
    override val description: String,
) : FeatureData {

    abstract fun check(user: User): Boolean
    abstract fun build(): Feature

    companion object {
        val all by lazy {
            listOf(
                TradeLandDescriptor,

                // !!!!!!!!!!!!!!!!!!!!!
                // keep MANUALLY in sync
                // !!!!!!!!!!!!!!!!!!!!!
            )
        }
    }
}
