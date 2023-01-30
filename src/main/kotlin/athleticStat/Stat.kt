package athleticStat

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object Stat {

    fun stat(s: String): String = RunnerResult(s).getResults()

    class RunnerResult(results: String) {
        private val defaultFormat = DateTimeFormatter.ofPattern("[HH|mm|ss]")
        private val resultList: List<Duration> = results.split(",").map { result ->
            val format = DateTimeFormatter.ofPattern("[HH|mm|ss][H|mm|ss][H|mm|s]")
            val localeTime = LocalTime.parse(result.trim(), format)
            Duration.between(LocalTime.MIN, localeTime)
        }

        fun getResults() = getRange() + getAverage() + getMedian()

        private fun getRange(): String = resultList.sorted().let { itList ->
            "Range: " + itList.first().minus(itList.last()).abs()
                .let { LocalTime.of(it.toHoursPart(), it.toMinutesPart(), it.toSecondsPart()).format(defaultFormat) }
        }

        private fun getAverage(): String =
            resultList.stream().reduce(Duration.ZERO, Duration::plus).dividedBy(resultList.size.toLong()).let {
                " Average: " + LocalTime.of(
                    it.toHoursPart(), it.toMinutesPart(), it.toSecondsPart()
                ).format(defaultFormat)
            }

        private fun getMedian(): String = resultList.sorted().let {
            if (it.size % 2 == 0) (it[it.size / 2] + it[(it.size - 1) / 2]).dividedBy(2)
            else it[it.size / 2]
        }.let {
            " Median: " + LocalTime.of(it.toHoursPart(), it.toMinutesPart(), it.toSecondsPart()).format(defaultFormat)
        }
    }
}


class StatTest {

    @Test
    fun basicTests() {
        assertEquals(
            "Range: 01|01|18 Average: 01|38|05 Median: 0|32|0",
            Stat.stat("01|15|59, 1|47|16, 01|17|20, 1|32|34, 0|17|0")
        )
        assertEquals(
            "Range: 00|31|17 Average: 02|26|18 Median: 02|22|00",
            Stat.stat("02|15|59, 2|47|16, 02|17|20, 2|32|34, 2|17|17, 2|22|00, 2|31|41")
        )

    }
}