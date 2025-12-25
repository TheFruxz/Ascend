package dev.fruxz.ascend.tool.time.state

operator fun Kalendar.rangeTo(other: Kalendar) =
    KalendarRange(start = this, endInclusive = other)