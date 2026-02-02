package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.feature.FeatureDescriptorType
import com.github.seepick.derbauer2.game.feature.enableFeature
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.testInfra.DslTest
import com.github.seepick.derbauer2.game.testInfra.dsl.Given
import com.github.seepick.derbauer2.game.testInfra.dsl.Then
import com.github.seepick.derbauer2.game.testInfra.dsl.When
import com.github.seepick.derbauer2.game.testInfra.dsl.pageAs
import com.github.seepick.derbauer2.game.testInfra.installDslExtension
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.textengine.shouldHaveSelectOption
import io.kotest.core.spec.style.StringSpec

class TechDslTest : DslTest, StringSpec() {
    init {
        installDslExtension()
        "Given tech feature enabled When re-render page Then prompt contains research option" {
            Given {
                user.enableFeature(FeatureDescriptorType.Technology)
            } When {
                page.invalidate()
            } Then {
                pageAs<HomePage>().prompt.shouldHaveSelectOption("research")
            }
        }
        "Given tech When research Then it is enabled" {
            Given {
                setOwned<Gold>(1000.z)
                user.enableFeature(FeatureDescriptorType.Technology)
                page.invalidate()
            } When {
                selectPrompt("research")
                selectPrompt("agriculture")
            } Then {
//                    shouldRaiseWarning("insufficient resources")
            }
        }
    }
}
