package com.github.seepick.derbauer2.common

import io.github.classgraph.ClassGraph
import io.github.classgraph.ClassInfo
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

class ClasspathScanner(packageName: String) {
    private val log = logger {}

    private val scanResult by lazy {
        ClassGraph().enableAllInfo().acceptPackages(packageName).scan().also {
            log.debug { "scan($packageName) -> ${it.allClasses.size} classes" }
        }
    }

    fun <T : Any> scanObjectsImplementing(
        interfaceClass: KClass<T>,
    ): List<T> = scanResult.getClassesImplementing(interfaceClass.qualifiedName).mapNotNull {
        if (it.hasAnnotation(ClasspathScanIgnore::class.qualifiedName)) {
            log.debug {
                "Scan ignoring ${interfaceClass.simpleName}: " +
                        "${it.name} (has ${ClasspathScanIgnore::class.simpleName} annotation)"
            }
            null
        } else if (!it.isObject) {
            log.warn { "${it.name} is not an object! Skipping." }
            null
        } else {
            log.debug { "Scan found ${interfaceClass.simpleName}: ${it.name}" }
            it.objectInstance<T>()
        }
    }

    companion object // for extensions
}

@Target(AnnotationTarget.CLASS)
annotation class ClasspathScanIgnore

private val ClassInfo.isObject get() = getFieldInfo("INSTANCE") != null

@Suppress("UNCHECKED_CAST")
private fun <T> ClassInfo.objectInstance() = Class.forName(name).getField("INSTANCE").get(null) as T
