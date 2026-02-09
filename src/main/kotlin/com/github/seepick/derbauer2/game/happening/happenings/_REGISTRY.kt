package com.github.seepick.derbauer2.game.happening.happenings

import com.github.seepick.derbauer2.game.happening.HappeningRef

interface HappeningRefRegistry {
    /** @return is never empty */
    val all: List<HappeningRef>

    companion object
}

object DefaultHappeningRefRegistry : HappeningRefRegistry {
    override val all = listOf(
        FoundGoldHappening.Ref,
        RatsEatFoodHappening.Ref,
        RottenFoodHappening.Ref,
        // ...
        // ..
        // .
    )
}
