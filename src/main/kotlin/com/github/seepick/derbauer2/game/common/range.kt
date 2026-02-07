package com.github.seepick.derbauer2.game.common

fun <T> rangeOfMin1To1(
    limits: List<Pair<Double, T>>,
): Range<DoubleMin1To1, T> = rangeOf<DoubleMin1To1, T>(limits.map {
    it.first.strictMin1To1 to it.second
})

fun <T> rangeOfMin1To1(
    vararg limits: Pair<Double, T>,
): Range<DoubleMin1To1, T> = rangeOfMin1To1(limits.toList())

inline fun <reified SD : StrictDouble, T> rangeOf(
    vararg limits: Pair<SD, T>,
): Range<SD, T> = rangeOf(limits.toList())

@Suppress("UNCHECKED_CAST")
inline fun <reified SD : StrictDouble, T> rangeOf(
    limits: List<Pair<SD, T>>,
): Range<SD, T> {
    require(limits.isNotEmpty())
    require(limits.isSortedByLimitValue())
    require(limits.hasNoDuplicateLimitValues())

    // TODO implement double strict ranging
    return when (limits.first().first) {
        is DoubleAny -> TODO()
        is DoubleMin1To1 -> Range.Min1To1Range(limits as List<Pair<DoubleMin1To1, T>>)
        is DoublePos -> TODO()
        is Double0To1 -> TODO()
    } as Range<SD, T>
}

fun <SD : StrictDouble, T> List<Pair<SD, T>>.isSortedByLimitValue(): Boolean =
    zipWithNext().all { (a, b) -> a.first.number <= b.first.number }

fun <SD : StrictDouble, T> List<Pair<SD, T>>.hasNoDuplicateLimitValues(): Boolean =
    map { it.first.number }.toSet().size == size

sealed class Range<SD : StrictDouble, T> {
    abstract fun map(check: SD): T

    class Min1To1Range<T>(private val limits: List<Pair<DoubleMin1To1, T>>) : Range<DoubleMin1To1, T>() {
        override fun map(check: DoubleMin1To1): T =
            limits.lastValueFor(check.number)

        private fun List<Pair<DoubleMin1To1, T>>.lastValueFor(check: Double): T =
            lastOrNull { it.first.number <= check }?.second ?: first().second
    }
}
