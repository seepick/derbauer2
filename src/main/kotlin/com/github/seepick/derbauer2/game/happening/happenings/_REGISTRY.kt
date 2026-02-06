package com.github.seepick.derbauer2.game.happening.happenings

import com.github.seepick.derbauer2.game.happening.HappeningDescriptor

interface HappeningDescriptorRepo {
    fun getAllDescriptors(): List<HappeningDescriptor>
}

object DefaultHappeningDescriptorRepo : HappeningDescriptorRepo {

    override fun getAllDescriptors() = listOf(
        FoundGoldDescriptor,
        RatsEatFoodDescriptor,
        // TODO rotten food; independent of season; thus at least 1 negative besides rats eat food
        // when in autumn, possibility of "saison ernte" happening
    )
}
