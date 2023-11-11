package dev.fruxz.ascend.tool.map.list

import dev.fruxz.ascend.annotation.ExperimentalAscendApi
import dev.fruxz.ascend.tool.map.MutableCollectionMap

/**
 * This class represents a mutable map of [List]s.
 * @param K the type of keys maintained by this map
 * @param V the type of values in the [List]s
 * @property data the map of [List]s
 * @see MutableCollectionMap
 * @author Fruxz
 * @since 2023.5
 */
@ExperimentalAscendApi
class MutableListMap<K, V>(data: MutableMap<K, List<V>> = mutableMapOf()) : MutableCollectionMap<K, V, List<V>>(data) {

    override fun addEntry(key: K, vararg value: V) {
        this.data[key] = this.data[key].orEmpty() + value
    }

    override fun addAllEntries(key: K, values: Iterable<V>) {
        this.data[key] = this.data[key].orEmpty() + values
    }

    override fun removeEntry(key: K, vararg value: V) {
        this.data[key] = this.data[key].orEmpty() - value.toSet()
    }

    override fun removeAllEntries(key: K, values: Iterable<V>) {
        this.data[key] = this.data[key].orEmpty() - values.toSet()
    }

    override fun toMutable(): MutableListMap<K, V> = clone(this.data)

    fun clone(data: Map<K, List<V>>): MutableListMap<K, V> = MutableListMap(data.toMutableMap())

}