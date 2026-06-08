package dev.fruxz.ascend.json

import dev.fruxz.ascend.extension.readTextOrNull
import dev.fruxz.ascend.extension.tryOrNull
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.OpenOption
import java.nio.file.Path
import kotlin.io.inputStream
import kotlin.io.path.*
import kotlin.jvm.Throws

/**
 * This function writes the given [this] object to a JSON file via the [globalJson]
 * and [toJsonStream] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see toJsonStream
 * @see outputStream
 * @return The path itself.
 * @author Fruxz
 * @since 2023.1
 */
@Throws(SerializationException::class, IOException::class)
inline fun <reified T> Path.writeJson(
    content: T,
    json: Json = globalJson,
    vararg options: OpenOption
) = apply {
    content.toJsonStream(
        stream = this.outputStream(options = options),
        json = json
    )
}

/**
 * This function writes the given [this] object to a JSON file via the [globalJson]
 * and [toJsonStream] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see toJsonStream
 * @return The file itself.
 * @author Fruxz
 * @since 2023.1
 */
@Throws(SerializationException::class, IOException::class)
inline fun <reified T> File.writeJson(
    content: T,
    json: Json = globalJson
) = apply {
    content.toJsonStream(
        stream = this.outputStream(),
        json = json
    )
}

/**
 * This function writes the given [content], to the file under [this] path, via the [writeJson]
 * if the file under [this] path currently does not exist.
 * @param content the content to write as json to the file.
 * @param createParent if the parent directories will be created before writing
 * @see writeJson
 * @return the path itself.
 * @author Fruxz
 * @since 2023.1
 */
@Throws(SerializationException::class, IOException::class, SecurityException::class)

inline fun <reified T> Path.writeJsonIfNotExists(content: T, createParent: Boolean = true, json: Json = globalJson, vararg options: OpenOption) = apply {
    if (exists()) return@apply

    if (createParent) parent.createDirectories()
    writeJson(content, json = json, *options)
}

/**
 * This function writes the given [content], to [this] file , via the [writeJson]
 * if the file currently does not exist.
 * @param content the content to write as json to the file
 * @param createParent if the parent directories will be created before writing
 * @see writeJson
 * @return the file itself
 * @author Fruxz
 * @since 2023.1
 */
@Throws(SerializationException::class, IOException::class, SecurityException::class)
inline fun <reified T> File.writeJsonIfNotExists(content: T, createParent: Boolean = true, json: Json = globalJson) = apply {
    if (exists()) return@apply

    if (createParent) parentFile.mkdirs()
    writeJson(content, json = json)
}

/**
 * This function writes the given [content], to the file under [this] path, via the [writeJson]
 * if the file under [this] path currently is empty or does not exist.
 * @param content the content to write as json to the file.
 * @param createParent if the parent directories will be created before writing
 * @see writeJson
 * @return the path itself.
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T> Path.writeJsonIfEmpty(content: T, createParent: Boolean = true, json: Json = globalJson, vararg options: OpenOption) = apply {
    if (this.fileSize() > 0L) return@apply

    if (createParent) parent.createDirectories()
    writeJson(content, json = json, *options)
}

/**
 * This function writes the given [content], to [this] file, via the [writeJson]
 * if [this] file is currently empty or does not exist.
 * @param content the content to write as json to the file.
 * @param createParent if the parent directories will be created before writing
 * @see writeJson
 * @return the file itself.
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T> File.writeJsonIfEmpty(content: T, createParent: Boolean = true, json: Json = globalJson) = apply {
    if (this.length() > 0L) return@apply

    if (createParent) parentFile.mkdirs()
    writeJson(content, json = json)
}

/**
 * This function writes the given [content], to the file under [this] path, via the [writeJson]
 * if the file under [this] path is currently blank or does not exist.
 * @param content the content to write as json to the file.
 * @param createParent if the parent directories will be created before writing
 * @see writeJson
 * @return the path itself
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T> Path.writeJsonIfBlank(content: T, createParent: Boolean = true, json: Json = globalJson, vararg options: OpenOption) = apply {
    if (fileSize() > 0L) return@apply
    if (bufferedReader().useLines { it.any(String::isNotBlank) }) return@apply

    if (createParent) parent.createDirectories()
    writeJson(content, json = json, *options)
}

/**
 * This function writes the given [content], to the file under [this] file, via the [writeJson]
 * if the file under [this] path is currently blank or does not exist.
 * @param content the content to write as json to the file.
 * @param createParent if the parent directories will be created before writing
 * @see writeJson
 * @return the path itself
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T> File.writeJsonIfBlank(content: T, createParent: Boolean = true, json: Json = globalJson) = apply {
    if (length() > 0L) return@apply
    if (bufferedReader().useLines { it.any(String::isNotBlank) }) return@apply

    if (createParent) parentFile.mkdirs()
    writeJson(content, json = json)
}


// input json from file

/**
 * This function returns the content of [this] Path using the [inputStream] and [fromJsonStream] function.
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T> Path.readJson(json: Json = globalJson) = inputStream().fromJsonStream<T>(json = json)

/**
 * This function returns the content of [this] File using the [inputStream] and [fromJsonStreamOrNull] function.
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T> Path.readJsonOrNull(json: Json = globalJson) = inputStream().fromJsonStreamOrNull<T>(json = json)

/**
 * This function reads the file content and parses it to a [JsonElement]
 * using the [Json.decodeFromStream] function.
 * @return the parsed [JsonElement]
 * @author Fruxz
 * @since 2023.1
 */
fun Path.readJsonElement(charset: Charset = Charsets.UTF_8, json: Json = globalJson) = json.decodeFromStream<JsonElement>(this.inputStream())

/**
 * This function reads the file content and parses it to a [JsonElement]
 * using the [Json.decodeFromStream] function.
 * @return the parsed [JsonElement] or null if failed
 * @author Fruxz
 * @since 2023.1
 */
fun Path.readJsonElementOrNull(charset: Charset = Charsets.UTF_8, json: Json = globalJson) = tryOrNull { json.decodeFromStream<JsonElement>(this.inputStream()) }

/**
 * This function reads, parses and converts the file content to an [JsonObject]
 * using the [Json.decodeFromStream] function.
 * @return the parsed [JsonObject]
 * @author Fruxz
 * @since 2023.1
 */
fun Path.readJsonObject(json: Json = globalJson) = json.decodeFromStream<JsonObject>(this.inputStream())

/**
 * This function reads, parses and converts the file content to an [JsonObject]
 * using the [Json.decodeFromStream] function.
 * @return the parsed [JsonObject] or null if failed
 * @author Fruxz
 * @since 2023.1
 */
fun Path.readJsonObjectOrNull(json: Json = globalJson) = tryOrNull { json.decodeFromStream<JsonObject>(this.inputStream()) }

/**
 * This function returns the content of [this] File using the [inputStream] and [fromJsonStream] function.
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T> File.readJson(json: Json = globalJson) = inputStream().fromJsonStream<T>(json = json)

/**
 * This function returns the content of [this] File using the [inputStream] and [fromJsonStreamOrNull] function.
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T> File.readJsonOrNull(json: Json = globalJson) = inputStream().fromJsonStreamOrNull<T>(json = json)

// read default

/**
 * This function reads the content of the file under [this] Path, converts it from the
 * json string to an object of type [T] via the [readJsonOrDefault] function, or
 * returns [default] if json cannot be read or transformed.
 * If the file does not have the transformable json contained / existent and
 * [writeIfBlank] is true, the [default] will be written to the file.
 * @param default the default value to return if the file is blank, non-transformable or does not exist.
 * @param writeIfBlank if the file is blank or does not exist, the [default] will be written to the file.
 * @param writeCreatesParent if the parent directories will be created before writing.
 * @see readJsonOrNull
 * @see Path.createDirectories
 * @see writeJson
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T> Path.readJsonOrDefault(default: T, writeIfBlank: Boolean = false, writeCreatesParent: Boolean = true, json: Json = globalJson, vararg options: OpenOption) =
    when {
        writeIfBlank -> {
            tryOrNull { readJsonOrNull<T>() } ?: default.also {
                if (writeCreatesParent) parent.createDirectories()
                if (this.notExists()) this.createFile()
                writeJson(default, json = json, *options)
            }
        }
        else -> readJsonOrNull<T>(json = json) ?: default
    }

/**
 * This function reads the content of [this] file, converts it from the
 * json string to an object of type [T] via the [readJsonOrDefault] function, or
 * returns [default] if json cannot be read or transformed.
 * If the file does not have the transformable json contained / existent and
 * [writeIfBlank] is true, the [default] will be written to the file.
 * @param default the default value to return if the file is blank, non-transformable or does not exist.
 * @param writeIfBlank if the file is blank or does not exist, the [default] will be written to the file.
 * @param writeCreatesParent if the parent directories will be created before writing.
 * @see readJsonOrNull
 * @see Path.createDirectories
 * @see writeJson
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T> File.readJsonOrDefault(default: T, writeIfBlank: Boolean = false, writeCreatesParent: Boolean = true, json: Json = globalJson) =
    when {
        writeIfBlank -> {
            tryOrNull { readJsonOrNull<T>() } ?: default.also {
                if (writeCreatesParent) parentFile.mkdirs()
                if (!this.exists()) this.createNewFile()
                writeJson(default, json = json)
            }
        }
        else -> readJsonOrNull<T>(json = json) ?: default
    }