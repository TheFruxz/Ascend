package de.fruxz.ascend.tool.time

/**
 * A time state is only defining, if it is already expired [inPast],
 * and/or if it is [infinite] in past/future.
 * This interface was created, to simply define an infinite future,
 * or an infinite past. This can be used to define something like
 * "this stays forever" or "never happened".
 * @author Fruxz
 * @since 1.0
 */
interface TimeState {

	val infinite: Boolean

	val inFuture: Boolean

	val inPast: Boolean

	val isNow: Boolean
		get() = !inFuture && !inPast

}