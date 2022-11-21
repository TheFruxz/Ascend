package de.fruxz.ascend.tool.timing.clock

import de.fruxz.ascend.tool.timing.clock.DisplayTime.Format.*
import kotlinx.serialization.Serializable
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.time.Duration

/**
 * This data class helps to easily create a displayable
 * Time-String, which can be set up to display the data
 * you want to display it.
 * @param ticks the amount of ticks used in this time
 */
@Serializable
data class DisplayTime(
	var ticks: Double
) {

	constructor(format: Format, timeValue: Double) : this(format.ticks(timeValue))

	constructor(duration: Duration) : this(duration.inWholeSeconds.toDouble() * 20)

	/**
	 * Generates a displayable string, but only contains
	 * the values of the time units of [views].
	 * @param views the displaying time units
	 * @return the view-string
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getClockDisplay(vararg views: Format): String {
		var out = ""

		val remainingYears = floor(ticks / (20 * 60 * 60 * 24 * 365)).roundToInt()
		val remainingDays = floor(ticks / (20 * 60 * 60 * 24)).roundToInt() - (remainingYears * 365)
		val remainingHours = floor(ticks / (20 * 60 * 60)).roundToInt() - (remainingYears * 24*365) - (remainingDays * 24)
		val remainingMinutes = floor(ticks / (20 * 60)).roundToInt() - (remainingYears * 60*24*365) - (remainingDays * 60*24) - (remainingHours * 60)
		val remainingSeconds = floor(ticks / 20).roundToInt() - (remainingYears * 60*60*24*365) - (remainingDays * 60*60*24) - (remainingHours * 60*60) - (remainingMinutes * 60)
		val remainingTicks = ticks - (remainingYears * 20*60*60*24*365) - (remainingDays * 20*60*60*24) - (remainingHours * 20*60*60) - (remainingMinutes * 20*60) - (remainingSeconds * 20)

		Format.values()
			.filter { views.contains(it) }
			.forEach { timeFormat ->

				out = when (timeFormat) {

					TICKS -> "$remainingTicks".padStart(2, '0')
					SECONDS -> "$remainingSeconds".padStart(2, '0')
					MINUTES -> "$remainingMinutes".padStart(2, '0')
					HOURS -> "$remainingHours".padStart(2, '0')
					DAYS -> "$remainingDays".padStart(3, '0')
					YEARS -> "$remainingYears".padStart(3, '0')

				} + ":" + out

			}

		return out.removeSuffix(":")
	}

	/**
	 * The time formats
	 * ***CALENDAR UNITS IN FUTURE***
	 * @author Fruxz
	 * @since 1.0
	 */
	enum class Format {
		TICKS, SECONDS, MINUTES, HOURS, DAYS, YEARS;

		fun ticks(value: Double) = when (this) {
			TICKS -> value
			SECONDS -> value*20
			MINUTES -> value*60*20
			HOURS -> value*60*60*20
			DAYS -> value*24*60*60*20
			YEARS -> value*365*24*60*60*20
		}

	}

}