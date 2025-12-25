import dev.fruxz.ascend.json.fromJsonString
import dev.fruxz.ascend.json.toJsonString
import dev.fruxz.ascend.tool.time.state.Kalendar
import kotlin.test.Test
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import java.util.Calendar as JavaUtilCalendar

class KalendarTest {

    @Test
    fun `Add time to calendar`() {

        val add = 10.days
        val standpointCalendar = Kalendar.from(milliseconds = STANDPOINT)
        val addedCalendar = Kalendar.now().milliseconds(value = STANDPOINT + add.inWholeMilliseconds)

        assert(standpointCalendar + add == addedCalendar) { "Addition of $add to $standpointCalendar should be $addedCalendar, but was ${standpointCalendar + add}" }

    }

    @Test
    fun `Subtract time from calendar`() {
        val subtract = 10.days
        val standpointCalendar = Kalendar.from(milliseconds = STANDPOINT)
        val subtractedCalendar = Kalendar.now().milliseconds(value = STANDPOINT - subtract.inWholeMilliseconds)

        assert(standpointCalendar - subtract == subtractedCalendar) { "Subtraction of $subtract from $standpointCalendar should be $subtractedCalendar, but was ${standpointCalendar - subtract}" }

    }

    @Test
    fun `Comparison functionality with Java Calendar`() {
        val calendar = Kalendar.from(milliseconds = STANDPOINT)
        val javaCalendar = Kalendar.from(milliseconds = STANDPOINT).java

        calendar += 20.days
        calendar += 25000.milliseconds
        calendar -= 5.hours

        javaCalendar.add(JavaUtilCalendar.DAY_OF_YEAR, 20)
        javaCalendar.add(JavaUtilCalendar.MILLISECOND, 25000)
        javaCalendar.add(JavaUtilCalendar.HOUR_OF_DAY, -5)

        assert(calendar.java == javaCalendar) { "Calendar $calendar should be equal to Java Calendar $javaCalendar, but was not" }
    }

    @Test
    fun `JSON-Conversion`() {
        val calendar = Kalendar.from(milliseconds = STANDPOINT)
        val json = calendar.toJsonString()
        val parsedCalendar = json.fromJsonString<Kalendar>()

        assert(calendar == parsedCalendar) { "Calendar $calendar should be equal to parsed Calendar $parsedCalendar, but was not" }
    }

    companion object {
        const val STANDPOINT = 1695492368000L
    }

}