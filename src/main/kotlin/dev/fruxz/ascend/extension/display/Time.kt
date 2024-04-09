package dev.fruxz.ascend.extension.display

import dev.fruxz.ascend.tool.time.clock.TimeDisplay
import kotlin.time.Duration

/**
 * This function creates a [TimeDisplay] from this [Duration].
 * @return [TimeDisplay]
 * @author Fruxz
 * @since 2023.1
 */
fun Duration.display() = TimeDisplay(this)