package de.fruxz.ascend.tool.timing.cooldown

import de.moltenKt.core.tool.timing.calendar.TimeState

open class PassiveCooldown(var destination: TimeState) {

	val isOver = destination.inPast

}
