package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.testInfra.dsl.TestDsl
import com.github.seepick.derbauer2.game.testInfra.dsl.WhenDsl
import com.github.seepick.derbauer2.textengine.KeyInput
import kotlin.reflect.KClass

@TestDsl
class WhenBuildPageDsl(whenDsl: WhenDsl) : WhenDsl by whenDsl {
    fun build(buildingClass: KClass<out Building>) {
        val building = user.findBuilding(buildingClass)
        selectPrompt("build ${building.labelSingular}")
    }

    fun back() {
        input(KeyInput.Enter)
    }
}

inline fun <reified B : Building> WhenBuildPageDsl.build() {
    build(B::class)
}

fun <B : Building> User.addBuilding(building: B, amount: Z): B {
    add(building)
    // by-pass validation via transaction
    building._setOwnedInternal = amount
    return building
}
