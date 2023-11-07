package dev.fruxz.ascend.tool.smart.identity

/**
 * A value class to mark something an identity of something identifiable
 * @author Fruxz
 * @since 2023.5
 */
@JvmInline
value class RelatedIdentity<O, T>(val value: T) {

    fun <E> ofType() = RelatedIdentity<E, T>(value = value)

    companion object {

        fun <O, T> of(value: T) = RelatedIdentity<O, T>(value = value)

    }

}