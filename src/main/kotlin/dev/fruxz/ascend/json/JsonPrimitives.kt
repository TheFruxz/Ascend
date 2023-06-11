package dev.fruxz.ascend.json

import kotlinx.serialization.json.JsonPrimitive

/**
 * This computational value creates a new [JsonPrimitive]
 * with this [Boolean] as its value.
 * @author Fruxz
 * @since 1.0
 */
val Boolean.asJsonPrimitive
    get() = JsonPrimitive(this)

/**
 * This computational value creates a new [JsonPrimitive]
 * with this [Number] as its value.
 * @author Fruxz
 * @since 1.0
 */
val Number.asJsonPrimitive
    get() = JsonPrimitive(this)

/**
 * This computational value creates a new [JsonPrimitive]
 * with this [String] as its value.
 * @author Fruxz
 * @since 1.0
 */
val String.asJsonPrimitive
    get() = JsonPrimitive(this)