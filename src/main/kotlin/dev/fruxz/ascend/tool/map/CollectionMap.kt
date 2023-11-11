package dev.fruxz.ascend.tool.map

import dev.fruxz.ascend.annotation.ExperimentalAscendApi

/**
 * This class represents a map of collections of type [C].
 * @param K the type of keys maintained by this map
 * @param V the type of values in the collection
 * @param C the type of collection
 * @param M the type of map
 * @property data the map of collections
 * @see MutableCollectionMap
 * @author Fruxz
 * @since 2023.5
 */
@ExperimentalAscendApi
abstract class CollectionMap<K, V, C : Collection<V>, M : Map<K, C>>(
    val data: M,
) : Map<K, C> {

    override val entries: Set<Map.Entry<K, C>> by data::entries
    override val keys: Set<K> by data::keys
    override val size: Int by data::size
    override val values: Collection<C> by data::values
    override fun containsKey(key: K): Boolean = data.containsKey(key)
    override fun containsValue(value: C): Boolean = data.containsValue(value)
    override fun get(key: K): C? = data[key]
    override fun isEmpty(): Boolean = data.isEmpty()

    abstract fun toMutable(): MutableCollectionMap<K, V, C>

}
