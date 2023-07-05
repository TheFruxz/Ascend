package dev.fruxz.ascend.extension.time

import kotlin.time.Duration

/**
 * Takes the [Duration.inWholeMilliseconds] and divides it by 50,
 * to represent the duration in minecraft-ticks (20 ticks in a second).
 * @author Fruxz
 * @since 2023.1
 */
val Duration.inWholeMinecraftTicks: Long
	get() = this.inWholeMilliseconds / 50