package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.feature.Feature
import com.github.seepick.derbauer2.game.feature.FeatureRef
import com.github.seepick.derbauer2.game.turn.Reports
import com.github.seepick.derbauer2.game.view.AsciiArt

@Suppress("MagicNumber")
enum class UserProgress(
    val label: String,
) {
    // keep order!
    Sir("Young Sire"),
    Lord("Lord"),
    King("King"),
    Emperor("Emperor");

    companion object {
        val initial = entries.first()
    }
}

class UserTitleLordFeature : Feature by Ref {
    object Ref : UserTitleRef(
        enumValue = UserProgress.Lord,
        checkIt = { _, reports -> reports.last().turn >= Mechanics.featureLordAge },
        buildIt = { UserTitleLordFeature() }
    )
}

abstract class UserTitleRef(
    val enumValue: UserProgress,
    checkIt: (user: User, reports: Reports) -> Boolean,
    buildIt: () -> Feature,
) : FeatureRef(
    label = "Social Climber",
    multilineDescription = "Congratulations, you gained a new title: ${enumValue.label}!",
    asciiArt = AsciiArt.shield,
    checkIt = checkIt,
    buildIt = buildIt,
), Feature {
    override val ref = this

    override fun mutate(user: User) {
        user.userProgress = enumValue
    }

    override fun deepCopy() = this
}
