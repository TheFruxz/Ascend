import dev.fruxz.ascend.tool.time.calendar.Calendar
import kotlin.test.Test
import kotlin.time.Duration.Companion.days

class CalendarTest {

    private val STANDPOINT = 1695492368

    @Test
    fun `Add time to calendar`() {
        val add = 10.days
        val standpointCalendar = Calendar.now().copy(timeInMillis = STANDPOINT.toLong())
        val addedCalendar = Calendar.now().copy(timeInMillis = STANDPOINT.toLong() + add.inWholeMilliseconds)

        assert(standpointCalendar + add == addedCalendar) { "Addition of $add to $standpointCalendar should be $addedCalendar, but was ${standpointCalendar + add}" }

    }

    @Test
    fun `Subtract time from calendar`() {
        val subtract = 10.days
        val standpointCalendar = Calendar.now().copy(timeInMillis = STANDPOINT.toLong())
        val subtractedCalendar = Calendar.now().copy(timeInMillis = STANDPOINT.toLong() - subtract.inWholeMilliseconds)

        assert(standpointCalendar - subtract == subtractedCalendar) { "Subtraction of $subtract from $standpointCalendar should be $subtractedCalendar, but was ${standpointCalendar - subtract}" }

    }

}