package com.github.seepick.derbauer2.textengine

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.string.shouldContain

class TextmapTest : DescribeSpec({
    describe("constructor") {
        it("Given 1x1 Then return single whitespace") {
            Textmap(1, 1).toFullString() shouldBeEqual " "
        }
        it("Given 2x1 Then return two whitespaces") {
            Textmap(2, 1).toFullString() shouldBeEqual "  "
        }
        it("Given 1x2 Then return two lines") {
            Textmap(1, 2).toFullString() shouldBeEqual " \n "
        }
    }
    describe("line") {
        it("Given 1x1 and single char Then return it") {
            Textmap(1, 1).line("a").toFullString() shouldBeEqual "a"
        }
        it("Given 2x1 and single char Then fill remaining spaces") {
            Textmap(2, 1).line("a").toFullString() shouldBeEqual "a "
        }
        it("Given 1x2 and two lines Then return them") {
            Textmap(1, 2).line("a").line("b").toFullString() shouldBeEqual "a\nb"
        }
        it("Given 1x1 and two chars Then fail") {
            shouldThrow<InvalidTextmapException> {
                Textmap(1, 1).line("aX")
            }.message shouldContain "X"
        }
        it("Given linefeed Then fail") {
            shouldThrow<InvalidTextmapException> {
                Textmap(1, 1).line("\n")
            }
        }
    }
    describe("emptyLine") {
        it("Given 1x1 and single char Then return it") {
            Textmap(1, 1).emptyLine().toFullString() shouldBeEqual " "
        }
    }
    describe("multiLine") {
        it("Given 1x2 and two filled lines Then return them") {
            Textmap(1, 2).multiLine("a\nb").toFullString() shouldBeEqual "a\nb"
        }

        it("Given 1x2 and second line empty Then second line empty") {
            Textmap(1, 2).multiLine("a\n").toFullString() shouldBeEqual "a\n "
        }
    }
    describe("hr") {
        it("Given 3x1 Then fill line with symbol") {
            Textmap(3, 1).hr().toFullString() shouldBeEqual "==="
        }
    }
    describe("aligned") {
        it("Given 4x1 and two single chars each side Then gap of two") {
            Textmap(4, 1).aligned("a", "b").toFullString() shouldBeEqual "a  b"
        }

        it("Given 2x1 and too long strings Then fail") {
            shouldThrow<InvalidTextmapException> {
                Textmap(1, 1).aligned("a", "b")
            }
        }
        it("emoji").config(enabled = false) {
            Textmap(5, 2)
                .aligned("😀", "🚗").toFullString() shouldBeEqual "😀 🚗\n     "
        }
    }
    describe("fillVertical") {
        it("Given 1x3 and fill all Then 3 empty lines") {
            Textmap(1, 3).fillVertical(0).toFullString() shouldBeEqual " \n \n "
        }
        it("Given 1x3 and fill minus 1 and add line Then line is at the bottom") {
            Textmap(1, 3).fillVertical(1).line("x").toFullString() shouldBeEqual " \n \nx"
        }
    }
    describe("reset") {
        it("reset clears buffer and cursor") {
            Textmap(1, 1).line("a").reset().toFullString() shouldBeEqual " "
        }
    }
})
