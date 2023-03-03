package de.fruxz.ascend.tool.time.calendar

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.currentDialect
import java.util.TimeZone

/**
 * This column type defines a column, which stores a [Calendar] object,
 * but does not use the JSON system, like before, but now really uses
 * the columnType `time`, which is supported by most databases.
 * The [timeZone] is defined as a property inside this column object,
 * which is by default [TimeZone.getDefault].
 * @author Fruxz
 * @since 1.0
 */
class CalendarColumnType(
    private val timeZone: TimeZone = TimeZone.getDefault(),
) : ColumnType() {

    override fun sqlType(): String =
        currentDialect.dataTypeProvider.timeType()

    override fun valueFromDB(value: Any): Calendar {
        return when (value) {
            is Long -> Calendar(timeInMillis = value, timeZone = timeZone)
            is String -> Calendar(timeInMillis = "$value".toLong(), timeZone = timeZone)
            else -> error("Invalid value type")
        }
    }

    override fun valueToDB(value: Any?): Any {
        return when (value) {
            is Calendar -> value.timeInMilliseconds
            else -> error("Invalid value type")
        }
    }

}

/**
 * This function registers a column using [CalendarColumnType], which
 * allows database native storage of [Calendar] objects.
 * @author Fruxz
 * @since 1.0
 */
fun Table.calendar(name: String, timeZone: TimeZone): Column<Calendar> =
    registerColumn(name, CalendarColumnType(timeZone))