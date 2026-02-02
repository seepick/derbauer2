package com.github.seepick.derbauer2.game.testInfra

import org.koin.test.KoinTest
import kotlin.reflect.KClass

fun <T> KoinTest.get(klass: KClass<*>): T =
    getKoin().get(klass)
