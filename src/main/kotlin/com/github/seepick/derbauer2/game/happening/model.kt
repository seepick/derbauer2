package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.happening.happenings.FoundGoldDescriptor
import com.github.seepick.derbauer2.game.happening.happenings.RatsEatFoodDescriptor
import com.github.seepick.derbauer2.game.view.MultiViewSubPage

enum class HappeningNature {
    Positive,
    Negative,
    Mixed,
    Neutral,
}

/** Enforce exhaustion despite HappeningDescriptor not being a sealed class */
enum class HappeningType(val descriptor: HappeningDescriptor) {
    FoundGold(FoundGoldDescriptor),
    RatsEatFood(RatsEatFoodDescriptor);
}

interface HappeningData {
    val nature: HappeningNature
    val type: HappeningType
}

interface Happening : MultiViewSubPage, HappeningData

abstract class HappeningDescriptor(
    override val nature: HappeningNature
) : HappeningData {
    abstract fun canHappen(user: User): Boolean
    abstract fun buildHappening(user: User): Happening

    companion object // for extensions
}
