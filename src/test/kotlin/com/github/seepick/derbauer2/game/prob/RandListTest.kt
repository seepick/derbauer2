package com.github.seepick.derbauer2.game.prob

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.shouldBe

object IdentityListShuffler : ListShuffler {
    override fun <T> shuffle(items: List<T>) = items
}

class RandListTest : StringSpec({

    fun <T> randListBy(list: List<T>) = RandList(list, IdentityListShuffler)
    fun <T> randListOf(vararg items: T) = RandList(items.toList(), IdentityListShuffler)

    "When instantiate with empty list Then fail" {
        shouldThrow<IllegalArgumentException> {
            randListOf<Any>()
        }
    }
    "Given single item When get next Then return it" {
        randListOf(42).next() shouldBe 42
    }

    "Given some When call next for each Then return all" {
        val someList = listOf(5, 2, 3, 1, 4)
        val rand = randListBy(someList)
        (0..someList.size).map { rand.next() }.toSet() shouldBe someList.toSet()
    }
    "Given 2 When get 3 next Then third is cycled" {
        val someList = listOf(1, 2)
        val rand = randListBy(someList)
        repeat(someList.size) { rand.next() }
        someList shouldContain rand.next()
    }
})

class RandomListShufflerTest : StringSpec({
    "Given some When shuffle Then order changed" {
        val list = (1..100).toList()
        val shuffled = RandomListShuffler.shuffle(list)
        shuffled shouldNotBeEqual list
        shuffled.toSet() shouldBe list.toSet()
    }
})
