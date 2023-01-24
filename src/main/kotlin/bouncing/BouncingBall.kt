package bouncing

import org.junit.Assert.assertEquals
import org.junit.Test


fun bouncingBall(buildingHeight: Double, bounce: Double, windowHeight: Double): Int {
    if (buildingHeight <= 0 || bounce <= 0 || bounce >= 1 || windowHeight >= buildingHeight) return -1
    if (1 - bounce < 0.2) return 1
    return 1 + (if ((buildingHeight * bounce) < windowHeight) 0
    else 1 + bouncingBall(buildingHeight * bounce, bounce, windowHeight))
}

class BouncingBallTest {
    @Test
    fun test1() {
        assertEquals(3, bouncingBall(3.0, 0.66, 1.5))
    }

    @Test
    fun test2() {
        assertEquals(15, bouncingBall(30.0, 0.66, 1.5))
    }
}