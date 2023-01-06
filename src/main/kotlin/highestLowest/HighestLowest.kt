package highestLowest

import org.junit.Assert.assertEquals
import org.junit.Test

fun highAndLow(numbers: String): String {
    val numbersArray = numbers.split(" ").map { it.toInt() }
    return "${numbersArray.maxOrNull()} ${numbersArray.minOrNull()}"
}

//best solution
//fun highAndLow(numbers: String) = numbers.split(" ").map { it.toInt() }.run { "${maxOrNull()} ${minOrNull()}" }

class TestExample {
    @Test
    fun test1() {
        assertEquals("42 -9", highAndLow("8 3 -5 42 -1 0 0 -9 4 7 4 -4"))
    }

    @Test
    fun test2() {
        assertEquals("3 1", highAndLow("1 2 3"))
    }
}
