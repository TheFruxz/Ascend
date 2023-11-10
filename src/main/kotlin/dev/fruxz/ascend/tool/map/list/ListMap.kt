package dev.fruxz.ascend.tool.map.list

import dev.fruxz.ascend.annotation.ExperimentalAscendApi
import dev.fruxz.ascend.tool.map.CollectionMap

@ExperimentalAscendApi
class ListMap<K, V>(data: Map<K, List<V>>) : CollectionMap<K, V, List<V>, Map<K, List<V>>>(data) {

    override fun toMutable(): MutableListMap<K, V> = MutableListMap(data.toMutableMap())

}