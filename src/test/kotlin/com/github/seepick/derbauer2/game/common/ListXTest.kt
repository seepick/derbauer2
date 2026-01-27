package com.github.seepick.derbauer2.game.common

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlin.test.DefaultAsserter.fail

class ListXTest : DescribeSpec({

    describe("findOrNull simple") {
        it("Given empty list Then null") {
            val listx = ListX(emptyList())

            listx.findOrNull<String>().shouldBeNull()
        }
        it("Given non-matching Then null") {
            val listx = ListX<Any>(listOf("x"))

            listx.findOrNull<Int>().shouldBeNull()
        }
        it("Given matching Then find it") {
            val listx = ListX<Any>(listOf("x"))

            listx.findOrNull<String>().shouldNotBeNull() shouldBeEqual "x"
        }
        it("Given two matching Then fail") {
            val listx = ListX<Any>(listOf("test1", "test2"))

            shouldThrow<IllegalStateException> {
                listx.findOrNull<String>()
            }.message.should {
                it.shouldNotBeNull()
                it shouldContain "String"
                it shouldContain "test1"
                it shouldContain "test2"
            }
        }
    }
    describe("findOrNull types") {
        open class SuperType
        open class SubType : SuperType()
        open class SubTypeA : SuperType()
        class SubTypeB : SuperType()

        val superType = SuperType()
        val subType = SubType()
        val subTypeA = SubTypeA()
        val subTypeB = SubTypeB()

        it("Given twice same type When find it Then fail") {
            val listx = ListX(listOf(superType, superType))

            shouldThrow<IllegalStateException> { listx.findOrNull<SuperType>() }
                .message.shouldNotBeNull().should {
                    it.contains("2")
                    it.contains(SuperType::class.simpleName!!)
                }
        }
        it("Given two different subs When find one Then return it") {
            val listx = ListX(listOf(subTypeA, subTypeB))

            listx.findOrNull<SubTypeA>().shouldNotBeNull() shouldBeEqual subTypeA
        }
        it("Given super and sub When find super Then return super And not sub") {
            val listx = ListX(listOf(superType, subType))

            listx.findOrNull<SuperType>().shouldNotBeNull() shouldBeEqual superType
        }
        it("Given sub When find super Then return null as of exact match") {
            val listx = ListX<SuperType>(listOf(subType))

            listx.findOrNull<SuperType>().shouldBeNull()
        }
    }
    describe("find") { // simply delegates to findOrNull
        it("Given empty Then null") {
            val listx = ListX(emptyList())

            shouldThrow<NotFoundEntityException> {
                listx.find<String>()
            }.message.should {
                it.shouldNotBeNull()
                it.contains("String")
            }
        }
        it("Given matching Then find it") {
            val listx = ListX(listOf("foobar"))

            listx.find<String>() shouldBeEqual "foobar"
        }
    }
    describe("alsoIfExists") {
        it("Given empty Then not called") {
            ListX(emptyList()).alsoIfExists(String::class) {
                fail("Should not be called")
            }
        }
        it("Given matching Then pass it") {
            var called = false
            ListX(listOf("x")).alsoIfExists(String::class) {
                it shouldBe "x"
                called = true
            }
            called.shouldBeTrue()
        }
    }

    describe("letIfExists") {
        it("Given empty Then not called And null") {
            ListX(emptyList()).letIfExists(String::class) {
                fail("Should not be called")
            }.shouldBeNull()
        }
        it("Given matching Then called And transform it") {
            var called = false
            val result = ListX(listOf("x")).letIfExists(String::class) {
                it shouldBe "x"
                called = true
                42
            }
            called.shouldBeTrue()
            result.shouldNotBeNull() shouldBeEqual 42
        }
    }
})
