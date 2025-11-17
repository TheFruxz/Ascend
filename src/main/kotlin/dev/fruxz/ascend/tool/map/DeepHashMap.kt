package dev.fruxz.ascend.tool.map

import java.util.concurrent.ConcurrentHashMap

/**
 * A thread-safe map that allows for a two-level key structure.
 *
 * @param K the type of the primary keys.
 * @param DK the type of the secondary (deep) keys.
 * @param DV the type of the values.
 */
open class DeepHashMap<K : Any, DK : Any, DV : Any>(
    val map: ConcurrentHashMap<K, ConcurrentHashMap<DK, DV>> = ConcurrentHashMap(),
) : MutableMap<K, ConcurrentHashMap<DK, DV>> by map {

    operator fun get(key: K, deepKey: DK): DV? = this[key]?.get(deepKey)

    operator fun set(key: K, deepKey: DK, value: DV) = put(key, deepKey, value)

    fun put(key: K, deepKey: DK, value: DV): DV? {
        return this.computeIfAbsent(key) { ConcurrentHashMap() }.put(deepKey, value)
    }

}