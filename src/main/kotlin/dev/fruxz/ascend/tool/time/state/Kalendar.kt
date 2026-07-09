package dev.fruxz.ascend.tool.time.state

import dev.fruxz.ascend.tool.time.TimeState
import dev.fruxz.ascend.tool.time.TimeUnit
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.*
import kotlin.time.*
import kotlin.time.Clock
import kotlin.time.Instant
import java.util.Calendar as JavaCalendar
import java.util.TimeZone as JavaTimeZone

@Serializable
data class Kalendar(
    var instant: Instant,
    var timeZone: TimeZone,
) : TimeState, Comparable<Kalendar> {

    // modifications

    fun milliseconds(value: Long) = apply {
        instant = Instant.fromEpochMilliseconds(value)
    }

    fun timeZone(value: TimeZone) = apply {
        timeZone = value
    }

    fun instant(value: Instant) = apply {
        instant = value
    }

    // operations

    operator fun plusAssign(duration: Duration) {
        instant += duration
    }

    operator fun minusAssign(duration: Duration) {
        instant -= duration
    }

    operator fun plus(duration: Duration) =
        Kalendar(instant + duration, timeZone)

    operator fun minus(duration: Duration) =
        Kalendar(instant - duration, timeZone)

    /**
     * @see java.util.Calendar.get
     */
    operator fun get(timeUnit: TimeUnit) =
        java.get(timeUnit.javaField)

    operator fun rangeTo(other: Kalendar) =
        KalendarRange(start = this, endInclusive = other)

    // measurement

    fun durationTo(other: Kalendar) =
        other.instant - instant

    fun durationFrom(other: Kalendar) =
        instant - other.instant

    fun durationToNow() =
        Clock.System.now() - instant

    fun durationFromNow() =
        instant - Clock.System.now()

    var timeInMilliseconds: Long
        get() = instant.toEpochMilliseconds()
        set(value) {
            instant = Instant.fromEpochMilliseconds(value)
        }

    // java

    var java: JavaCalendar
        get() {
            val millis = instant.toEpochMilliseconds()
            return JavaCalendar.getInstance().apply {
                timeInMillis = millis
                timeZone = JavaTimeZone.getTimeZone(this@Kalendar.timeZone.id)
            }
        }
        set(value) {
            instant = Instant.fromEpochMilliseconds(value.timeInMillis)
            timeZone = TimeZone.of(value.timeZone.id)
        }

    fun java(builder: JavaCalendar.() -> Unit) {
        java = java.apply(builder)
    }

    val javaOffset: OffsetDateTime
        get() = OffsetDateTime.ofInstant(instant.toJavaInstant(), timeZone.toJavaZoneId())

    // easy fields

    var localDateTime: LocalDateTime
        get() = LocalDateTime(year, month, dayOfMonth, hour, minute, second, nanosecond)
        set(value) {
            instant = javaOffset.withYear(value.year).withMonth(value.month.number).withDayOfMonth(value.day).withHour(value.hour).withMinute(value.minute).withSecond(value.second).withNano(value.nanosecond).toInstant().toKotlinInstant()
        }

    var year: Int
        get() = javaOffset.year
        set(value) {
            instant = javaOffset.withYear(value).toInstant().toKotlinInstant()
        }

    var month: Month
        get() = javaOffset.month.toKotlinMonth()
        set(value) {
            instant = javaOffset.withMonth(value.ordinal + 1).toInstant().toKotlinInstant()
        }

    var yearMonth: YearMonth
        get() = YearMonth(year, month)
        set(value) {
            instant = javaOffset.withYear(value.year).withMonth(value.month.number).toInstant().toKotlinInstant()
        }

    /**
     * @see java.time.OffsetDateTime.getDayOfMonth
     */
    var dayOfMonth: Int
        get() = javaOffset.dayOfMonth
        set(value) {
            instant = javaOffset.withDayOfMonth(value).toInstant().toKotlinInstant()
        }

    val dayOfWeek: DayOfWeek get() = javaOffset.dayOfWeek.toKotlinDayOfWeek()

    var hour: Int
        get() = javaOffset.hour
        set(value) {
            instant = javaOffset.withHour(value).toInstant().toKotlinInstant()
        }

    var minute: Int
        get() = javaOffset.minute
        set(value) {
            instant = javaOffset.withMinute(value).toInstant().toKotlinInstant()
        }

    var second: Int
        get() = javaOffset.second
        set(value) {
            instant = javaOffset.withSecond(value).toInstant().toKotlinInstant()
        }

    var nanosecond: Int
        get() = javaOffset.nano
        set(value) {
            instant = javaOffset.withNano(value).toInstant().toKotlinInstant()
        }

    val isLeapYear: Boolean
        get() = localDateTime.toJavaLocalDateTime().toLocalDate().isLeapYear

    // strings

    /**
     * Formats this calendar using a custom-built [format]
     */
    fun format(format: DateFormat): String {
        return format.format(java.time)
    }

    /**
     * Formats this calendar using a custom-built [pattern]
     */
    fun format(pattern: String): String {
        return this.format(SimpleDateFormat(pattern))
    }

    /**
     * Formats this calendar using the specified [dateStyle] and [timeStyle] for the [locale]
     */
    fun format(dateStyle: FormatStyle, timeStyle: FormatStyle, locale: Locale = Locale.getDefault()): String {
        val dateFormat = SimpleDateFormat.getDateTimeInstance(dateStyle.ordinal, timeStyle.ordinal, locale)
        return this.format(dateFormat)
    }

    /**
     * Formats this calendar using the default [DateFormat] for the [locale]
     */
    fun format(locale: Locale = Locale.getDefault()): String {
        val dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale)
        return this.format(format = dateFormat)
    }

    /**
     * Formats this calendar using the kotlinx-datetime [DateTimeFormat]
     */
    fun format(dateTimeFormat: DateTimeFormat<LocalDateTime>): String {
        return dateTimeFormat.format(localDateTime)
    }

    override fun toString() = javaOffset.toString()

    fun toString(
        date: FormatStyle,
        time: FormatStyle,
        locale: Locale = Locale.getDefault(),
    ): String = format(dateStyle = date, timeStyle = time, locale = locale)

    fun toISOString() = LocalDateTime.Formats.ISO.format(localDateTime)

    // overrides

    override val infinite: Boolean
        get() = instant.isDistantFuture || instant.isDistantPast

    override val inFuture: Boolean
        get() = instant > Clock.System.now()

    override val inPast: Boolean
        get() = instant < Clock.System.now()

    override val inPresent: Boolean
        get() = instant == Clock.System.now()

    override fun compareTo(other: Kalendar) =
        instant.compareTo(other.instant)

    override fun equals(other: Any?) =
        this === other || (other is Kalendar && instant == other.instant && timeZone == other.timeZone)

    override fun hashCode(): Int {
        var result = instant.hashCode()
        result = 31 * result + timeZone.hashCode()
        return result
    }

    companion object {

        fun now(
            instant: Instant = Clock.System.now(),
            timeZone: TimeZone = TimeZone.currentSystemDefault(),
        ) = Kalendar(instant = instant, timeZone = timeZone)

        fun fromISOString(
            isoString: String,
            timeZone: TimeZone = TimeZone.currentSystemDefault(),
        ) = Kalendar(
            instant = LocalDateTime.Formats.ISO.parse(isoString).toInstant(timeZone),
            timeZone = timeZone,
        )

        fun from(
            milliseconds: Long,
            timeZone: TimeZone = TimeZone.currentSystemDefault(),
        ) = Kalendar(
            instant = Instant.fromEpochMilliseconds(milliseconds),
            timeZone = timeZone,
        )

        fun from(
            localDate: LocalDate,
            timeZone: TimeZone = TimeZone.currentSystemDefault(),
        ) = Kalendar(
            instant = localDate.atStartOfDayIn(timeZone),
            timeZone = timeZone,
        )

        fun from(
            localDateTime: LocalDateTime,
            timeZone: TimeZone = TimeZone.currentSystemDefault(),
        ) = Kalendar(
            instant = localDateTime.toInstant(timeZone),
            timeZone = timeZone,
        )

        fun from(
            year: Int,
            month: Month = Month.JANUARY,
            dayOfMonth: Int = 1,
            hour: Int = 0,
            minute: Int = 0,
            second: Int = 0,
            nanosecond: Int = 0,
            timeZone: TimeZone = TimeZone.currentSystemDefault(),
        ) = Kalendar(
            instant = LocalDateTime(year, month, dayOfMonth, hour, minute, second, nanosecond).toInstant(timeZone),
            timeZone = timeZone,
        )

    }

    enum class FormatStyle {
        FULL, BIG, MEDIUM, SMALL;
    }

}