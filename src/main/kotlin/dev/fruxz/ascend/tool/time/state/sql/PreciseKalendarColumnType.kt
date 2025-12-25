package dev.fruxz.ascend.tool.time.state.sql

import dev.fruxz.ascend.tool.time.state.Kalendar
import kotlinx.datetime.TimeZone
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ColumnType
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.vendors.currentDialect

data class PreciseKalendarColumnType(
    val timeZone: TimeZone,
) : ColumnType<Kalendar>() {

    override fun sqlType() = currentDialect.dataTypeProvider.longType()

    override fun valueFromDB(value: Any): Kalendar {
        return when (value) {
            is Kalendar -> value
            is Long -> Kalendar.from(
                milliseconds = value,
                timeZone = timeZone,
            )
            is String -> Kalendar.from(
                milliseconds = value.toLong(),
                timeZone = timeZone,
            )
            else -> error("Invalid value type ${value::class.simpleName}/${value.javaClass.name}")
        }
    }

    override fun notNullValueToDB(value: Kalendar) = value.timeInMilliseconds

}