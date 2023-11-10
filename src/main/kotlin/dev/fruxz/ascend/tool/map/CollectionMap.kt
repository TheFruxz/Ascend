package dev.fruxz.ascend.tool.map

import dev.fruxz.ascend.annotation.ExperimentalAscendApi

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
