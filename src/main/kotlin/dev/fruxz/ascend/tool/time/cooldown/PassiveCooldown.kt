package dev.fruxz.ascend.tool.time.cooldown

import dev.fruxz.ascend.annotation.RefactoringCandidate
import dev.fruxz.ascend.tool.time.TimeState

@RefactoringCandidate
open class PassiveCooldown(var destination: TimeState) {

	val isOver = destination.inPast

}
