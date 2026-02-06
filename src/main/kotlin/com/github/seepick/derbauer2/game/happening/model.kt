package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.view.MultiViewSubPage

enum class HappeningNature {
    Positive,
    Negative,
    Mixed,
    Neutral,
}

interface HappeningData {
    val nature: HappeningNature
}

interface Happening : MultiViewSubPage, HappeningData

abstract class HappeningDescriptor(
    override val nature: HappeningNature
) : HappeningData {
    abstract fun canHappen(user: User): Boolean
    abstract fun buildHappening(user: User): Happening

    companion object // for extensions
}
