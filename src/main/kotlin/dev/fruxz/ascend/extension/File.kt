@file:Suppress("NOTHING_TO_INLINE")

package dev.fruxz.ascend.extension

import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files.createFile
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.FileAttribute
import kotlin.io.path.*

/**
 * This function returns the path to a project resource as a [Path].
 * @param resource the path of the resource located inside the resources folder
 * @return the path to the resource
 * @throws NoSuchElementException if the resource cannot be found
 * @author Fruxz
 * @since 2023.1
 */
@Throws(NoSuchElementException::class)
inline fun getResource(resource: String): Path = getResourceOrNull(resource) ?: throw NoSuchElementException("Resource $resource not found")

/**
 * This function returns the path to a project resource as a [Path].
 * @param resource the path of the resource located inside the resources folder
 * @return the path to the resource or null if not found
 * @author Fruxz
 * @since 2023.1
 */
inline fun getResourceOrNull(resource: String): Path? = object {}.javaClass.classLoader.getResource(resource)?.toURI()?.let(Paths::get)

/**
 * Converts the string [this] into a full [File] using [this] as a [Path],
 * through the [Path.of] and the [Path.toFile] functions.
 * @author Fruxz
 * @since 2023.1
 */
fun String.pathAsFile(): File =
    Path.of(this).absolute().toFile()

/**
 * Converts the string [this] into a base-based [File] using [this] as a [Path],
 * through the [Path.of] and the [Path.toFile] functions additionally the
 * [System.getProperty]("user.dir") process.
 * @author Fruxz
 * @since 2023.1
 */
@Deprecated(message = "Path from runtime will be removed, use pathAsFile instead", replaceWith = ReplaceWith("pathAsFile()"))
fun String.pathAsFileFromRuntime() =
    File(System.getProperty("user.dir") + "/$this")

/**
 * This function creates the parent directories and the
 * file itself. This utilizes the [File.mkdirs]
 * and the [File.createNewFile] function.
 * @author Fruxz
 * @since 2023.1
 */
fun File.createFileAndDirectories(ignoreIfExists: Boolean = true) = apply {
    if (tryOrNull { parentFile } != null && (!parentFile.exists() || !ignoreIfExists)) parentFile.mkdirs()
    if (!exists() || !ignoreIfExists) createNewFile()
}

/**
 * This function uses the [Path.getParent] of this [Path] to
 * generate the directories, to which this path points to.
 * @author Fruxz
 * @since 2023.1
 */
fun Path.createParentDirectories(ignoreIfExists: Boolean = true, vararg attributes: FileAttribute<*>) = apply {
    if (tryOrNull { parent } != null && (parent.notExists() || !ignoreIfExists)) parent.createDirectories(*attributes)
}

/**
 * This function creates the parent directories and the
 * file itself. This utilizes the [createParentDirectories]
 * and the [createFile] function.
 * @author Fruxz
 * @since 2023.1
 */
fun Path.createFileAndDirectories(ignoreIfExists: Boolean = true, directoryAttributes: List<FileAttribute<*>> = emptyList(), fileAttributes: List<FileAttribute<*>> = listOf()) = apply {
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
 * @since 2023.1
 */
inline fun getHomePath(absolute: Boolean = true): Path = Paths.get("").let {
    if (absolute) it.toAbsolutePath() else it
}

/**
 * This computational value returns the result of invoking the [getHomePath],
 * with the parameter `absolute` set to true.
 * @author Fruxz
 * @since 2023.1
 */
val absoluteHomePath = getHomePath(true)

/**
 * This function tries to return the result of executing the [readText],
 * or returns null, if an exception gets thrown.
 * @see readText
 * @see tryOrNull
 * @author Fruxz
 * @since 2023.1
 */
fun Path.readTextOrNull(charset: Charset = Charsets.UTF_8) = tryOrNull { readText(charset) }

/**
 * This function tries to return the result of executing the [readText],
 * or returns null, if an exception gets thrown.
 * @see readText
 * @see tryOrNull
 * @author Fruxz
 * @since 2023.1
 */
fun File.readTextOrNull(charset: Charset = Charsets.UTF_8) = tryOrNull { this.readText(charset) }