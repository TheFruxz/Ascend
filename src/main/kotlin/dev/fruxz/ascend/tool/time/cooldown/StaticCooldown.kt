package dev.fruxz.ascend.tool.time.cooldown

import dev.fruxz.ascend.annotation.RefactoringCandidate
import dev.fruxz.ascend.tool.time.calendar.Calendar
import dev.fruxz.ascend.tool.time.state.Kalendar
import kotlin.time.Duration

@RefactoringCandidate
object StaticCooldown {

	var cooldownStats: Map<Any, Kalendar> = emptyMap()

	fun getCooldown(key: Any): Kalendar? {
		return cooldownStats[key]?.takeIf { it.inFuture }
	}

	fun setCooldown(key: Any, cooldown: Kalendar) {
		cooldownStats += key to cooldown
	}

	fun removeCooldown(key: Any) {
		cooldownStats -= key
	}

	fun hasCooldown(key: Any): Boolean =
		cooldownStats[key]?.inPast == false

	fun isExpired(key: Any): Boolean =
		cooldownStats[key]?.inPast ?: true

	fun extend(key: Any, time: Duration) {
		cooldownStats += key to ((cooldownStats[key] ?: Kalendar.now()) + time)
	}

	fun decrease(key: Any, time: Duration) {
		cooldownStats += key to ((cooldownStats[key] ?: Kalendar.now()) - time)
	}

	fun clean() {
		cooldownStats = cooldownStats.filterNot { it.value.inPast }
	}

}