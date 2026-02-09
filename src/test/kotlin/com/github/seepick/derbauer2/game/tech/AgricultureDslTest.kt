package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.WarningType
import com.github.seepick.derbauer2.game.core.shouldHaveEntity
import com.github.seepick.derbauer2.game.core.shouldNotHaveEntity
import com.github.seepick.derbauer2.game.feature.FeatureDescriptorType
import com.github.seepick.derbauer2.game.feature.enableFeature
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Knowledge
import com.github.seepick.derbauer2.game.testInfra.DslTest
import com.github.seepick.derbauer2.game.testInfra.dsl.Given
import com.github.seepick.derbauer2.game.testInfra.dsl.Then
import com.github.seepick.derbauer2.game.testInfra.dsl.When
import com.github.seepick.derbauer2.game.testInfra.installDslExtension
import io.kotest.core.spec.style.StringSpec

class AgricultureDslTest : DslTest, StringSpec() {
    init {
        installDslExtension()
        "Given tech enabled and sufficient resources When research agriculture Then it is enabled" {
            Given {
                setOwned<Knowledge>(1000.z)
                setOwned<Gold>(1000.z)
                user.enableFeature(FeatureDescriptorType.Technology)
                page.invalidate()
            } When {
                selectPrompt("research")
                selectPrompt("agriculture")
            } Then {
                user shouldHaveEntity AgricultureTech::class
            }
        }
        "Given tech enabled and insufficient resources When research agriculture Then fail" {
            Given {
                setOwned<Knowledge>(0.z)
                setOwned<Gold>(0.z)
                user.enableFeature(FeatureDescriptorType.Technology)
                page.invalidate()
            } When {
                selectPrompt("research")
                selectPrompt("agriculture")
            } Then {
                user shouldNotHaveEntity AgricultureTech::class
                shouldHaveRaisedWarningOfType(WarningType.INSUFFICIENT_RESOURCES)
            }
        }
    }
}
