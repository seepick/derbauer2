package com.github.seepick.derbauer2.game.core

import io.kotest.assertions.withClue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import kotlin.reflect.KClass

fun User() = User(emptyList())

infix fun User.shouldHaveEntity(entityClass: KClass<out Entity>) {
    withClue({ "Could not find entity of type ${entityClass.simpleName}; contained: $all" }) {
        all.findOrNull(entityClass).shouldNotBeNull()
    }
}

infix fun User.shouldNotHaveEntity(entityClass: KClass<out Entity>) {
    withClue({ "Expected ${entityClass.simpleName} not to be existing, in: $all" }) {
        all.findOrNull(entityClass).shouldBeNull()
    }
}
