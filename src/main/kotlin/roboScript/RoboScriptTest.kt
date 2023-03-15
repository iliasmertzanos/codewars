import org.junit.Assert.assertEquals
import org.junit.Test
import roboScript.RoboScriptInterpreter


class RoboScriptTest {

    @Test
    fun sampleTests() {
//        assertPathEquals("LF5(RF3)(RF3R)F7", "    ****\r\n    *  *\r\n    *  *\r\n********\r\n    *   \r\n    *   ")
//        assertPathEquals(
//            "(L(F5(RF3))(((R(F3R)F7))))",
//            "    ****\r\n    *  *\r\n    *  *\r\n********\r\n    *   \r\n    *   "
//        )
        assertPathEquals(
            "F4L(F4RF4RF4LF4L)2F4RF4RF4",
            "    *****   *****   *****\r\n    *   *   *   *   *   *\r\n    *   *   *   *   *   *\r\n    *   *   *   *   *   *\r\n*****   *****   *****   *"
        )
//        assertPathEquals(
//            "F4L((F4R)2(F4L)2)2(F4R)2F4",
//            "    *****   *****   *****\r\n    *   *   *   *   *   *\r\n    *   *   *   *   *   *\r\n    *   *   *   *   *   *\r\n*****   *****   *****   *"
//        )
    }

    private fun assertPathEquals(code: String, expected: String) {
        val actual = RoboScriptInterpreter().drawPath(code)
        assertEquals(
            expected,
            actual,
            "--------------\nCode: $code\nYou returned:\n$actual\nExpected path of robot:\n$expected\n--------------\n"
        )
    }
}