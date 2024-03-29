package dev.fruxz.ascend.tool.time

/**
 * A time state is only defining, if it is already expired [inPast],
 * and/or if it is [infinite] in past/future.
 * This interface was created, to simply define an infinite future,
 * or an infinite past. This can be used to define something like
 * "this stays forever" or "never happened".
 * @author Fruxz
 * @since 2023.1
 */
interface TimeState {

	val infinite: Boolean

	val inFuture: Boolean

	val inPast: Boolean

	val isNow: Boolean
		get() = !inFuture && !inPast

	companion object {

		@JvmStatic
		val INFINITE_FUTURE = object : TimeState {
			override val inFuture = true
			override val infinite = true
			override val inPast = false
		}

		@JvmStatic
		val INFINITE_PAST = object : TimeState {
			override val inFuture = false
			override val infinite = true
			override val inPast = false
		}

	}

}