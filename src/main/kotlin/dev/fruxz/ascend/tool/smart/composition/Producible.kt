package dev.fruxz.ascend.tool.smart.composition

/**
 * This interface marks every class/object, that can produce an object type [T]
 * @author Fruxz
 * @since 2023.1
 */
interface Producible<T : Any> {

	/**
	 * Produces the product [T]
	 * @return the product [T]
	 * @author Fruxz
	 * @since 2023.1
	 */
	fun produce(): T

}