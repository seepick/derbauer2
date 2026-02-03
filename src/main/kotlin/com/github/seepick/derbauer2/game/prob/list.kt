package com.github.seepick.derbauer2.game.prob

interface ListShuffler {
    fun <T> shuffle(items: List<T>): List<T>
}

object RandomListShuffler : ListShuffler {
    override fun <T> shuffle(items: List<T>) =
        items.shuffled()
}

fun <T> randListBy(list: List<T>, shuffler: ListShuffler) = RandList(list, shuffler)
fun <T> randListOf(vararg items: T, shuffler: ListShuffler) = RandList(items.toList(), shuffler)

/**
 * Returns random elements but guaranteed each element only once until all have been returned, and then resets.
 */
class RandList<T>(
    private val allItems: List<T>,
    private val shuffler: ListShuffler,
) {
    private val remainingItems = mutableListOf<T>()

    init {
        require(allItems.isNotEmpty())
    }

    fun next(): T {
        if (remainingItems.isEmpty()) {
            resetRemainingItems()
        }
        return remainingItems.removeFirst()
    }

    private fun resetRemainingItems() {
        require(remainingItems.isEmpty())
        remainingItems.addAll(shuffler.shuffle(allItems))
    }
}
