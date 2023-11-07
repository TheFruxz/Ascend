package dev.fruxz.ascend.tool.smart.identity


/**
 * An interface that marks an object, which can be identified
 * by a custom identity [T] and is related to an object of type [O].
 * @author Fruxz
 * @since 2023.4
 */
interface RelatedUniq<O, T> : Uniq<T> {

    override val identity: RelatedIdentity<O, T>

    companion object {

        /**
         * Generates a new [RelatedUniq]<[O], [T]> object, which
         * has the [T] [identity] as the identity set.
         * @param identity the identity of the object
         * @param O the type of the object, where the focus is on
         * @param T the type of the object that is identifiable
         * @return the new [RelatedUniq]<[O], [T]> object
         */
        fun <O, T> of(
            identity: T,
            identityObject: RelatedIdentity<O, T> = RelatedIdentity(identity),
        ) = object : RelatedUniq<O, T> {
            override val identity = identityObject
        }

    }

}