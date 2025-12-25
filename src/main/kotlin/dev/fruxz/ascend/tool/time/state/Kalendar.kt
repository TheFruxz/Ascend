package dev.fruxz.ascend.tool.time.state

import dev.fruxz.ascend.tool.time.TimeState
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaZoneId
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.*
import kotlin.time.*
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

    // strings

    override fun toString() = javaOffset.toString()

    fun toString(
        date: FormatStyle,
        time: FormatStyle,
        locale: Locale = Locale.getDefault(),
    ): String = SimpleDateFormat
        .getDateTimeInstance(date.ordinal, time.ordinal, locale)
        .format(java.time)

    // overrides

    override val infinite = instant.isDistantFuture || instant.isDistantPast

    override val inFuture: Boolean
        get() = instant > Clock.System.now()

    override val inPast: Boolean
        get() = instant < Clock.System.now()

    override fun compareTo(other: Kalendar) =
        instant.compareTo(other.instant)

    override fun equals(other: Any?) =
        this === other || (other is Kalendar && instant == other.instant && timeZone == other.timeZone)

    override fun hashCode(): Int {
        var result = infinite.hashCode()
        result = 31 * result + instant.hashCode()
        result = 31 * result + timeZone.hashCode()
        result = 31 * result + timeInMilliseconds.hashCode()
        result = 31 * result + inFuture.hashCode()
        result = 31 * result + inPast.hashCode()
        result = 31 * result + java.hashCode()
        result = 31 * result + javaOffset.hashCode()
        return result
    }

    companion object {

        fun now(
            instant: Instant = Clock.System.now(),
            timeZone: TimeZone = TimeZone.currentSystemDefault(),
        ) = Kalendar(instant = instant, timeZone = timeZone)

        fun from(
            milliseconds: Long,
            timeZone: TimeZone = TimeZone.currentSystemDefault(),
        ) = Kalendar(
            instant = Instant.fromEpochMilliseconds(milliseconds),
            timeZone = timeZone,
        )

    }

    enum class FormatStyle {
        FULL, BIG, MEDIUM, SMALL;
    }

}
