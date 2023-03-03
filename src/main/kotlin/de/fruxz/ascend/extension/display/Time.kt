package de.fruxz.ascend.extension.display

import de.fruxz.ascend.tool.time.clock.DisplayTime
import de.fruxz.ascend.tool.time.clock.DisplayTime.Format.SECONDS
import kotlin.time.Duration

/**
 * This function creates a [DisplayTime] from a [Duration] using its [Duration.inWholeSeconds] value.
 * @return [DisplayTime]
 * @author Fruxz
 * @since 1.0
 */
fun Duration.display() = DisplayTime(SECONDS, this.inWholeSeconds.toDouble())