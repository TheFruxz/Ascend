@file:Suppress("NOTHING_TO_INLINE")

package de.fruxz.ascend.extension

import de.fruxz.ascend.tool.path.ArtificialPath
import de.fruxz.ascend.tool.path.ArtificialReadOnlyResourcePathProcessor
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.readText

/**
 * Returns the file inside the resource folder of the class, where the function is called from.
 * The file is returned by its content read by the [readText] function.
 * @param resource the file(+folder) path
 * @return the file content
 * @author Fruxz
 * @since 1.0
 */
inline fun getResourceText(resource: String) =
    object {}.javaClass.classLoader.getResource(resource)?.readText() ?: throw NoSuchElementException("Resource $resource not found")

/**
 * Returns the file inside the resource folder of the class, where the function is called from.
 * The file is returned by its content read by the [readBytes] function.
 * @param resource the file(+folder) path
 * @return the file content
 * @author Fruxz
 * @since 1.0
 */
inline fun getResourceByteArray(resource: String) =
    object {}.javaClass.classLoader.getResource(resource)?.readBytes() ?: throw NoSuchElementException("Resource $resource not found")

/**
 * Converts the string [this] into a full [File] using [this] as a [Path],
 * through the [Path.of] and the [Path.toFile] functions.
 * @author Fruxz
 * @since 1.0
 */
fun String.pathAsFile(): File =
    Path.of(this).toFile()

/**
 * Converts the string [this] into a base-based [File] using [this] as a [Path],
 * through the [Path.of] and the [Path.toFile] functions additionally the
 * [System.getProperty]("user.dir") process.
 * @author Fruxz
 * @since 1.0
 */
fun String.pathAsFileFromRuntime() =
    File(System.getProperty("user.dir") + "/$this")

/**
 * This function adds a part to the [Path] using
 * the [Path.resolve] function and a [other] part attaching
 * to the [Path].
 * @param other is the additional path
 * @author Fruxz
 * @since 1.0
 */
operator fun Path.div(other: String): Path = resolve(other)

/**
 * This value defines the basic, from Ascend created [ArtificialPath].
 * This [ArtificialPath] can be used, to easily access file in the
 * local directory & the resources.
 * @author Fruxz
 * @since 1.0
 */
val ascendArtificialPath = ArtificialPath(arrayOf(
    ArtificialReadOnlyResourcePathProcessor,
))

/**
 * This function uses the [ascendArtificialPath] and its [ArtificialPath.getFile]
 * function, to get access to the file behind the [path].
 * @param path is the path to the file
 * @return the file behind the path, or if the path is a '//readOnlyResource/:/',
 * than the file inside the resources.
 * @author Fruxz
 * @since 1.0
 */
fun getFileViaArtificialPath(path: String) = ascendArtificialPath.getFile(path)

/**
 * This function creates the parent folders and the file
 * itself, if the file doesn't exist.
 * @author Fruxz
 * @since 1.0
 */
fun File.generateFileAndPath() {
    parentFile.mkdirs()
    createNewFile()
}

/**
 * This function returns the path, where the application
 * is running from.
 * @sample getHomePath
 * @return the path, where the application is running from
 * @author Fruxz
 * @since 1.0
 */
inline fun getHomePath(): Path = Paths.get("")

/**
 * This function tries to return the result of executing the [readText],
 * or returns null, if an exception gets thrown.
 * @see readText
 * @see tryOrNull
 * @author Fruxz
 * @since 1.0
 */
fun Path.readTextOrNull(charset: Charset = Charsets.UTF_8) = tryOrNull { readText(charset) }

/**
 * This function tries to return the result of executing the [readText],
 * or returns null, if an exception gets thrown.
 * @see readText
 * @see tryOrNull
 * @author Fruxz
 * @since 1.0
 */
fun File.readTextOrNull(charset: Charset = Charsets.UTF_8) = tryOrNull { this.readText(charset) }