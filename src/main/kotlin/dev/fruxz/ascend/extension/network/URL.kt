package dev.fruxz.ascend.extension.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

/**
 * This function downloads the content of the given [URL] to the given [destination] path.
 * @param destination The destination path to download the content to.
 * @param options The [CopyOption]s to use when copying the content.
 * @return The number of bytes that have been copied.
 * @author Fruxz
 * @since 2023.1
 */
suspend inline fun URL.copyTo(destination: Path, vararg options: CopyOption) =
	withContext(Dispatchers.IO) {
		Files.copy(openStream(), destination, *options)
	}

/**
 * This function downloads the content of the given [URL] to the given [destination] path.
 * If the file already exists, it will be replaced.
 * @param destination The destination path to download the content to.
 * @return The number of bytes that have been copied.
 * @author Fruxz
 * @since 2023.1
 */
suspend inline infix fun URL.downloadTo(destination: Path) =
	copyTo(destination, StandardCopyOption.REPLACE_EXISTING)