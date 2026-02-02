package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.testInfra.dsl.TestDsl
import com.github.seepick.derbauer2.game.testInfra.dsl.WhenDsl
import com.github.seepick.derbauer2.textengine.KeyInput
import kotlin.reflect.KClass

@TestDsl
class WhenBuildPageDsl(whenDsl: WhenDsl) : WhenDsl by whenDsl {
    fun build(buildingClass: KClass<out Building>) {
        val building = user.building(buildingClass)
        selectPrompt("build ${building.labelSingular}")
    }

    fun back() {
        input(KeyInput.Enter)
    }
}

inline fun <reified B : Building> WhenBuildPageDsl.build() {
    build(B::class)
}
