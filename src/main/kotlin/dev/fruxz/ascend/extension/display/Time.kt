package dev.fruxz.ascend.extension.display

import dev.fruxz.ascend.tool.time.clock.DigitalTime
import dev.fruxz.ascend.tool.time.clock.DigitalTime.Format.SECONDS
import kotlin.time.Duration

/**
 * This function creates a [DigitalTime] from a [Duration] using its [Duration.inWholeSeconds] value.
 * @return [DigitalTime]
 * @author Fruxz
 * @since 2023.1
 */
fun Duration.display() = DigitalTime(SECONDS, this.inWholeSeconds.toDouble())