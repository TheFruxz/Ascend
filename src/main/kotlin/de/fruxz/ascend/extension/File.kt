@file:Suppress("NOTHING_TO_INLINE")

package de.fruxz.ascend.extension

import de.fruxz.ascend.tool.path.ArtificialPath
import de.fruxz.ascend.tool.path.ArtificialReadOnlyResourcePathProcessor
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files.createFile
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.FileAttribute
import kotlin.io.path.absolute
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.exists
import kotlin.io.path.notExists
import kotlin.io.path.pathString
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
    Path.of(this).absolute().toFile()

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
 * This function creates the parent directories and the
 * file itself. This utilizes the [File.mkdirs]
 * and the [File.createNewFile] function.
 * @author Fruxz
 * @since 1.0
 */
fun File.createFileAndDirectories(ignoreIfExists: Boolean = true) {
    if (tryOrNull { parentFile } != null && (!parentFile.exists() || !ignoreIfExists)) parentFile.mkdirs()
    if (!exists() || !ignoreIfExists) createNewFile()
}

/**
 * This function uses the [Path.getParent] of this [Path] to
 * generate the directories, to which this path points to.
 * @author Fruxz
 * @since 1.0
 */
fun Path.createParentDirectories(ignoreIfExists: Boolean = true, vararg attributes: FileAttribute<*>) {
    if (tryOrNull { parent } != null && (parent.notExists() || !ignoreIfExists)) parent.createDirectories(*attributes)
}

/**
 * This function creates the parent directories and the
 * file itself. This utilizes the [createParentDirectories]
 * and the [createFile] function.
 * @author Fruxz
 * @since 1.0
 */
fun Path.createFileAndDirectories(ignoreIfExists: Boolean = true, directoryAttributes: List<FileAttribute<*>> = emptyList(), fileAttributes: List<FileAttribute<*>> = listOf()) {
    createParentDirectories(ignoreIfExists, *directoryAttributes.toTypedArray())
    if (notExists() || !ignoreIfExists) createFile(*fileAttributes.toTypedArray())
}

/**
 * This function returns the path, where the application
 * is running from. This utilizes the [Paths.get] function
 * with an empty string, and then (if [absolute] is not false)
 * converts the path to an absolute path using [Path.toAbsolutePath].
 * @param absolute if the path should be absolute
 * @return the path, where the application is running from
 * @author Fruxz
 * @since 1.0
 */
inline fun getHomePath(absolute: Boolean = true): Path = Paths.get("").let {
    if (absolute) it.toAbsolutePath() else it
}

/**
 * This computational value returns the result of invoking the [getHomePath],
 * with the parameter `absolute` set to true.
 * @author Fruxz
 * @since 1.0
 */
val absoluteHomePath = getHomePath(true)

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