package com.github.seepick.derbauer2.game.prob

data class ProbProviderHandle<T>(
    val key: ProbProviderKey<T>,
    val calculator: ProbCalculator,
    val provider: () -> T,
)

@JvmInline
value class ProbProviderKey<T>(val name: String) {
    override fun toString() = "${this::class.simpleName}[$name]"

    companion object
}
