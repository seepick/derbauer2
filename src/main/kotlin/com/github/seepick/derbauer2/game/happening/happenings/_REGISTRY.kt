package com.github.seepick.derbauer2.game.happening.happenings

import com.github.seepick.derbauer2.game.happening.HappeningDescriptor

fun interface HappeningDescriptorRepo {
    /** @return is never empty */
    fun getAllDescriptors(): List<HappeningDescriptor>
}

object DefaultHappeningDescriptorRepo : HappeningDescriptorRepo {
    override fun getAllDescriptors() = listOf(
        FoundGoldDescriptor,
        RatsEatFoodDescriptor,
        RottenFoodDescriptor,
    )
}
