package de.fruxz.ascend.tool.time.cooldown

import de.fruxz.ascend.tool.time.TimeState

open class PassiveCooldown(var destination: TimeState) {

	val isOver = destination.inPast

}
