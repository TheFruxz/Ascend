package dev.fruxz.ascend.tool.map.set

import dev.fruxz.ascend.annotation.ExperimentalAscendApi
import dev.fruxz.ascend.tool.map.CollectionMap
import dev.fruxz.ascend.tool.map.MutableCollectionMap

/**
 * This class represents a map of [Set]s.
 * @param K the type of keys maintained by this map
 * @param V the type of values in the [Set]s
 * @property data the map of [Set]s
 * @see CollectionMap
 * @author Fruxz
 * @since 2023.5
 */
@ExperimentalAscendApi
class SetMap<K, V>(data: Map<K, Set<V>>) : CollectionMap<K, V, Set<V>, Map<K, Set<V>>>(data) {

    override fun toMutable(): MutableCollectionMap<K, V, Set<V>> = MutableSetMap(data.toMutableMap())

}