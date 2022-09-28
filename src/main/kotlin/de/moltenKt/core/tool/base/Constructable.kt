package de.moltenKt.core.tool.base

/**
 * Interface for constructable objects.
 * @author Fruxz
 * @since 1.0
 */
interface Constructable<T : Any> {

	/**
	 * Construct
	 * @throws IllegalArgumentException if [parameters] is invalid at the target [T]
	 * @author Fruxz
	 * @since 1.0
	 */
	fun constructor(vararg parameters: Any?): T

}