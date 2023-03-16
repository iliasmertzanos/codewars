import org.junit.Assert.assertEquals
import org.junit.Test
import roboScript.RoboScriptInterpreter


class RoboScriptTest {

    @Test
    fun sampleTests() {
        assertPathEquals("((L14F11RR10R1L10R16F8L6RF17LR4L4F2(R16R3L3LF1LLR1L19))3)15L",
            "    *****   *****   *****\r\n    *   *   *   *   *   *\r\n    *   *   *   *   *   *\r\n    *   *   *   *   *   *\r\n*****   *****   *****   *"
        )
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