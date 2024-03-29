package dev.fruxz.ascend.extension.time

import dev.fruxz.ascend.tool.time.calendar.Calendar
import dev.fruxz.ascend.tool.time.calendar.CalendarRange
import dev.fruxz.ascend.tool.time.TimeUnit.Companion.ERA
import dev.fruxz.ascend.tool.time.TimeUnit.Companion.HOUR
import dev.fruxz.ascend.tool.time.TimeUnit.Companion.MILLISECOND
import dev.fruxz.ascend.tool.time.TimeUnit.Companion.MINUTE
import dev.fruxz.ascend.tool.time.TimeUnit.Companion.MONTH
import dev.fruxz.ascend.tool.time.TimeUnit.Companion.SECOND
import dev.fruxz.ascend.tool.time.TimeUnit.Companion.YEAR
import java.util.Calendar as JavaUtilCalendar

/**
 * Converts a [JavaUtilCalendar] to a [Calendar] from Ascend, but keeps it contents.
 * @author Fruxz
 * @since 2023.1
 */
val JavaUtilCalendar.asAscendCalendar: Calendar
	get() = Calendar.fromLegacy(this)

/**
 * Gets the [JavaUtilCalendar], internally converts it, with its contents, to an
 * Ascend-[Calendar], edit it with the [action] in the Ascend/Kotlin-Calender-Environment and
 * returns the [Calendar] converted back to a [JavaUtilCalendar] with the new
 * values containing inside the [JavaUtilCalendar].
 * @param action the edit process, which is executed in the Ascend-[Calendar]-Environment
 * @return the [JavaUtilCalendar] with the new values
 * @author Fruxz
 * @since 2023.1
 */
fun JavaUtilCalendar.editInAscendCalendar(action: Calendar.() -> Unit) = asAscendCalendar.apply(action).produce()

/**
 * This value returns the value of the [Calendar.get] function
 * with the TimeUnit [ERA].
 * @return the value of the [Calendar.get] function with the TimeUnit [ERA].
 * @author Fruxz
 * @since 2023.1
 */
val Calendar.era: Int
	get() = get(ERA)

/**
 * This value returns the value of the [Calendar.get] function
 * with the TimeUnit [YEAR].
 * @return the value of the [Calendar.get] function with the TimeUnit [YEAR].
 * @author Fruxz
 * @since 2023.1
 */
val Calendar.year: Int
	get() = get(YEAR)

/**
 * This value returns the value of the [Calendar.year] value, but
 * attaches 0 at the beginning if the value is lower than the usual
 * used displayed time format.
 * ***Format: ####***
 * @author Fruxz
 * @since 2023.1
 */
val Calendar.styledYear: String
	get() = "$year".padStart(4, '0')

/**
 * This value returns the value of the [Calendar.get] function
 * with the TimeUnit [MONTH] plus 1.
 * @return the value of the [Calendar.get] function with the TimeUnit [MONTH] plus 1.
 * @author Fruxz
 * @since 2023.1
 */
val Calendar.month: Int
	get() = get(MONTH)+1

/**
 * This value returns the value of the [Calendar.month] value, but
 * attaches 0 at the beginning if the value is lower than the usual
 * used displayed time format.
 * ***Format: ##***
 * @author Fruxz
 * @since 2023.1
 */
val Calendar.styledMonth: String
	get() = "$month".padStart(2, '0')

/**
 * This value returns the value of the [JavaUtilCalendar.get] function
 * with the TimeUnit [JavaUtilCalendar.DAY_OF_MONTH].
 * @return the value of the [JavaUtilCalendar.get] function with the TimeUnit [JavaUtilCalendar.DAY_OF_MONTH].
 * @author Fruxz
 * @since 2023.1
 */
val Calendar.day: Int
	get() = javaCalendar.get(JavaUtilCalendar.DAY_OF_MONTH)

/**
 * This value returns the value of the [Calendar.day] value, but
 * attaches 0 at the beginning if the value is lower than the usual
 * used displayed time format.
 * ***Format: ##***
 * @author Fruxz
 * @since 2023.1
 */
val Calendar.styledDay: String
	get() = "$day".padStart(2, '0')

/**
 * This value returns the value of the [Calendar.get] function
 * with the TimeUnit [HOUR].
 * @return the value of the [Calendar.get] function with the TimeUnit [HOUR].
 * @author Fruxz
 */
val Calendar.hour: Int
	get() = get(HOUR)

/**
 * This value returns the value of the [Calendar.hour] value, but
 * attaches 0 at the beginning if the value is lower than the usual
 * used displayed time format.
 * ***Format: ##***
 * @author Fruxz
 * @since 2023.1
 */
val Calendar.styledHour: String
	get() = "$hour".padStart(2, '0')

/**
 * This value returns the value of the [Calendar.get] function
 * with the TimeUnit [MINUTE].
 * @return the value of the [Calendar.get] function with the TimeUnit [MINUTE].
 * @author Fruxz
 * @since 2023.1
 */
val Calendar.minute: Int
	get() = get(MINUTE)

/**
 * This value returns the value of the [Calendar.minute] value, but
 * attaches 0 at the beginning if the value is lower than the usual
 * used displayed time format.
 * ***Format: ##***
 * @author Fruxz
 * @since 2023.1
 */
val Calendar.styledMinute: String
	get() = "$minute".padStart(2, '0')

/**
 * This value returns the value of the [Calendar.get] function
 * with the TimeUnit [SECOND].
 * @return the value of the [Calendar.get] function with the TimeUnit [SECOND].
 * @author Fruxz
 * @since 2023.1
 */
val Calendar.second: Int
	get() = get(SECOND)

/**
 * This value returns the value of the [Calendar.second] value, but
 * attaches 0 at the beginning if the value is lower than the usual
 * used displayed time format.
 * ***Format: ##***
 * @author Fruxz
 * @since 2023.1
 */
val Calendar.styledSecond: String
	get() = "$second".padStart(2, '0')

/**
 * This value returns the value of the [Calendar.get] function
 * with the TimeUnit [MILLISECOND].
 * @return the value of the [Calendar.get] function with the TimeUnit [MILLISECOND].
 * @author Fruxz
 * @since 2023.1
 */
val Calendar.millisecond: Int
	get() = get(MILLISECOND)

/**
 * This value returns the value of the [Calendar.millisecond] value, but
 * attaches 0 at the beginning if the value is lower than the usual
 * used displayed time format.
 * ***Format: ###***
 * @author Fruxz
 * @since 2023.1
 */
val Calendar.styledMillisecond: String
	get() = "$millisecond".padStart(3, '0')

/**
 * This value returns the current state of the [Calendar] object, as a YYYY MM DD HH MM SS SSS format.
 * @author Fruxz
 * @since 2023.1
 */
val Calendar.prettyPrint: String
	get() = "y: $styledYear; m: $styledMonth; d: $styledDay; h: $styledHour; m: $styledMinute; s: $styledSecond; ms: $styledMillisecond"

/**
 * This function creates a [CalendarRange] object from [this] to the [other] Calendar.
 * @param other the other Calendar to create the [CalendarRange] object.
 * @author Fruxz
 * @since 2023.1
 */
operator fun Calendar.rangeTo(other: Calendar) = CalendarRange(this, other)