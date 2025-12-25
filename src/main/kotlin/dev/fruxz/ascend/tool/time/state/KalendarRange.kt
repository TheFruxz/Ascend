package dev.fruxz.ascend.tool.time.state

import kotlinx.serialization.Serializable

@Serializable
class KalendarRange(
    override val start: Kalendar,
    override val endInclusive: Kalendar,
) : Iterable<Kalendar>, ClosedRange<Kalendar>, Comparable<Kalendar> {

    fun asDuration() = start.durationTo(endInclusive)

    @Suppress("ConvertTwoComparisonsToRangeCheck")
    override fun contains(value: Kalendar): Boolean =
        start <= value && value <= endInclusive

    override fun iterator(): ListIterator<Kalendar> = listOf(start, endInclusive).listIterator()

    /**
     * This function returns, if the [other]-time is before the [start] of the range, or if the [other]-time is after the [endInclusive] of the range. (or inside, then returns 'equals')
     * @param other is the calendar to return
     * @author Fruxz
     * @since 2025.12
     */
    override operator fun compareTo(other: Kalendar): Int = when {
        this.endInclusive < other -> -1
        this.start > other -> 1
        else -> 0
    }

}