package com.github.seepick.derbauer2.common.unit

import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

/** Signed long. */
@Suppress("TooManyFunctions", "MethodOverloading")
data class Zz(
    val value: Long,
) : Comparable<Zz> {
    val magnitutedValue = translateToMaxMagnitude(value)
    val isNegative = value < 0

    fun toSymboledString() = if (value > 0) "+$this" else toString()
    override fun toString() = magnitutedValue.toString()

    /** Will simply remove "-" sign if present, returning its absolute value. */
    fun toZAbs() = abs(value).z
    /** Will throw if negative. */
    fun toZLimitMinZero() = if (value < 0) 0.z else value.z
    fun toZOrThrowIfNegative() = value.z
    fun toDouble() = value.toDouble()

    infix fun timesCeil(mulitiplier: Double): Zz = ceil(this.toDouble() * mulitiplier).toLong().zz
    infix fun timesCeil(percent: Percent): Zz = timesCeil(percent.number)
    infix fun timesFloor(mulitiplier: Double): Zz = floor(this.toDouble() * mulitiplier).toLong().zz
    infix fun timesFloor(percent: Percent): Zz = timesFloor(percent.number)
    infix fun divCeil(mulitiplier: Double): Zz = ceil(this.toDouble() / mulitiplier).toLong().zz
    infix fun divFloor(mulitiplier: Double): Zz = floor(this.toDouble() / mulitiplier).toLong().zz

    operator fun unaryMinus() = Zz(-value)
    operator fun plus(other: Zz) = Zz(value + other.value)
    operator fun plus(other: Long) = Zz(value + other)
    operator fun plus(other: Int) = Zz(value + other)
    operator fun minus(other: Zz) = Zz(value - other.value)
    operator fun minus(other: Z) = Zz(value - other.value)
    operator fun minus(other: Long) = Zz(value - other)
    operator fun minus(other: Int) = Zz(value - other)
    operator fun times(other: Zz) = Zz(value * other.value)
    operator fun times(other: Long) = Zz(value * other)
    operator fun times(other: Int) = Zz(value * other)

    override operator fun compareTo(other: Zz) = value.compareTo(other.value)
    operator fun compareTo(other: Long) = value.compareTo(other)
    operator fun compareTo(other: Int) = value.compareTo(other.toLong())
}

val Long.zz get() = Zz(this)
val Int.zz get() = Zz(toLong())
