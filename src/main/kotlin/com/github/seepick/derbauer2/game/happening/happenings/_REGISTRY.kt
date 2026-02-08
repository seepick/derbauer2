package com.github.seepick.derbauer2.game.happening.happenings

import com.github.seepick.derbauer2.game.happening.HappeningDescriptor

interface HappeningDescriptorRepo {
    /** @return is never empty */
    val all: List<HappeningDescriptor>

    companion object
}

object DefaultHappeningDescriptorRepo : HappeningDescriptorRepo {
    override val all = listOf(
        FoundGoldDescriptor,
        RatsEatFoodDescriptor,
        RottenFoodDescriptor,
        // ...
        // ..
        // .
    )
}
