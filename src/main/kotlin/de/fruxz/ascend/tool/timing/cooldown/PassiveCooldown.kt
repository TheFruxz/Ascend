package de.fruxz.ascend.tool.timing.cooldown

import de.fruxz.ascend.tool.timing.calendar.TimeState

open class PassiveCooldown(var destination: TimeState) {

	val isOver = destination.inPast

}
