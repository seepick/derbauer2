package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.features.TradeLandDescriptor
import com.github.seepick.derbauer2.game.technology.TechnologyDescriptor
import com.github.seepick.derbauer2.game.view.AsciiArt

/** Enforce exhaustion despite FeatureDescriptor not being a sealed class */
enum class FeatureDescriptorEnum(val descriptor: FeatureDescriptor) {
    TradeLand(TradeLandDescriptor),
    Technology(TechnologyDescriptor),
}

abstract class FeatureDescriptor(
    override val label: String,
    override val asciiArt: AsciiArt,
    override val multilineDescription: String,
) : FeatureData {

    @Suppress("unused")
    abstract val enumIdentifier: FeatureDescriptorEnum // only to enforce registration here ;)
    abstract fun check(user: User): Boolean
    abstract fun build(): Feature

    companion object {
        val all: List<FeatureDescriptor> by lazy {
            FeatureDescriptorEnum.entries.toList().map { it.descriptor }
        }
    }
}
