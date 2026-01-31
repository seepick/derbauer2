package com.github.seepick.derbauer2.game.probability

/** To identify for tests. */
enum class ProbabilitySelectorSource {
    Happenings,
}

fun interface ProbabilitySelector<T> {
    fun select(items: List<T>): T
}

data class ProbabilitySelectorHandle<T>(
    val source: ProbabilitySelectorSource,
    val selector: ProbabilitySelector<T>,
) {
    fun withSelector(newSelector: ProbabilitySelector<out Any>): ProbabilitySelectorHandle<T> {
        @Suppress("UNCHECKED_CAST")
        return ProbabilitySelectorHandle(source, newSelector as ProbabilitySelector<T>)
    }
}

open class AlwaysSameProbabilitySelector<T>(val index: Int) : ProbabilitySelector<T> {
    override fun select(items: List<T>) = items[index]!!
}

class AlwaysFirstProbabilitySelector<T> : AlwaysSameProbabilitySelector<T>(index = 0)

class RandomProbabilitySelector<T> : ProbabilitySelector<T> {
    override fun select(items: List<T>) = items.random()
}
