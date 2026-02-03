package com.github.seepick.derbauer2.game.prob

fun interface ProbSelector<T> {
    fun select(items: List<T>): T
}

data class ProbSelectorHandle<T>(
    val key: ProbSelectorKey,
    val selector: ProbSelector<T>,
) {
    fun withSelector(newSelector: ProbSelector<out Any>): ProbSelectorHandle<T> {
        @Suppress("UNCHECKED_CAST")
        return ProbSelectorHandle(key, newSelector as ProbSelector<T>)
    }
}

@JvmInline
value class ProbSelectorKey(val name: String) {
    override fun toString() = "ProbSelectorKey[name]"

    companion object
}

open class AlwaysSameProbSelector<T>(val index: Int) : ProbSelector<T> {
    override fun select(items: List<T>) = items[index]!!
}

class AlwaysFirstProbSelector<T> : AlwaysSameProbSelector<T>(index = 0)

class RandomProbSelector<T> : ProbSelector<T> {
    override fun select(items: List<T>) = items.random()
}
