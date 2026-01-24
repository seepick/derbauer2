package com.github.seepick.derbauer2.game.engine

import com.github.seepick.derbauer2.engine.KeyPressed
import com.github.seepick.derbauer2.engine.Prompt
import com.github.seepick.derbauer2.engine.SelectOption
import com.github.seepick.derbauer2.engine.one
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class SelectPromptTest : DescribeSpec( {
    val title = "test title"
    val opt1 = SelectOption("opt1") { }

    describe("render text") {
        it("success") {
            Prompt.Select(title, listOf(opt1))
                .renderText() shouldBeEqual """
                    $title

                    [1] ${opt1.label}

                    >> ❓
                """.trimIndent()
        }
    }
    describe("interaction") {
        it("fo") {
            val prompt = Prompt.Select(title, listOf(opt1))
            prompt.onKeyPressed(KeyPressed.one)
        }
    }
})