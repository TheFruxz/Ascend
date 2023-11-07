package dev.fruxz.ascend.tool.smart.identity

/**
 * A value class to mark something an identity of something identifiable
 * @author Fruxz
 * @since 2023.5
 */
@JvmInline
value class Identity<T>(override val value: T): IdentitySchematic<T> {

    companion object {

        fun <T> of(value: T) = Identity(value = value)

    }

}