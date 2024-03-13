@file:Suppress("NOTHING_TO_INLINE")

package dev.fruxz.ascend.extension

import java.io.File
import java.io.InputStream
import java.nio.charset.Charset
import java.nio.file.Files.createFile
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.FileAttribute
import kotlin.io.path.*

/**
 * This function returns the path to a project resource as a [Path].
 * ***NOTICE: This uses the resource as a file,
 * this may not work while in an environment, which does not support jar: protocol.
 * Instead, consider [getResourceAsStream]***
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
 * ***NOTICE: This uses the resource as a file,
 * this may not work while in an environment, which does not support jar: protocol.
 * Instead, consider [getResourceAsStreamOrNull]***
 * @param resource the path of the resource located inside the resources folder
 * @return the path to the resource or null if not found
 * @author Fruxz
 * @since 2023.1
 */
inline fun getResourceOrNull(resource: String): Path? = getClassLoader().getResource(resource)?.toURI()?.let(Paths::get)

/**
 * This function returns the input stream of a project resource.
 * @param resource the path of the resource located inside the resources folder
 * @return the input stream of the resource
 * @throws NoSuchElementException if the resource cannot be found
 * @see getResourceAsStreamOrNull
 * @author Fruxz
 * @since 2024.1.2
 */
inline fun getResourceAsStream(resource: String): InputStream = getResourceAsStreamOrNull(resource) ?: throw NoSuchElementException("Resource $resource not found")

/**
 * This function returns the input stream of a project resource.
 * @param resource the path of the resource located inside the resources folder
 * @return the input stream of the resource or null if not found
 * @author Fruxz
 * @since 2024.1.2
 */
inline fun getResourceAsStreamOrNull(resource: String): InputStream? = getClassLoader()?.getResourceAsStream(resource)

/**
 * This function returns the class loader of the current class.
 * @return the class loader of the current class
 * @author Fruxz
 * @since 2024.1.2
 */
inline fun getClassLoader(): ClassLoader = object {}.javaClass.classLoader

/**
 * Converts the string [this] into a full [File] using [this] as a [Path],
 * through the [Path.of] and the [Path.toFile] functions.
 * @author Fruxz
 * @since 2023.1
 */
fun String.pathAsFile(): File =
    Path.of(this).absolute().toFile()

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
 * This function creates the parent directories and the
 * file itself. This utilizes the [createParentDirectories]
 * and the [createFile] function.
 * @author Fruxz
 * @since 2023.1
 */
fun Path.createFileAndDirectories(ignoreIfExists: Boolean = true, directoryAttributes: List<FileAttribute<*>> = emptyList(), fileAttributes: List<FileAttribute<*>> = listOf()) = apply {
    createParentDirectories(*directoryAttributes.toTypedArray())
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
inline fun getBasePath(absolute: Boolean = true): Path = Path("").modifiedIf(absolute) { it.toAbsolutePath() }

/**
 * This computational value returns the result of invoking the [getBasePath],
 * with the parameter `absolute` set to true.
 * @author Fruxz
 * @since 2023.1
 */
val absoluteBasePath = getBasePath(true)

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