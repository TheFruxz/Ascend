package dev.fruxz.ascend.tool.smart.path

import dev.fruxz.ascend.tool.smart.identity.RelatedIdentity
import dev.fruxz.ascend.tool.smart.identity.RelatedUniq
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This class represents an address (inside a path-structure).
 * @param addressString the address as string
 * @author Fruxz
 * @since 2023.1
 */
@Serializable
@ConsistentCopyVisibility
@SerialName("smartAddress")
data class Address<T> internal constructor(
    @SerialName("path") override val addressString: String,
    @SerialName("divider") val divider: String,
) : Addressable<T>, RelatedUniq<Address<T>, String> {

    override val identity = RelatedIdentity<Address<T>, String>(addressString)

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

    }

}