package de.fruxz.ascend.json

import kotlinx.serialization.json.JsonPrimitive

fun Boolean.jsonPrimitive() = JsonPrimitive(this)
fun Number.jsonPrimitive() = JsonPrimitive(this)
fun String.jsonPrimitive() = JsonPrimitive(this)