package com.github.seepick.derbauer2.textengine

import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed
import com.github.seepick.derbauer2.textengine.keyboard.one
import com.github.seepick.derbauer2.textengine.keyboard.two
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import com.github.seepick.derbauer2.textengine.prompt.SelectPrompt
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.property.Arb
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.string

class SelectPromptTest : DescribeSpec({
    val anyTitle = Arb.string().next()
    val anyOption = Arb.selectOption().next()

    describe("render text") {
        it("success") {
            val textmap = Textmap(30, 3)
            val title = "title"
            val option = SelectOption("option") {}
            val prompt = SelectPrompt(title, listOf(option))

            prompt.renderAndToFullString(textmap) shouldBeEqual """
                    $title                         
                                                  
                    [1] ${option.label()}                    
                """.trimIndent()
        }
    }
    describe("ctor") {
        it("empty fails") {
            shouldThrow<IllegalArgumentException> {
                SelectPrompt(anyTitle, emptyList())
            }
        }
    }
    describe("indicator") {
        it("single option") {
            SelectPrompt(anyTitle, listOf(anyOption)).inputIndicator shouldBeEqual "1-1"
        }
    }
    describe("onKeyPressed") {
        it("Given option When press its key Then return true and invoke selection callback") {
            var optSelected = false
            val opt = SelectOption("any") { optSelected = true }
            val prompt = SelectPrompt(anyTitle, listOf(opt))

            val pressedResult = prompt.onKeyPressed(KeyPressed.one)

            pressedResult shouldBeEqual true
            optSelected shouldBeEqual true
        }
        it("Given option When press invalid key Then return false and not invoke selection callback") {
            var optSelected = false
            val opt = SelectOption("any") { optSelected = true }
            val prompt = SelectPrompt(anyTitle, listOf(opt))

            val pressedResult = prompt.onKeyPressed(KeyPressed.two)

            pressedResult shouldBeEqual false
            optSelected shouldBeEqual false
        }
    }
})
