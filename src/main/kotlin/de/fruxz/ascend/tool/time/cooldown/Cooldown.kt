package de.fruxz.ascend.tool.time.cooldown

import de.fruxz.ascend.tool.time.calendar.Calendar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration

data class Cooldown(val duration: Duration, var running: Boolean = false, var onFinish: List<CooldownDestination>, var onHeartbeat: List<CooldownHeartbeat>, val heartBeatDuration: Duration, val beatOnRemainingTime: Boolean, val beatOnLaunch: Boolean) : PassiveCooldown(Calendar.INFINITE_FUTURE) {

	companion object {

		inline fun create(duration: Duration, onFinish: List<CooldownDestination> = emptyList(), onHeartbeat: List<CooldownHeartbeat> = emptyList(), heartBeatDuration: Duration = Duration.ZERO, beatOnRemainingTime: Boolean = true, beatOnLaunch: Boolean = true, builder: Cooldown.() -> Unit = { }) =
			Cooldown(duration, false, onFinish, onHeartbeat, heartBeatDuration, beatOnRemainingTime, beatOnLaunch).apply(builder)

		inline fun launch(duration: Duration, onFinish: List<CooldownDestination> = emptyList(), onHeartbeat: List<CooldownHeartbeat> = emptyList(), heartBeatDuration: Duration = Duration.ZERO, beatOnRemainingTime: Boolean = true, beatOnLaunch: Boolean = true,  builder: Cooldown.() -> Unit = { }) =
			create(duration, onFinish, onHeartbeat, heartBeatDuration, beatOnRemainingTime, beatOnLaunch, builder).launchNative()

		inline fun create(destination: Calendar, onFinish: List<CooldownDestination> = emptyList(), onHeartbeat: List<CooldownHeartbeat> = emptyList(), heartBeatDuration: Duration = Duration.ZERO, beatOnRemainingTime: Boolean = true, beatOnLaunch: Boolean = true,  builder: Cooldown.() -> Unit = { }) =
			Cooldown(destination.durationFromNow(), false, onFinish, onHeartbeat, heartBeatDuration, beatOnRemainingTime, beatOnLaunch).apply(builder)

		inline fun launch(destination: Calendar, onFinish: List<CooldownDestination> = emptyList(), onHeartbeat: List<CooldownHeartbeat> = emptyList(), heartBeatDuration: Duration = Duration.ZERO, beatOnRemainingTime: Boolean = true, beatOnLaunch: Boolean = true,  builder: Cooldown.() -> Unit = { }) =
			create(destination, onFinish, onHeartbeat, heartBeatDuration, beatOnRemainingTime, beatOnLaunch, builder).launchNative()

	}

	@Throws(IllegalStateException::class)
	fun launchNative(coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)) {
		if (running) throw IllegalStateException("This cooldown is already running!")

		running = true

		coroutineScope.launch {
			destination = Calendar.now() + duration

			if (beatOnLaunch) onHeartbeat.forEach { onHeartbeat -> onHeartbeat.onBeat() }

			if (heartBeatDuration.isPositive()) {
				var currentDuration = Duration.ZERO
				var runningLoop = true

				while (runningLoop) {

					if (duration > (currentDuration + heartBeatDuration)) {
						delay(heartBeatDuration)
						currentDuration += heartBeatDuration
						onHeartbeat.forEach { onHeartbeat -> onHeartbeat.onBeat() }
					} else {
						(duration - currentDuration).let { computedDuration ->
							delay(computedDuration)
							currentDuration += computedDuration
							runningLoop = false
							if (beatOnRemainingTime) onHeartbeat.forEach { onHeartbeat -> onHeartbeat.onBeat() }
						}
					}

				}

			} else {
				delay(duration)
			}

			onFinish.forEach { onFinish -> onFinish.onFinish() }

			running = false
			cancel()
		}

	}

	fun attachOnFinish(process: () -> Unit) {
		onFinish += CooldownDestination(process)
	}

	fun attachOnHeartbeat(process: () -> Unit) {
		onHeartbeat += CooldownHeartbeat(process)
	}

	fun interface CooldownDestination {
		suspend fun onFinish()
	}

	fun interface CooldownHeartbeat {
		suspend fun onBeat()
	}

	// player.cooldown(key: Key, duration/calendar/...)

	// executiveCooldown(key: Key, run: () -> Unit)

	// ✅ splitted time? (refreshable) with code, that executes every time a 'heartbeat' occurs

	// Interchanges -> Cooldown

}
