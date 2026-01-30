package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.view.MultiViewSubPage

enum class HappeningNature {
    Positive,
    Negative,
    Mixed,
    Neutral,
}

enum class HappeningId {
    FoundGold,
    RatsEatFood,
}

interface HappeningData {
    val nature: HappeningNature
    val id: HappeningId
}

interface Happening : MultiViewSubPage, HappeningData
