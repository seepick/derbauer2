package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.User

fun User.enableFeature(feature: FeatureDescriptorType) {
    enable(feature.descriptor.build())
}
