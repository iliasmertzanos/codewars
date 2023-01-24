import org.junit.Assert.assertEquals
import org.junit.Test
import java.awt.Color

fun hexStringToRGB(hexString: String) = Color.decode(hexString).toRGB()

fun Color.toRGB() = RGB(this.red, this.green, this.blue)

data class RGB(var r: Int, var g: Int, var b: Int)

//BEST SOLUTION
//fun hexStringToRGB(hexString: String): RGB = Color.decode(hexString).let { RGB(it.red, it.green, it.blue) }

class TestExample {
    @Test
    fun testFixed() {
        assertEquals(RGB(r = 255, g = 153, b = 51), hexStringToRGB("#FF9933"))
        assertEquals(RGB(r = 190, g = 173, b = 237), hexStringToRGB("#beaded"))
        assertEquals(RGB(r = 0, g = 0, b = 0), hexStringToRGB("#000000"))
        assertEquals(RGB(r = 17, g = 17, b = 17), hexStringToRGB("#111111"))
        assertEquals(RGB(r = 250, g = 52, b = 86), hexStringToRGB("#Fa3456"))
    }
}