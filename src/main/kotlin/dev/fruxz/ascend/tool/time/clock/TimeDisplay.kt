package dev.fruxz.ascend.tool.time.clock

import dev.fruxz.ascend.extension.container.spatialMap
import dev.fruxz.ascend.tool.time.TimeUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toKotlinDuration
import java.time.Duration as JavaTimeDuration

data class TimeDisplay(
    val duration: Duration,
) {

    constructor(javaDuration: JavaTimeDuration) : this(javaDuration.toKotlinDuration())
    constructor(timeUnit: TimeUnit, timeValue: Long) : this((timeUnit.milliseconds * timeValue).milliseconds)
    constructor(timeUnitValues: Map<TimeUnit, Long>) : this(timeUnitValues.entries.sumOf { (unit, value) -> unit.milliseconds * value }.milliseconds)

    val values by lazy {
        TimeUnit.entries
            .associateWith { it.durationAccessor.invoke(duration) }
            .toList()
            .reversed()
            .spatialMap { previous, current, next ->
                if (previous == null) return@spatialMap current
                if (previous.second <= 0) return@spatialMap current

                val previousValue = previous.second
                val currentValue = current.second

                val currentMillis = currentValue * current.first.milliseconds
                val previousMillis = previousValue * previous.first.milliseconds
                val difference = currentMillis - previousMillis

                current.copy(second = difference / current.first.milliseconds)
            }
    }

    fun toClockString(
        vararg views: TimeUnit,
        separator: CharSequence = ":",
        emptyDigit: Char = '0'
    ): String {
        return values
            .filter { views.contains(it.first) }
            .joinToString(separator) { (unit, value) ->
                value.toString().padStart(unit.digits, emptyDigit)
            }
    }

    companion object {

        fun fromMilliseconds(milliseconds: Long) = TimeDisplay(milliseconds.milliseconds)

        operator fun invoke(milliseconds: Long) = fromMilliseconds(milliseconds)

    }

}