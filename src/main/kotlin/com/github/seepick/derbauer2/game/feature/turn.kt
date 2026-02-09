package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.Reports

class FeatureTurner(
    private val user: User,
    private val reports: Reports,
) {
    fun buildFeatureMultiPages(): List<FeatureSubPage> =
        FeatureDescriptor.all
            .filter { !user.hasFeature(it) && it.check(user, reports) }
            .map { FeatureSubPage(it.build()) }
}
