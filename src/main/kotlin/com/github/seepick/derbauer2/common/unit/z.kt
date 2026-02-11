package com.github.seepick.derbauer2.common.unit

import kotlin.math.ceil
import kotlin.math.floor

/** Unsigned long. */
@Suppress("TooManyFunctions", "MethodOverloading")
data class Z(
    val value: Long,
) : Comparable<Z> {
    init {
        if (value < 0) {
            throw NegativeZException(value)
        }
    }

    val magnitutedValue = translateToMaxMagnitude(value)
    val zz get() = value.zz
    fun toDouble() = value.toDouble()

    fun toPrefixedString() = if (value > 0) "+$this" else toString()
    override fun toString() = magnitutedValue.toString()

    infix fun timesCeil(percent: Percent): Z = ceil(this.value.toDouble() * percent.number).toLong().z
    infix fun timesFloor(percent: Percent): Z = floor(this.value.toDouble() * percent.number).toLong().z

    infix fun divCeil(mulitiplier: Double): Z = ceil(this.toDouble() / mulitiplier).toLong().z
    infix fun divFloor(mulitiplier: Double): Z = floor(this.toDouble() / mulitiplier).toLong().z
    infix fun divFloor(mulitiplier: Z): Z = floor(this.toDouble() / mulitiplier.toDouble()).toLong().z

    operator fun unaryMinus() = Zz(-value)
    operator fun plus(other: Z) = Z(value + other.value)
    operator fun plus(other: Zz) = Zz(value + other.value)
    operator fun plus(other: Long) = Z(value + other)
    operator fun plus(other: Int) = Z(value + other)
    operator fun minus(other: Z) = Z(value - other.value)
    operator fun minus(other: Long) = Z(value - other)
    operator fun minus(other: Int) = Z(value - other)
    operator fun times(other: Z) = Z(value * other.value)
    operator fun times(other: Zz) = Zz(value * other.value)
    operator fun times(other: Long) = Z(value * other)
    operator fun times(other: Int) = Z(value * other)
    operator fun times(other: Percent) = Z((value * other.number).toLong())

    override operator fun compareTo(other: Z) = value.compareTo(other.value)
    operator fun compareTo(other: Long) = value.compareTo(other)
    operator fun compareTo(other: Int) = value.compareTo(other.toLong())
}

// operator fun Long.plus(other: Z) = Z(this + other.value) .........

class NegativeZException(val value: Long) :
    IllegalArgumentException("Negative value for unsigned long: $value")

@Suppress("VariableMinLength")
val Long.z get() = Z(this)
@Suppress("VariableMinLength")
val Int.z get() = Z(toLong())
