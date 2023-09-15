package dev.fruxz.ascend.tool.smart.identity

/**
 * An interface that marks an object, which can
 * be identified by a custom identity [T].
 * @param T the type of the object that identifies this object
 * @author Fruxz
 * @since 2023.4
 */
interface Uniq<T> {

    /**
     * The object's identity, to identify this object.
     */
    val identity: T

    fun matchesIdentity(other: Uniq<T>) = identity == other.identity

    companion object {

        /**
         * Generates a new [Uniq]<[T]> object, which
         * has the [T] [identity] as the identity set.
         * @param identity the identity of the object
         * @param T the type of the object that is identifiable
         * @return the new [Uniq]<[T]> object
         */
        fun <T> of(identity: T) = object : Uniq<T> {
            override val identity = identity
        }

    }

}