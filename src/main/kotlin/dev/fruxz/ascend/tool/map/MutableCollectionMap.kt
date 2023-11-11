package dev.fruxz.ascend.tool.map

/**
 * This class represents a mutable map of collections of type [C].
 * @param K the type of keys maintained by this map
 * @param V the type of values in the collection
 * @param C the type of collection
 * @property data the map of collections
 * @see CollectionMap
 * @author Fruxz
 * @since 2023.5
 */
abstract class MutableCollectionMap<K, V, C : Collection<V>> internal constructor(data: MutableMap<K, C>) : CollectionMap<K, V, C, MutableMap<K, C>>(data), MutableMap<K, C> {

    override val entries: MutableSet<MutableMap.MutableEntry<K, C>> by data::entries
    override val keys: MutableSet<K> by data::keys
    override val size: Int by data::size
    override val values: MutableCollection<C> by data::values
    override fun clear() = data.clear()
    override fun isEmpty(): Boolean = data.isEmpty()
    override fun remove(key: K): C? = data.remove(key)
    override fun putAll(from: Map<out K, C>) = data.putAll(from)
    override fun put(key: K, value: C): C? = data.put(key, value)
    override fun get(key: K): C? = data[key]
    override fun containsValue(value: C): Boolean = data.containsValue(value)
    override fun containsKey(key: K): Boolean = data.containsKey(key)

    // Special functions

    abstract fun addEntry(key: K, vararg value: V)
    abstract fun addAllEntries(key: K, values: Iterable<V>)
    abstract fun removeEntry(key: K, vararg value: V)
    abstract fun removeAllEntries(key: K, values: Iterable<V>)

    override fun toString(): String {
        return data.toString()
    }

}