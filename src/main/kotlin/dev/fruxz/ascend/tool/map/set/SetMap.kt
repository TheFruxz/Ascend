package dev.fruxz.ascend.tool.map.set

import dev.fruxz.ascend.annotation.ExperimentalAscendApi
import dev.fruxz.ascend.tool.map.CollectionMap
import dev.fruxz.ascend.tool.map.MutableCollectionMap

@ExperimentalAscendApi
class SetMap<K, V>(data: Map<K, Set<V>>) : CollectionMap<K, V, Set<V>, Map<K, Set<V>>>(data) {

    override fun toMutable(): MutableCollectionMap<K, V, Set<V>> = MutableSetMap(data.toMutableMap())

}