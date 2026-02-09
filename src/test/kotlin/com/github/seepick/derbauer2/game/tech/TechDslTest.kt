package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.feature.addFeature
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
        "Given tech enabled When re-render page Then prompt contains research option" {
            Given {
                user.addFeature(TechnologyFeature())
            } When {
                page.invalidate()
            } Then {
                pageAs<HomePage>().prompt.shouldHaveSelectOption("research")
            }
        }
    }
}
