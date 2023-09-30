import dev.fruxz.ascend.tool.time.calendar.Calendar
import kotlin.test.Test
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import java.util.Calendar as JavaUtilCalendar

class CalendarTest {

    private val STANDPOINT = 1695492368L

    @Test
    fun `Add time to calendar`() {
        val add = 10.days
        val standpointCalendar = Calendar.now().copy(timeInMillis = STANDPOINT)
        val addedCalendar = Calendar.now().copy(timeInMillis = STANDPOINT + add.inWholeMilliseconds)

        assert(standpointCalendar + add == addedCalendar) { "Addition of $add to $standpointCalendar should be $addedCalendar, but was ${standpointCalendar + add}" }

    }

    @Test
    fun `Subtract time from calendar`() {
        val subtract = 10.days
        val standpointCalendar = Calendar.now().copy(timeInMillis = STANDPOINT)
        val subtractedCalendar = Calendar.now().copy(timeInMillis = STANDPOINT - subtract.inWholeMilliseconds)

        assert(standpointCalendar - subtract == subtractedCalendar) { "Subtraction of $subtract from $standpointCalendar should be $subtractedCalendar, but was ${standpointCalendar - subtract}" }

    }

    @Test
    fun `Comparison functionality with Java Calendar`() {
        val calendar = Calendar.fromMilliseconds(STANDPOINT)
        val javaCalendar = JavaUtilCalendar.getInstance().apply { timeInMillis = STANDPOINT }

        calendar += 20.days
        calendar += 25000.milliseconds
        calendar -= 5.hours

        javaCalendar.add(JavaUtilCalendar.DAY_OF_YEAR, 20)
        javaCalendar.add(JavaUtilCalendar.MILLISECOND, 25000)
        javaCalendar.add(JavaUtilCalendar.HOUR_OF_DAY, -5)

        assert(calendar.javaCalendar == javaCalendar) { "Calendar $calendar should be equal to Java Calendar $javaCalendar, but was not" }
    }

}