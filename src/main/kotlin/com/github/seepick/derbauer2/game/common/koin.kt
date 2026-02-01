package com.github.seepick.derbauer2.game.common

import org.koin.core.annotation.KoinInternalApi
import org.koin.core.definition.Kind
import org.koin.java.KoinJavaComponent
import kotlin.reflect.full.isSubclassOf

@OptIn(KoinInternalApi::class)
inline fun <reified T : Any> getKoinBeansByType(): List<T> =
    KoinJavaComponent.getKoin().let { koin ->
        koin.instanceRegistry.instances.map { it.value.beanDefinition }
            .filter { it.kind == Kind.Singleton }
            .filter { it.primaryType.isSubclassOf(T::class) }
            .map { koin.get(clazz = it.primaryType, qualifier = null, parameters = null) }
    }
