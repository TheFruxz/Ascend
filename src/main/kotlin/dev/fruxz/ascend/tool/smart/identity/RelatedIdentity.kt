package dev.fruxz.ascend.tool.smart.identity

import kotlinx.serialization.Serializable

/**
 * A value class to mark something an identity of something identifiable.
 * The relation of the identifiable is represented with [O].
 * @author Fruxz
 * @since 2023.5
 */
@JvmInline
@Serializable
value class RelatedIdentity<O, T>(override val value: T) : IdentitySchematic<T> {

    fun <E> ofType() = RelatedIdentity<E, T>(value = value)

    companion object {

        fun <O, T> of(value: T) = RelatedIdentity<O, T>(value = value)

    }

}