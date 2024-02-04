package dev.fruxz.ascend.tool.time

import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import java.util.Calendar as JavaUtilCalendar

interface TimeUnit {

    val name: String
    val javaField: Int
    val durationAccessor: (Duration) -> Long
    val milliseconds: Long
    val digits: Int

    companion object {

        const val DAYS_IN_MONTH = 30

        val entries by lazy {
            listOf(MILLISECOND, SECOND, MINUTE, HOUR, DAY, MONTH, YEAR, ERA)
        }

        @JvmStatic
        val ERA = object : TimeUnit {
            override val name: String = "ERA"
            override val javaField: Int = JavaUtilCalendar.ERA
            override val durationAccessor: (Duration) -> Long = { duration ->
                duration.inWholeDays / 365 / 1000
            }
            override val milliseconds: Long = (365 * 1000).days.inWholeMilliseconds
            override val digits: Int = 1
            override fun toString() = name
        }

        @JvmStatic
        val YEAR = object : TimeUnit {
            override val name: String = "YEAR"
            override val javaField: Int = JavaUtilCalendar.YEAR
            override val durationAccessor: (Duration) -> Long = { duration ->
                duration.inWholeDays / 365
            }
            override val milliseconds: Long = 365.days.inWholeMilliseconds
            override val digits: Int = 4
            override fun toString() = name
        }

        @JvmStatic
        val MONTH = object : TimeUnit {
            override val name: String = "MONTH"
            override val javaField: Int = JavaUtilCalendar.MONTH
            override val durationAccessor: (Duration) -> Long = { duration ->
                duration.inWholeDays / DAYS_IN_MONTH
            }
            override val milliseconds: Long = DAYS_IN_MONTH.days.inWholeMilliseconds
            override val digits: Int = 2
            override fun toString() = name
        }

        @JvmStatic
        val DAY = object : TimeUnit {
            override val name: String = "DAY"
            override val javaField: Int = JavaUtilCalendar.DAY_OF_MONTH
            override val durationAccessor = Duration::inWholeDays
            override val milliseconds: Long = 1.days.inWholeMilliseconds
            override val digits: Int = 2
            override fun toString() = name
        }

        @JvmStatic
        val HOUR = object : TimeUnit {
            override val name: String = "HOUR"
            override val javaField: Int = JavaUtilCalendar.HOUR_OF_DAY
            override val durationAccessor = Duration::inWholeHours
            override val milliseconds: Long = 1.hours.inWholeMilliseconds
            override val digits: Int = 2
            override fun toString() = name
        }

        @JvmStatic
        val MINUTE = object : TimeUnit {
            override val name: String = "MINUTE"
            override val javaField: Int = JavaUtilCalendar.MINUTE
            override val durationAccessor = Duration::inWholeMinutes
            override val milliseconds: Long = 1.minutes.inWholeMilliseconds
            override val digits: Int = 2
            override fun toString() = name
        }

        @JvmStatic
        val SECOND = object : TimeUnit {
            override val name: String = "SECOND"
            override val javaField: Int = JavaUtilCalendar.SECOND
            override val durationAccessor = Duration::inWholeSeconds
            override val milliseconds: Long = 1000
            override val digits: Int = 2
            override fun toString() = name
        }

        @JvmStatic
        val MILLISECOND = object : TimeUnit {
            override val name: String = "MILLISECOND"
            override val javaField: Int = JavaUtilCalendar.MILLISECOND
            override val durationAccessor = Duration::inWholeMilliseconds
            override val milliseconds: Long = 1
            override val digits: Int = 3
            override fun toString() = name
        }

    }

}