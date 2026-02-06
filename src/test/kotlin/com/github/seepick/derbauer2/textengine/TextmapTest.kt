package com.github.seepick.derbauer2.textengine

import com.github.seepick.derbauer2.textengine.textmap.InvalidTextmapException
import com.github.seepick.derbauer2.textengine.textmap.TableAlign
import com.github.seepick.derbauer2.textengine.textmap.TableCol
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import com.github.seepick.derbauer2.textengine.textmap.TransformingTableCol
import com.github.seepick.derbauer2.textengine.textmap.emptyLine
import com.github.seepick.derbauer2.textengine.textmap.multiLine
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
        it("emoji") {
            Textmap(5, 2).aligned("😀", "🚗").toFullString() shouldBeEqual "😀   🚗\n     "
        }
        it("emoji extra-wide") {
            Textmap(5, 2).aligned("🙎🏻‍♂️", "🚗").toFullString() shouldBeEqual "🙎🏻‍♂️   🚗\n     "
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
    describe("simpleTable") {
        it("empty") {
            Textmap(1, 1).simpleTable(rows = emptyList()).toFullString() shouldBeEqual " "
        }
        it("naiv") {
            Textmap(1, 1).simpleTable(rows = listOf(listOf("x"))).toFullString() shouldBeEqual "x"
        }
        it("simple with default left aligned") {
            Textmap(5, 2).simpleTable(
                rows = listOf(
                    listOf("aa", "b"),
                    listOf("d", "ee"),
                )
            ).toFullString() shouldBeEqual "aa b \nd  ee"
        }
        it("custom column alignment right") {
            Textmap(5, 2).simpleTable(
                cols = listOf(TableCol(align = TableAlign.Right)),
                rows = listOf(
                    listOf("aa"),
                    listOf("b"),
                ),
            ).toFullString() shouldBeEqual "aa   \n b   "
        }
        it("custom column alignment center") {
            Textmap(5, 2).simpleTable(
                cols = listOf(TableCol(align = TableAlign.Center)),
                rows = listOf(
                    listOf("aaa"),
                    listOf("b"),
                ),
            ).toFullString() shouldBeEqual "aaa  \n b   "
        }
        it("variable row lengths allowed") {
            Textmap(3, 2).simpleTable(
                rows = listOf(
                    listOf("a"),
                    listOf("1", "2"),
                )
            ).toFullString() shouldBeEqual "a  \n1 2"
        }
        it("table with simple emojis") {
            Textmap(3, 2).simpleTable(
                rows = listOf(
                    listOf("😀", "b"),
                    listOf("d", "🚗"),
                ),
            ).toFullString() shouldBeEqual "😀 b\nd 🚗"
        }
        it("table with extra-wide emojis") {
            Textmap(3, 2).simpleTable(
                rows = listOf(
                    listOf("🙎🏻‍♂️", "b"),
                    listOf("d", "🚗"),
                ),
            ).toFullString() shouldBeEqual "🙎🏻‍♂️ b\nd 🚗"
        }
    }

    describe("customTable") {
        it("empty") {
            Textmap(1, 1).customTable<String>(
                cols = emptyList(),
                rowItems = emptyList(),
            ).toFullString() shouldBeEqual " "
        }
        it("naiv") {
            data class Item(val value: String)
            Textmap(1, 1).customTable(
                cols = listOf(TransformingTableCol { _, _, item -> item.value }),
                rowItems = listOf(Item(value = "x")),
            ).toFullString() shouldBeEqual "x"
        }
    }
    describe("When clear") {
        it("Then is empty (clears buffer and resets cursor)") {
            Textmap(1, 1).line("a").clear().toFullString() shouldBeEqual " "
        }
    }
})
