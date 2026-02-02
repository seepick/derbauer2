package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.testInfra.dsl.TestDsl
import com.github.seepick.derbauer2.game.testInfra.dsl.WhenDsl
import com.github.seepick.derbauer2.textengine.KeyInput

@TestDsl
class WhenReportPageDsl(whenDsl: WhenDsl) : WhenDsl by whenDsl {
    fun nextPage() {
        input(KeyInput.Enter)
    }
}
