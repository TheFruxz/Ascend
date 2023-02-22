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
 * This function converts [this] object to a json string via the [jsonBase]
 * and [Json.encodeToString] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.encodeToString
 * @return The object represented as a JSON string.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonString(json: Json = jsonBase) = json.encodeToString(this)

/**
 * This function tries to return the result of executing the [toJsonString] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see toJsonString
 * @return The object represented as a JSON string, or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonStringOrNull(json: Json = jsonBase) = tryOrNull { toJsonString(json = json) }

/**
 * This function converts [this] object as a json stream into the [stream] via the
 * [jsonBase] and [Json.encodeToStream] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.encodeToStream
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonStream(stream: OutputStream, json: Json = jsonBase) = json.encodeToStream(this, stream)

/**
 * This function tries to return the result of executing the [toJsonStream] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see toJsonStream
 * @return The unit if it succeeded, or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonStreamOrNull(stream: OutputStream, json: Json = jsonBase) = tryOrNull { toJsonStream(stream, json = json) }

/**
 * This function converts [this] object to a [JsonElement] via the [jsonBase]
 * and [Json.encodeToJsonElement] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.encodeToJsonElement
 * @return The object represented as a [JsonElement].
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonElement(json: Json = jsonBase) = json.encodeToJsonElement(this)

/**
 * This function tries to return the result of executing the [toJsonElement] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see toJsonElement
 * @return The object represented as a [JsonElement], or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> T.toJsonElementOrNull(json: Json = jsonBase) = tryOrNull { toJsonElement(json = json) }

/**
 * This function parses this string into a [JsonElement] using the [jsonBase]
 * @author Fruxz
 * @since 1.0
 */
fun String.parseToJsonElement(json: Json = jsonBase) = json.parseToJsonElement(this)

/**
 * This function parses this string into a [JsonElement] using the [jsonBase]
 * or null if it fails
 * @author Fruxz
 * @since 1.0
 */
fun String.parseToJsonElementOrNull(json: Json = jsonBase) = tryOrNull { parseToJsonElement(json = json) }

/**
 * This function parses this string into a [JsonObject] using the
 * [parseToJsonElement] function.
 * @author Fruxz
 * @since 1.0
 */
fun String.parseToJsonObject(json: Json = jsonBase) = parseToJsonElement(json = json).jsonObject

/**
 * This function parses this string into a [JsonObject] using the
 * [parseToJsonElementOrNull] function, or null if it fails.
 * @author Fruxz
 * @since 1.0
 */
fun String.parseToJsonObjectOrNull(json: Json = jsonBase) = tryOrNull { parseToJsonObject(json = json) }

// from json string

/**
 * This function converts [this] JSON string to an object type [T] via the [jsonBase]
 * and [Json.decodeFromString] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.decodeFromString
 * @return The object represented as a [T].
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> String.fromJsonString(json: Json = jsonBase) = json.decodeFromString<T>(this)

/**
 * This function tries to return the result of executing the [fromJsonString] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see fromJsonString
 * @return The object represented as a [T], or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> String.fromJsonStringOrNull(json: Json = jsonBase) = tryOrNull { fromJsonString<T>(json = json) }

/**
 * This function converts [this] JSON stream to an object type [T] via the [jsonBase]
 * and [Json.decodeFromStream] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.decodeFromStream
 * @return The object represented as a [T].
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> InputStream.fromJsonStream(json: Json = jsonBase) = json.decodeFromStream<T>(this)

/**
 * This function tries to return the result of executing the [fromJsonStream] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see InputStream.fromJsonStream
 * @return The object represented as a [T]
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> InputStream.fromJsonStreamOrNull(json: Json = jsonBase) = tryOrNull { fromJsonStream<T>(json = json) }

/**
 * This function converts [this] [JsonElement] to an object type [T] via the [jsonBase]
 * and [Json.decodeFromJsonElement] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see Json.decodeFromJsonElement
 * @return The object represented as a [T]
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> JsonElement.fromJsonElement(json: Json = jsonBase) = json.decodeFromJsonElement<T>(this)

/**
 * This function tries to return the result of executing the [fromJsonElement] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see JsonElement.fromJsonElement
 * @return The object represented as a [T]
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> JsonElement.fromJsonElementOrNull(json: Json = jsonBase) = tryOrNull { fromJsonElement<T>(json = json) }

/**
 * This function reads the content of [this] Path using the [readText] function and
 * converts the content from a String to the requested [T] object via the [fromJsonStream]
 * function.
 * This process can throw exceptions if something goes wrong!
 * @see Path.readText
 * @see fromJsonString
 * @return The object represented as a [T]
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.fromJsonFile(charset: Charset = Charsets.UTF_8, json: Json = jsonBase) = readText(charset).fromJsonString<T>(json = json)

/**
 * This function tries to return the result of executing the [fromJsonFile] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see Path.fromJsonFile
 * @return The object represented as a [T], or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.fromJsonFileOrNull(charset: Charset = Charsets.UTF_8, json: Json = jsonBase) = tryOrNull { fromJsonFile<T>(charset, json = json) }

/**
 * This function reads the content of [this] File using the [readText] function and
 * converts the content from a String to the requested [T] object via the [fromJsonString].
 * This process can throw exceptions if something goes wrong!
 * @see File.readText
 * @see fromJsonString
 * @return The object represented as a [T]
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.fromJsonFile(charset: Charset = Charsets.UTF_8, json: Json = jsonBase) = readText(charset).fromJsonString<T>(json = json)

/**
 * This function tries to return the result of executing the [fromJsonFile] function,
 * but if it fails, it does return null, because of the utilization of the [tryOrNull].
 * @see File.fromJsonFile
 * @return The object represented as a [T], or null if it failed.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.fromJsonFileOrNull(charset: Charset = Charsets.UTF_8, json: Json = jsonBase) = tryOrNull { fromJsonFile<T>(charset, json = json) }
