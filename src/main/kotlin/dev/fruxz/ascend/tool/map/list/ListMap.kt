package dev.fruxz.ascend.tool.map.list

import dev.fruxz.ascend.tool.map.CollectionMap

/**
 * This class represents a map of [List]s.
 * @param K the type of keys maintained by this map
 * @param V the type of values in the [List]s
 * @property data the map of [List]s
 * @see CollectionMap
 * @author Fruxz
 * @since 2023.5
 */
class ListMap<K, V>(data: Map<K, List<V>>) : CollectionMap<K, V, List<V>, Map<K, List<V>>>(data) {

    override fun toMutable(): MutableListMap<K, V> = MutableListMap(data.toMutableMap())

}