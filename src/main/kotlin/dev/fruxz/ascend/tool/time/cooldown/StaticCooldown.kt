package dev.fruxz.ascend.tool.time.cooldown

import dev.fruxz.ascend.annotation.RefactoringCandidate
import dev.fruxz.ascend.tool.time.calendar.Calendar
import kotlin.time.Duration

@RefactoringCandidate
object StaticCooldown {

	var cooldownStats: Map<Any, Calendar> = emptyMap()

	fun getCooldown(key: Any): Calendar? {
		return cooldownStats[key]?.takeIf { !it.isExpired }
	}

	fun setCooldown(key: Any, cooldown: Calendar) {
		cooldownStats += key to cooldown
	}

	fun removeCooldown(key: Any) {
		cooldownStats -= key
	}

	fun hasCooldown(key: Any): Boolean =
		cooldownStats[key]?.isExpired == false

	fun isExpired(key: Any): Boolean =
		cooldownStats[key]?.isExpired ?: true

	fun extend(key: Any, time: Duration) {
		cooldownStats += key to ((cooldownStats[key] ?: Calendar.now()) + time)
	}

	fun decrease(key: Any, time: Duration) {
		cooldownStats += key to ((cooldownStats[key] ?: Calendar.now()) - time)
	}

	fun clean() {
		cooldownStats = cooldownStats.filterNot { it.value.isExpired }
	}

}