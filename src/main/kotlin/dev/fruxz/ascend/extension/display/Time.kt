package dev.fruxz.ascend.extension.display

import dev.fruxz.ascend.tool.time.clock.DisplayTime
import dev.fruxz.ascend.tool.time.clock.DisplayTime.Format.SECONDS
import kotlin.time.Duration

/**
 * This function creates a [DisplayTime] from a [Duration] using its [Duration.inWholeSeconds] value.
 * @return [DisplayTime]
 * @author Fruxz
 * @since 1.0
 */
fun Duration.display() = DisplayTime(SECONDS, this.inWholeSeconds.toDouble())