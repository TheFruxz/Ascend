package de.jet.jvm.tool.smart.positioning

/**
 * Interface for objects that can be addressed by a [Address].
 * @author Fruxz
 * @since 1.0
 */
interface Addressable<T> {

	/**
	 * The address of the object as a [String].
	 * @author Fruxz
	 * @since 1.0
	 */
	val addressString: String
		get() = address.addressString

	/**
	 * The addres of the object as a [Address]
	 */
	val address: Address<T>

}