package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.User

class FeatureTurner(private val user: User) {
    fun buildFeatureMultiPages(): List<FeatureInfo> =
        FeatureDescriptor.all
            .filter { !user.hasFeature(it) && it.check(user) }
            .map { FeatureInfo(it.build()) }
}
