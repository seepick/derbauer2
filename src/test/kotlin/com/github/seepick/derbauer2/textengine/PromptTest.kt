package com.github.seepick.derbauer2.textengine

import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed
import com.github.seepick.derbauer2.textengine.keyboard.one
import com.github.seepick.derbauer2.textengine.keyboard.two
import com.github.seepick.derbauer2.textengine.prompt.Options
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import com.github.seepick.derbauer2.textengine.prompt.SelectOptionLabel
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
    val title = "test title"
    val anyOption = Arb.selectOption().next()
    val doNothingOnSelected = {}
    describe("render text") {
        val textmap = Textmap(30, 3)
        beforeTest {
            textmap.clear()
        }
        infix fun <LABEL : SelectOptionLabel, OPTIONS : Options<LABEL>> SelectPrompt<LABEL, OPTIONS>.renderShouldBe(
            expected: String
        ) {
            renderTrimmedFullString(textmap) shouldBeEqual expected
        }

        it("singled") {
            val option = SelectOption(SelectOptionLabel.Single.Static("option"), doNothingOnSelected)
            val prompt = SelectPrompt(title, Options.Singled(listOf(option)))

            prompt renderShouldBe """
                $title
                
                [1] ${option.label.value}
                """.trimIndent()
        }
        it("tabled") {
            val option = SelectOption(SelectOptionLabel.Table(listOf("a", "b")), doNothingOnSelected)
            val prompt = SelectPrompt(title, Options.Tabled(listOf(option)))

            prompt renderShouldBe """
                $title
                
                [1] a b
                """.trimIndent()

        }
        // TODO invalid test with different table columns
    }
    describe("ctor") {
        it("empty fails") {
            shouldThrow<IllegalArgumentException> {
                SelectPrompt(anyTitle, emptyList<SelectOption<SelectOptionLabel.Single.Static>>())
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
