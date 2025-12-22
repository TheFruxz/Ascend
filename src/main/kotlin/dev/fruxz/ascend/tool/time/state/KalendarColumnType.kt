package dev.fruxz.ascend.tool.time.state

import kotlinx.datetime.toKotlinTimeZone
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ColumnType
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.vendors.currentDialect
import java.time.OffsetDateTime
import kotlin.time.toKotlinInstant

object KalendarColumnType : ColumnType<Kalendar>() {

    override fun sqlType() = currentDialect.dataTypeProvider.timestampWithTimeZoneType()

    override fun valueFromDB(value: Any): Kalendar {
        return when (value) {
            is Kalendar -> value
            is OffsetDateTime -> Kalendar(instant = value.toInstant().toKotlinInstant(), timeZone = value.offset.toKotlinTimeZone()) // default case
            is String -> OffsetDateTime.parse(value).let {
                Kalendar(instant = it.toInstant().toKotlinInstant(), timeZone = it.offset.toKotlinTimeZone())
            }
            else -> error("Invalid value type ${value::class.simpleName}/${value.javaClass.name}")
        }
    }

    override fun notNullValueToDB(value: Kalendar) = value.javaOffset.toString()

    fun Table.kalendar(name: String): Column<Kalendar> =
        registerColumn(name = name, type = KalendarColumnType)

}