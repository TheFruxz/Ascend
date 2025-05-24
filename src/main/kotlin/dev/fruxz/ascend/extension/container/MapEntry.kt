package dev.fruxz.ascend.extension.container

/**
 * Creates a new [Map.Entry] instance with the specified key and value.
 * This function is useful for creating entries without needing to
 * implement the [Map.Entry] interface manually.
 * @param key the key of the entry
 * @param value the value of the entry
 * @return a new [Map.Entry] instance with the specified key and value
 * @author Fruxz
 * @since 2025.5
 */
fun <K, V> entryOf(key: K, value: V): Map.Entry<K, V> = object : Map.Entry<K, V> {
    override val key: K = key
    override val value: V = value

    override fun toString(): String = "$key=$value"

    override fun hashCode(): Int = key.hashCode() xor value.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Map.Entry<*, *>) return false
        return key == other.key && value == other.value
    }

}