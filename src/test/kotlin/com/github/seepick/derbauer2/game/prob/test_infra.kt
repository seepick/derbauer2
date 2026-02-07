package com.github.seepick.derbauer2.game.prob


fun ProbsImpl.updateSelector(
    key: ProbSelectorKey, newSelector: ProbSelector<out Any>
) {
    val selectorSelector = selectorHandles[key] ?: error("No selector for key $key")
    selectorHandles[key] = selectorSelector.withSelector(newSelector)
}

/** Using dataclass' copy() didn't work properly due to generics issue. */
fun <T> ProbSelectorHandle<T>.withSelector(newSelector: ProbSelector<out Any>): ProbSelectorHandle<T> {
    @Suppress("UNCHECKED_CAST")
    return ProbSelectorHandle(key, newSelector as ProbSelector<T>)
}

fun ProbsImpl.updateProvider(
    key: ProbProviderKey<*>, calc: ProbCalculator
) {
    val providerProvider = providerHandles[key] ?: error("No provider for key $key")
    providerHandles[key] = providerProvider.copy(calculator = calc)
}

fun ProbsImpl.updateDiffuser(
    key: ProbDiffuserKey, diffuser: ProbDiffuser
) {
    val diffuserHandle = diffuserHandles[key] ?: error("No diffuser for key $key")
    diffuserHandles[key] = diffuserHandle.copy(diffuser = diffuser)
}

fun ProbsImpl.updateThresholder(
    key: ProbThresholderKey, thresholder: ProbThresholder
) {
    val thresholderHandle = thresholderHandles[key] ?: error("No thresholder for key $key")
    thresholderHandles[key] = thresholderHandle.copy(thresholder = thresholder)
}
