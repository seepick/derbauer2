package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import com.github.seepick.derbauer2.game.resource.buildResourceChanges
import com.github.seepick.derbauer2.game.resource.shouldContainChange
import com.github.seepick.derbauer2.game.testInfra.dsl.TestDsl
import com.github.seepick.derbauer2.game.testInfra.dsl.WhenDsl
import com.github.seepick.derbauer2.textengine.KeyInput
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import java.util.concurrent.atomic.AtomicInteger

private val turnStepCounter = AtomicInteger(0)

fun ResourceTurnStep.Companion.build(
    resource: Resource,
    change: Zz,
    order: Int = turnStepCounter.getAndIncrement(),
) = object : ResourceTurnStep {

    override val order = order

    override fun calcChanges() = buildResourceChanges {
        add(resource, change)
    }
}

fun ResourceTurnStep.calcShouldContain(resource: Resource, expected: Zz) {
    calcChanges().shouldContainChange(resource, expected)
}

@TestDsl
class WhenReportPageDsl(whenDsl: WhenDsl) : WhenDsl by whenDsl {
    fun nextPage() {
        input(KeyInput.Enter)
    }
}

fun Arb.Companion.turn() = arbitrary {
    Turn(
        number = int(1..1_000).bind(),
    )
}

fun Arb.Companion.turnReport() = arbitrary {
    TurnReport(
        turn = turn().bind(),
        resourceChanges = ResourceChanges.empty,
        happenings = emptyList(),
        newFeatures = emptyList(),
        actions = emptyList(),
    )
}

fun TurnReport.Companion.empty() = TurnReport(
    turn = Turn(1),
    resourceChanges = ResourceChanges.empty,
    happenings = emptyList(),
    newFeatures = emptyList(),
    actions = emptyList(),
)
