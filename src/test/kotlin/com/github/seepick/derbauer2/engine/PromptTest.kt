package com.github.seepick.derbauer2.engine

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class SelectPromptTest : DescribeSpec( {
    val title = "test title"
    val opt1 = SelectOption("opt1") { }

    describe("render text") {
        it("success") {
            val textmap = Textmap(30, 3)
            Prompt.Select(title, listOf(opt1)).render(textmap)
            textmap.toFullString() shouldBeEqual """
                    $title                    
                                                  
                    [1] ${opt1.label}                      
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