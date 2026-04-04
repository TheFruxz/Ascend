package dev.fruxz.ascend.tool.time.state

import dev.fruxz.ascend.tool.time.state.sql.KalendarColumnType
import dev.fruxz.ascend.tool.time.state.sql.PreciseKalendarColumnType
import kotlinx.datetime.TimeZone
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.Table.Dual.clientDefault
import org.jetbrains.exposed.v1.datetime.timestamp

/**
 * Saves the [Kalendar] timestamp in milliseconds instead of the [kalendarTimestamp] seconds.
 * Rendering this column to be more precise, but [kalendarTimestamp] is recommended, for
 * the better compatibility and ease of use. Only use this, if you need the precision, and your database supports it.
 */
fun Table.kalendarPrecise(
    name: String,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): Column<Kalendar> = registerColumn(name = name, type = PreciseKalendarColumnType(timeZone))

/**
 * Recommended use of [Kalendar]s. utilizes [timestamp] under the hood, and transforms it to a [Kalendar] using the provided [timeZone]. This is more compatible with databases.
 */
fun Table.kalendarTimestamp(name: String, timeZone: TimeZone = TimeZone.currentSystemDefault()): Column<Kalendar> =
    timestamp(name = name).transform(wrap = { Kalendar(instant = it, timeZone = timeZone) }, unwrap = { it.instant })

/**
 * Only supported by a few databases, and not recommended, because of the timezone issues. Use [kalendarTimestamp] instead.
 */
fun Table.kalendarWithTimeZone(name: String) =
    registerColumn(name = name, type = KalendarColumnType)

@Deprecated(message = "Not recommended, because of the lack of support in databases. Please consider kalendarTimestamp, exact drop-in replacement would be kalendarWithTimeZone()", replaceWith = ReplaceWith("kalendarWithTimeZone(name)"))
fun Table.kalendar(name: String): Column<Kalendar> =
    kalendarWithTimeZone(name = name)

/**
 * Sets the (client) default value of this [Kalendar] column to the current time.
 * @author Fruxz
 * @since 2024.6
 */
fun Column<Kalendar>.defaultNow() = this.clientDefault { Kalendar.now() }