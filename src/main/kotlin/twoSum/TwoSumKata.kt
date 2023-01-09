package twoSum

import org.junit.Assert.assertEquals
import org.junit.Test

object TwoSum {
    fun twoSum(numbers: IntArray, target: Int): Pair<Int, Int> {
        numbers.forEachIndexed { index1, it ->
            run {
                numbers.forEachIndexed { index2, it2 ->
                    run {
                        if ((it + it2) == target && index1!=index2) {
                            return Pair(index1, index2)
                        }
                    }
                }
            }
        }
        return Pair(0, 0)
    }
}

//best solution
//object TwoSum {
//    fun twoSum(numbers: IntArray, target: Int): Pair<Int,Int> {
//        var a = 0
//        var b = 0
//        numbers.forEachIndexed{index, item ->
//            if (numbers.contains(target - item)){
//                a =  index
//                b = numbers.indexOf(target - item)
//            }
//        }
//        return Pair(a, b)
//    }
//}

//object TwoSum {
//    fun twoSum(numbers: IntArray, target: Int): Pair<Int,Int> {
//        return numbers.mapIndexed { index, value ->
//            Pair(index, numbers.indexOf(target - value))
//        }.filter {
//            it.first != it.second && it.second >= 0
//        }.first()
//    }
//}

class ExampleTests {
    fun runTest(r: IntArray, n: Int, refSol: Pair<Int, Int>) {
        val userSol = TwoSum.twoSum(r, n).let { (x, y) -> if (x > y) Pair(y, x) else Pair(x, y) }
        assertEquals(refSol, userSol)
    }

    @Test
    fun `Basic Tests`() {
        runTest(intArrayOf(1, 2, 3), 4, Pair(0, 2))
        runTest(intArrayOf(1234, 5678, 9012), 14690, Pair(1, 2))
        runTest(intArrayOf(2, 2, 3), 4, Pair(0, 1))
    }
}