package dev.fruxz.ascend.extension.time

import dev.fruxz.ascend.tool.time.state.Kalendar
import kotlinx.datetime.TimeZone
import java.text.DateFormat
import java.util.Locale
import kotlin.time.Clock
import kotlin.time.Instant

fun time(
    instant: Instant = Clock.System.now(),
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Kalendar = Kalendar(instant = instant, timeZone = timeZone)

fun dateTimeFormat(
    dayFormat: Kalendar.FormatStyle = Kalendar.FormatStyle.entries[DateFormat.DEFAULT],
    timeFormat: Kalendar.FormatStyle = Kalendar.FormatStyle.entries[DateFormat.DEFAULT],
    locale: Locale = Locale.getDefault()
) = DateFormat.getDateTimeInstance(dayFormat.ordinal, timeFormat.ordinal, locale)