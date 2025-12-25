package dev.fruxz.ascend.tool.time.state

import dev.fruxz.ascend.tool.time.state.sql.KalendarColumnType
import dev.fruxz.ascend.tool.time.state.sql.PreciseKalendarColumnType
import kotlinx.datetime.TimeZone
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Table

fun Table.kalendarPrecise(
    name: String,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): Column<Kalendar> = registerColumn(name = name, type = PreciseKalendarColumnType(timeZone))

fun Table.kalendar(name: String): Column<Kalendar> =
    registerColumn(name = name, type = KalendarColumnType)