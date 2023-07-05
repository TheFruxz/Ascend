package dev.fruxz.ascend.tool.smart.path

/**
 * Interface for objects that can be addressed by a [Address].
 * @author Fruxz
 * @since 2023.1
 */
interface Addressable<T> {

	/**
	 * The address of the object as a [String].
	 * @author Fruxz
	 * @since 2023.1
	 */
	val addressString: String
		get() = addressObject.addressString

	/**
	 * The address of the object as a [Address]
	 * @author Fruxz
	 * @since 2023.1
	 */
	val addressObject: Address<T>

	val isRoot: Boolean
		get() = !addressString.contains(addressObject.divider) || addressString == addressObject.divider

}