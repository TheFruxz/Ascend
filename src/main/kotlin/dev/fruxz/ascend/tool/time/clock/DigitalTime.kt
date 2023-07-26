package dev.fruxz.ascend.tool.time.clock

import dev.fruxz.ascend.tool.time.clock.DigitalTime.Format.*
import kotlin.time.Duration
import kotlin.time.toKotlinDuration
import java.time.Duration as JavaTimeDuration

/**
 * This data class helps to easily create a displayable Time-String, which can be set up to display the data you want to display.
 *
 * @param ticks the number of ticks used in this time
 * @since 2023.1
 * @author Fruxz
 */
data class DigitalTime(
	var ticks: Double
) {

	constructor(format: Format, timeValue: Double) : this(format.ticks(timeValue))

	constructor(duration: Duration) : this(duration.inWholeSeconds.toDouble() * 20)

	constructor(javaDuration: JavaTimeDuration) : this(javaDuration.toKotlinDuration())

	/**
	 * Generates a displayable string, but only contains
	 * the values of the time units specified by [views].
	 *
	 * @param views the displaying time units
	 * @return view-string
	 * @since 2023.1
	 * @author Fruxz
	 */
	fun computeTimeString(vararg views: Format): String {
		var tempTicks = ticks

		val remainingYears = (tempTicks / TICKS_PER_YEAR).toInt()
		tempTicks -= remainingYears * TICKS_PER_YEAR

		val remainingDays = (tempTicks / TICKS_PER_DAY).toInt()
		tempTicks -= remainingDays * TICKS_PER_DAY

		val remainingHours = (tempTicks / TICKS_PER_HOUR).toInt()
		tempTicks -= remainingHours * TICKS_PER_HOUR

		val remainingMinutes = (tempTicks / TICKS_PER_MINUTE).toInt()
		tempTicks -= remainingMinutes * TICKS_PER_MINUTE

		val remainingSeconds = (tempTicks / TICKS_PER_SECOND).toInt()
		tempTicks -= remainingSeconds * TICKS_PER_SECOND

		val remainingTicks = tempTicks

		val formattedTime = Format.entries
			.filter { views.contains(it) }
			.joinToString(":") { timeFormat ->
				when (timeFormat) {
					TICKS -> "$remainingTicks".padStart(2, '0')
					SECONDS -> "$remainingSeconds".padStart(2, '0')
					MINUTES -> "$remainingMinutes".padStart(2, '0')
					HOURS -> "$remainingHours".padStart(2, '0')
					DAYS -> "$remainingDays".padStart(3, '0')
					YEARS -> "$remainingYears".padStart(3, '0')
				}
			}

		return formattedTime.removeSuffix(":")
	}

	private companion object {

		const val TICKS_PER_SECOND = 20
		const val TICKS_PER_MINUTE = TICKS_PER_SECOND * 60
		const val TICKS_PER_HOUR = TICKS_PER_MINUTE * 60
		const val TICKS_PER_DAY = TICKS_PER_HOUR * 24
		const val TICKS_PER_YEAR = TICKS_PER_DAY * 365

	}

	/**
	 * The Format class represents time formats.
	 *
	 * @since 2023.1
	 * @author Fruxz
	 */
	enum class Format {
		TICKS, SECONDS, MINUTES, HOURS, DAYS, YEARS;

		fun ticks(value: Double) = when (this) {
			TICKS -> value
			SECONDS -> value * TICKS_PER_SECOND
			MINUTES -> value * TICKS_PER_MINUTE
			HOURS -> value * TICKS_PER_HOUR
			DAYS -> value * TICKS_PER_DAY
			YEARS -> value * TICKS_PER_YEAR
		}
	}

}