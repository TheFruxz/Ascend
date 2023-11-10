package dev.fruxz.ascend.tool.map.set

import dev.fruxz.ascend.annotation.ExperimentalAscendApi
import dev.fruxz.ascend.tool.map.MutableCollectionMap

@ExperimentalAscendApi
class MutableSetMap<K, V>(data: MutableMap<K, Set<V>> = mutableMapOf()) : MutableCollectionMap<K, V, Set<V>>(data) {

    override fun addEntry(key: K, vararg value: V) {
        this.data[key] = this.data[key].orEmpty().toSet() + value
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

    override fun toMutable(): MutableSetMap<K, V> = clone(this.data)

    fun clone(data: Map<K, Set<V>>): MutableSetMap<K, V> = MutableSetMap(data.toMutableMap())

}