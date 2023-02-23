package de.fruxz.ascend.json

import de.fruxz.ascend.extension.tryOrNull
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.readText

// to json string

/**
 * This function converts [this] object to a json string via the [globalJson]
 * and [Json.encodeToString] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.encodeToString
 * @return The object represented as a JSON string.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonString(json: Json = globalJson) = json.encodeToString(this)

/**
 * This function tries to return the result of executing the [toJsonString] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see toJsonString
 * @return The object represented as a JSON string, or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonStringOrNull(json: Json = globalJson) = tryOrNull { toJsonString(json = json) }

/**
 * This function converts [this] object as a json stream into the [stream] via the
 * [globalJson] and [Json.encodeToStream] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.encodeToStream
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonStream(stream: OutputStream, json: Json = globalJson) = json.encodeToStream(this, stream)

/**
 * This function tries to return the result of executing the [toJsonStream] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see toJsonStream
 * @return The unit if it succeeded, or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonStreamOrNull(stream: OutputStream, json: Json = globalJson) = tryOrNull { toJsonStream(stream, json = json) }

/**
 * This function converts [this] object to a [JsonElement] via the [globalJson]
 * and [Json.encodeToJsonElement] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.encodeToJsonElement
 * @return The object represented as a [JsonElement].
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonElement(json: Json = globalJson) = json.encodeToJsonElement(this)

/**
 * This function tries to return the result of executing the [toJsonElement] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see toJsonElement
 * @return The object represented as a [JsonElement], or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonElementOrNull(json: Json = globalJson) = tryOrNull { toJsonElement(json = json) }

/**
 * This function parses this string into a [JsonElement] using the [globalJson]
 * @author Fruxz
 * @since 1.0
 */
fun String.parseToJsonElement(json: Json = globalJson) = json.parseToJsonElement(this)

/**
 * This function parses this string into a [JsonElement] using the [globalJson]
 * or null if it fails
 * @author Fruxz
 * @since 1.0
 */
fun String.parseToJsonElementOrNull(json: Json = globalJson) = tryOrNull { parseToJsonElement(json = json) }

/**
 * This function parses this string into a [JsonObject] using the
 * [parseToJsonElement] function.
 * @author Fruxz
 * @since 1.0
 */
fun String.parseToJsonObject(json: Json = globalJson) = parseToJsonElement(json = json).jsonObject

/**
 * This function parses this string into a [JsonObject] using the
 * [parseToJsonElementOrNull] function, or null if it fails.
 * @author Fruxz
 * @since 1.0
 */
fun String.parseToJsonObjectOrNull(json: Json = globalJson) = tryOrNull { parseToJsonObject(json = json) }

// from json string

/**
 * This function converts [this] JSON string to an object type [T] via the [globalJson]
 * and [Json.decodeFromString] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.decodeFromString
 * @return The object represented as a [T].
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> String.fromJsonString(json: Json = globalJson) = json.decodeFromString<T>(this)

/**
 * This function tries to return the result of executing the [fromJsonString] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see fromJsonString
 * @return The object represented as a [T], or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> String.fromJsonStringOrNull(json: Json = globalJson) = tryOrNull { fromJsonString<T>(json = json) }

/**
 * This function converts [this] JSON stream to an object type [T] via the [globalJson]
 * and [Json.decodeFromStream] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.decodeFromStream
 * @return The object represented as a [T].
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> InputStream.fromJsonStream(json: Json = globalJson) = json.decodeFromStream<T>(this)

/**
 * This function tries to return the result of executing the [fromJsonStream] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see InputStream.fromJsonStream
 * @return The object represented as a [T]
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> InputStream.fromJsonStreamOrNull(json: Json = globalJson) = tryOrNull { fromJsonStream<T>(json = json) }

/**
 * This function converts [this] [JsonElement] to an object type [T] via the [globalJson]
 * and [Json.decodeFromJsonElement] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.decodeFromJsonElement
 * @return The object represented as a [T]
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> JsonElement.fromJsonElement(json: Json = globalJson) = json.decodeFromJsonElement<T>(this)

/**
 * This function tries to return the result of executing the [fromJsonElement] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see JsonElement.fromJsonElement
 * @return The object represented as a [T]
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> JsonElement.fromJsonElementOrNull(json: Json = globalJson) = tryOrNull { fromJsonElement<T>(json = json) }
