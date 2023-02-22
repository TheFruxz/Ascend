package de.fruxz.ascend.json

import de.fruxz.ascend.extension.readTextOrNull
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import java.io.File
import java.nio.charset.Charset
import java.nio.file.OpenOption
import java.nio.file.Path
import kotlin.io.path.*

// write JSON

/**
 * This function writes the given [this] object to a JSON file via the [globalJson]
 * and [toJsonString] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see toJsonString
 * @see writeText
 * @return The path itself.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.writeJson(content: T, charset: Charset = Charsets.UTF_8, json: Json = globalJson, vararg options: OpenOption) = apply { writeText(content.toJsonString(json = json), charset, *options) }

/**
 * This function writes the given [this] object to a JSON file via the [globalJson]
 * and [toJsonString] function from the Kotlinx serialization library.
 * This process can throw exceptions if something goes wrong!
 * @see toJsonString
 * @see writeText
 * @return The file itself.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.writeJson(content: T, charset: Charset = Charsets.UTF_8, json: Json = globalJson) = apply { writeText(content.toJsonString(json = json), charset) }

/**
 * This function writes the given [content], to the file under [this] path, via the [writeJson]
 * if the file under [this] path currently does not exist.
 * @param content the content to write as json to the file.
 * @param createParent if the parent directories will be created during to write
 * @param charset the charset to use for to write
 * @see writeJson
 * @return the path itself.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.writeJsonIfNotExists(content: T, createParent: Boolean = true, charset: Charset = Charsets.UTF_8, json: Json = globalJson, vararg options: OpenOption) = apply {
    if (!exists()) {
        if (createParent) parent.createDirectories()
        writeJson(content, charset, json = json, *options)
    }
}

/**
 * This function writes the given [content], to [this] file , via the [writeJson]
 * if the file currently does not exist.
 * @param content the content to write as json to the file
 * @param createParent if the parent directories will be created during the write
 * @param charset the charset to use fo r to write
 * @see writeJson
 * @return the file itself
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.writeJsonIfNotExists(content: T, createParent: Boolean = true, charset: Charset = Charsets.UTF_8, json: Json = globalJson) = apply {
    if (!exists()) {
        if (createParent) parentFile.mkdirs()
        writeJson(content, charset, json = json)
    }
}

/**
 * This function writes the given [content], to the file under [this] path, via the [writeJson]
 * if the file under [this] path currently is empty or does not exist.
 * @param content the content to write as json to the file.
 * @param createParent if the parent directories will be created during to write
 * @param charset the charset to use for to write
 * @see writeJson
 * @return the path itself.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.writeJsonIfEmpty(content: T, createParent: Boolean = true, charset: Charset = Charsets.UTF_8, json: Json = globalJson, vararg options: OpenOption) = apply {
    if (readTextOrNull(charset)?.isBlank() != false) {
        if (createParent) parent.createDirectories()
        writeJson(content, charset, json = json, *options)
    }
}

/**
 * This function writes the given [content], to [this] file, via the [writeJson]
 * if [this] file is currently empty or does not exist.
 * @param content the content to write as json to the file.
 * @param createParent if the parent directories will be created during to write
 * @param charset the charset to use for to write
 * @see writeJson
 * @return the file itself.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.writeJsonIfEmpty(content: T, createParent: Boolean = true, charset: Charset = Charsets.UTF_8, json: Json = globalJson) = apply {
    if (readTextOrNull(charset)?.isBlank() != false) {
        if (createParent) parentFile.mkdirs()
        writeJson(content, charset, json = json)
    }
}

/**
 * This function writes the given [content], to the file under [this] path, via the [writeJson]
 * if the file under [this] path is currently blank or does not exist.
 * @param content the content to write as json to the file.
 * @param createParent if the parent directories will be created during to write
 * @param charset the charset to use for to write
 * @see writeJson
 * @return the path itself
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.writeJsonIfBlank(content: T, createParent: Boolean = true, charset: Charset = Charsets.UTF_8, json: Json = globalJson, vararg options: OpenOption) = apply {
    if (readTextOrNull(charset)?.isBlank() != false) {
        if (createParent) parent.createDirectories()
        writeJson(content, charset, json = json, *options)
    }
}

inline fun <reified T> File.writeJsonIfBlank(content: T, createParent: Boolean = true, charset: Charset = Charsets.UTF_8, json: Json = globalJson) = apply {
    if (readTextOrNull(charset)?.isBlank() != false) {
        if (createParent) parentFile.mkdirs()
        writeJson(content, charset, json = json)
    }
}


// read JSON

/**
 * This function returns the content of [this] Path using the [fromJsonFile] function.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.readJson(charset: Charset = Charsets.UTF_8, json: Json = globalJson) = fromJsonFile<T>(charset, json = json)

/**
 * This function returns the content of [this] File using the [fromJsonFileOrNull] function.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.readJsonOrNull(charset: Charset = Charsets.UTF_8, json: Json = globalJson) = fromJsonFileOrNull<T>(charset, json = json)

/**
 * This function reads the file content and parses it to a [JsonElement]
 * using the [parseToJsonElement] function.
 * @return the parsed [JsonElement]
 * @author Fruxz
 * @since 1.0
 */
fun Path.readJsonElement(charset: Charset = Charsets.UTF_8, json: Json = globalJson) = readText(charset).parseToJsonElement(json = json)

/**
 * This function reads the file content and parses it to a [JsonElement]
 * using the [parseToJsonElementOrNull] function.
 * @return the parsed [JsonElement] or null if failed
 * @author Fruxz
 * @since 1.0
 */
fun Path.readJsonElementOrNull(charset: Charset = Charsets.UTF_8, json: Json = globalJson) = readTextOrNull(charset)?.parseToJsonElementOrNull(json = json)

/**
 * This function reads, parses and converts the file content to an [JsonObject]
 * using the [parseToJsonObject] function.
 * @return the parsed [JsonObject]
 * @author Fruxz
 * @since 1.0
 */
fun Path.readJsonObject(charset: Charset = Charsets.UTF_8, json: Json = globalJson) = readText(charset).parseToJsonObject(json = json)

/**
 * This function reads, parses and converts the file content to an [JsonObject]
 * using the [parseToJsonObjectOrNull] function.
 * @return the parsed [JsonObject] or null if failed
 * @author Fruxz
 * @since 1.0
 */
fun Path.readJsonObjectOrNull(charset: Charset = Charsets.UTF_8, json: Json = globalJson) = readTextOrNull(charset)?.parseToJsonObjectOrNull(json = json)

/**
 * This function returns the content of [this] File using the [fromJsonFile] function.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.readJson(charset: Charset = Charsets.UTF_8, json: Json = globalJson) = fromJsonFile<T>(charset, json = json)

/**
 * This function returns the content of [this] File using the [fromJsonFileOrNull] function.
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.readJsonOrNull(charset: Charset = Charsets.UTF_8, json: Json = globalJson) = fromJsonFileOrNull<T>(charset, json = json)

// read default

/**
 * This function reads the content of the file under [this] Path, converts it from the
 * json string to an object of type [T] via the [readJsonOrDefault] function, or
 * returns [default] if json cannot be read or transformed.
 * If the file does not have the transformable json contained / existent and
 * [writeIfBlank] is true, the [default] will be written to the file.
 * @param default the default value to return if the file is blank, non-transformable or does not exist.
 * @param writeIfBlank if the file is blank or does not exist, the [default] will be written to the file.
 * @param writeCreatesParent if the parent directories will be created during the write.
 * @param charset the charset to use for to write
 * @see readJsonOrNull
 * @see Path.createDirectories
 * @see writeJson
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> Path.readJsonOrDefault(default: T, writeIfBlank: Boolean = false, writeCreatesParent: Boolean = true, charset: Charset = Charsets.UTF_8, json: Json = globalJson, vararg options: OpenOption) = if (writeIfBlank) {
    readJsonOrNull<T>(charset) ?: default.also {
        if (writeCreatesParent) parent.createDirectories()
        writeJson(default, charset, json = json, *options)
    }
} else {
    readJsonOrNull<T>(charset, json = json) ?: default
}

/**
 * This function reads the content of [this] file, converts it from the
 * json string to an object of type [T] via the [readJsonOrDefault] function, or
 * returns [default] if json cannot be read or transformed.
 * If the file does not have the transformable json contained / existent and
 * [writeIfBlank] is true, the [default] will be written to the file.
 * @param default the default value to return if the file is blank, non-transformable or does not exist.
 * @param writeIfBlank if the file is blank or does not exist, the [default] will be written to the file.
 * @param writeCreatesParent if the parent directories will be created during the write.
 * @param charset the charset to use for to write
 * @see readJsonOrNull
 * @see Path.createDirectories
 * @see writeJson
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T> File.readJsonOrDefault(default: T, writeIfBlank: Boolean = false, writeCreatesParent: Boolean = true, charset: Charset = Charsets.UTF_8, json: Json = globalJson) = if (writeIfBlank) {
    readJsonOrNull<T>(charset) ?: default.also {
        if (writeCreatesParent) parentFile.mkdirs()
        writeJson(default, charset, json = json)
    }
} else {
    readJsonOrNull<T>(charset, json = json) ?: default
}