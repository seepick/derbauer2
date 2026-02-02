package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.User

class FeatureTurner(private val user: User) {
    fun buildFeaturMultiPages(): List<FeatureInfo> =
        FeatureDescriptor.all
            // TODO TX-exec one, could later allow another to check==true
            // thus: do rounds of applying, until no feature returns check==true anymore
            // e.g.: TradingFeature enables TradeLandFeature; should BOTH appear in turn screens
            .filter { !user.hasFeature(it) && it.check(user) }
            .map { FeatureInfo(it.build()) }
}
