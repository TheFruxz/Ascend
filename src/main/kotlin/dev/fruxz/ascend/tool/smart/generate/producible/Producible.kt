package dev.fruxz.ascend.tool.smart.generate.producible

/**
 * This interface marks every class/object, that can produce an object type [T]
 * @author Fruxz
 * @since 2023.1
 */
interface Producible<T : Any> {

	/**
	 * This function computes the end result of type [T]
	 * @return the result [T]
	 */
	fun produce(): T

}