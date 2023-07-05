package dev.fruxz.ascend.tool.smart.path

import dev.fruxz.ascend.tool.smart.identification.Identifiable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This class represents an address (inside a path-structure).
 * @param addressString the address as string
 * @author Fruxz
 * @since 2023.1
 */
@Serializable
@SerialName("smartAddress")
data class Address<T> internal constructor(
    @SerialName("path") override val addressString: String,
    @SerialName("divider") val divider: String,
) : Addressable<T>, Identifiable<T> {

    override val identity = addressString

    /**
     * Returns the [addressString]
     * @return the [addressString]
     * @author Fruxz
     * @since 2023.1
     */
    override fun toString() = addressString

    override val addressObject: Address<T>
        get() = address(addressString)

    operator fun div(addition: String) = copy(addressString = "$addressString$divider$addition")

    companion object {

        /**
         * Generates an [Addressed]<[T]> from the [Address]'s class
         * internal constructor.
         * @param path the path as a string
         * @param divider the divider as a string
         * @return the [Address]<[T]>
         * @author Fruxz
         * @since 2023.1
         */
        @JvmStatic
        fun <T> address(path: String, divider: String = "/") =
            Address<T>(path, divider)

        /**
         * Generates an [Address]<[T]> from the [Address]'s class internal constructor,
         * like the [addressObject] companion function, but uses a '.' as the divider.
         * @param path the path as a string
         * @param divider the divider as a string
         * @return the [Address]<[T]>
         * @author Fruxz
         * @since 2023.1
         */
        @JvmStatic
        @Deprecated(
            "Use address() directly", ReplaceWith(
                "address<T>(path = path, divider = divider)",
                "dev.fruxz.ascend.tool.smart.path.Address.Companion.address"
            )
        )
        fun <T> packagedAddress(path: String, divider: String = ".") =
            address<T>(path = path, divider = divider)

    }

}