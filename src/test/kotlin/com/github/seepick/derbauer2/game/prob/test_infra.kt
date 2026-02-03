package com.github.seepick.derbauer2.game.prob

fun ProbsImpl.updateSelector(
    key: ProbSelectorKey, newSelector: ProbSelector<out Any>
) {
    val selectorSelector = selectorHandles[key] ?: error("No selector for key $key")
    selectorHandles[key] = selectorSelector.withSelector(newSelector) // copy didnt work properly; generics issue
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
