package com.github.seepick.derbauer2.textengine

import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed
import com.github.seepick.derbauer2.textengine.keyboard.one
import com.github.seepick.derbauer2.textengine.keyboard.two
import com.github.seepick.derbauer2.textengine.prompt.OptionLabel
import com.github.seepick.derbauer2.textengine.prompt.Options
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
    val title = "test title"
    val anyOption = Arb.selectOption().next()
    val doNothingOnSelected = {}

    describe("render text") {
        lateinit var textmap: Textmap
        beforeTest {
            textmap = Textmap(30, 3)
        }
        infix fun <LABEL : OptionLabel, OPTIONS : Options<LABEL>> SelectPrompt<LABEL, OPTIONS>.renderShouldBe(
            expected: String
        ) {
            renderTrimmedFullString(textmap) shouldBeEqual expected
        }

        it("singled") {
            val prompt = SelectPrompt(
                title, Options.Singled(
                    listOf(
                        SelectOption(OptionLabel.Single.Static("foo"), doNothingOnSelected)
                    )
                )
            )

            prompt renderShouldBe """
                $title
                
                [1] foo
                """.trimIndent()
        }
        it("tabled standard") {
            val prompt = SelectPrompt(
                title, Options.Tabled(
                    listOf(
                        SelectOption(OptionLabel.Table(listOf("a", "b")), doNothingOnSelected)
                    )
                )
            )

            prompt renderShouldBe """
                $title
                
                [1] a b
                """.trimIndent()
        }
        it("tabled variable column count") {
            val prompt = SelectPrompt(
                title, Options.Tabled(
                    listOf(
                        SelectOption(OptionLabel.Table(listOf("x")), doNothingOnSelected),
                        SelectOption(OptionLabel.Table(listOf("a", "b")), doNothingOnSelected)
                    )
                )
            )
            textmap = Textmap(30, 4)

            prompt renderShouldBe """
                $title
                
                [1] x
                [2] a b
                """.trimIndent()
        }
    }

    describe("ctor") {
        it("empty fails") {
            shouldThrow<IllegalArgumentException> {
                SelectPrompt(anyTitle, emptyList<SelectOption<OptionLabel.Single.Static>>())
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
