package com.github.seepick.derbauer2.game.happening

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
