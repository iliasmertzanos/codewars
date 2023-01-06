package camelCase

import org.junit.Assert.assertEquals
import org.junit.Test

fun toCamelCase(text: String): String {
    return text.split("-", "_").mapIndexed { index, it -> if (index > 0) it.replaceFirstChar(Char::titlecase) else it }
        .joinToString("")
}

//best solution
//fun toCamelCase(str: String) = str.replace(Regex("(-|_).")) { it.value[1].uppercase() }

class TestExample {
    @Test
    fun testFixed() {
        assertEquals("", toCamelCase(""))
        assertEquals("theStealthWarrior", toCamelCase("the_stealth_warrior"))
        assertEquals("TheStealthWarrior", toCamelCase("The-Stealth-Warrior"))
        assertEquals("ABC", toCamelCase("A-B-C"))
    }
}