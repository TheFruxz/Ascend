package dev.fruxz.ascend.tool.smart.composition

/**
 * Interface for constructable objects.
 * @author Fruxz
 * @since 2023.1
 */
interface Constructable<T : Any> {

	/**
	 * Construct
	 * @throws IllegalArgumentException if [parameters] is invalid at the target [T]
	 * @author Fruxz
	 * @since 2023.1
	 */
	fun constructor(vararg parameters: Any?): T

}