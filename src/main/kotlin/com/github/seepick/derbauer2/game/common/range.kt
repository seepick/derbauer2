package com.github.seepick.derbauer2.game.common

@Suppress("UNCHECKED_CAST")
inline fun <reified SD : StrictDouble, T> rangeOf(
    limits: List<Pair<SD, T>>,
): Range<SD, T> {
    require(limits.isNotEmpty())
    require(limits.isSortedByLimitValue())
    require(limits.hasNoDuplicateLimitValues())
    return when (limits.first().first) {
        is DoubleAny -> Range.Anything(limits as List<Pair<DoubleAny, T>>)
        is DoubleMin1To1 -> Range.Min1To1(limits as List<Pair<DoubleMin1To1, T>>)
        is DoublePos -> Range.Positive(limits as List<Pair<DoublePos, T>>)
        is Double0To1 -> Range.ZeroToOne(limits as List<Pair<Double0To1, T>>)
    } as Range<SD, T>
}

inline fun <reified SD : StrictDouble, T> rangeOf(
    vararg limits: Pair<SD, T>,
): Range<SD, T> = rangeOf(limits.toList())

fun <T> rangeOfMin1To1(
    limits: List<Pair<Double, T>>,
): Range<DoubleMin1To1, T> = rangeOf<DoubleMin1To1, T>(limits.map {
    it.first.strictMin1To1 to it.second
})

fun <T> rangeOfMin1To1(
    vararg limits: Pair<Double, T>,
): Range<DoubleMin1To1, T> = rangeOfMin1To1(limits.toList())

sealed class Range<SD : StrictDouble, T>(private val limits: List<Pair<SD, T>>) {

    fun map(input: SD): T = limits.lastOrNull { it.first.number <= input.number }?.second ?: limits.first().second

    class Anything<T>(limits: List<Pair<DoubleAny, T>>) : Range<DoubleAny, T>(limits)
    class Min1To1<T>(limits: List<Pair<DoubleMin1To1, T>>) : Range<DoubleMin1To1, T>(limits)
    class Positive<T>(limits: List<Pair<DoublePos, T>>) : Range<DoublePos, T>(limits)
    class ZeroToOne<T>(limits: List<Pair<Double0To1, T>>) : Range<Double0To1, T>(limits)
}

fun <SD : StrictDouble, T> List<Pair<SD, T>>.isSortedByLimitValue(): Boolean =
    zipWithNext().all { (a, b) -> a.first.number <= b.first.number }

fun <SD : StrictDouble, T> List<Pair<SD, T>>.hasNoDuplicateLimitValues(): Boolean =
    map { it.first.number }.toSet().size == size
