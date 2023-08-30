package dev.fruxz.ascend.tool.zip

import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import kotlin.io.path.*

/**
 * This object provides functions to list contents, pack, and unpack zip files.
 * @author Fruxz
 * @since 2023.3.2
 */
object Zip {

    /**
     * This function packs the [files] into a zip file at the [target] location.
     * @param files files to pack
     * @param target the target zip file
     * @param override if the target file should be overridden if it already exists
     * @return the target zip file path
     * @throws IllegalArgumentException if the target file already exists and [override] is false
     * @throws Exception if something goes wrong while packing the files
     */
    @Throws(IllegalArgumentException::class)
    fun pack(files: Collection<File>, target: Path, override: Boolean = false): Path {
        if (target.exists()) {
            if (!override) throw IllegalArgumentException("Target file already exists!")
            @OptIn(ExperimentalPathApi::class) target.deleteRecursively()
        }

        ZipOutputStream(target.outputStream()).use { zipStream ->
            files.forEach { file ->
                if (file.isDirectory) {
                    packDirectoryRecursively(file, file.name, zipStream)
                } else {
                    val entry = ZipEntry(file.name)
                    zipStream.putNextEntry(entry)
                    FileInputStream(file).use { fis ->
                        fis.copyTo(zipStream)
                    }
                    zipStream.closeEntry()
                }
            }
        }

        return target
    }

    private fun packDirectoryRecursively(directory: File, path: String, zipStream: ZipOutputStream) {
        directory.listFiles()?.forEach { file ->
            val newPath = "$path/${file.name}"
            if (file.isDirectory) {
                packDirectoryRecursively(file, newPath, zipStream)
            } else {
                val entry = ZipEntry(newPath)
                zipStream.putNextEntry(entry)
                FileInputStream(file).use { fis ->
                    fis.copyTo(zipStream)
                }
                zipStream.closeEntry()
            }
        }
    }

    /**
     * This function unpacks the [zip] file into the [target] directory.
     * @param zip the zip file to unpack
     * @param target the target directory, defaults to the zip file's parent directory + the zip file's name without the extension
     * @param override if the target directory should be overridden if it already exists
     * @return the target directory path
     * @throws IllegalArgumentException if the target directory already exists and [override] is false
     * @throws Exception if something goes wrong while unpacking the zip file
     */
    @Throws(IllegalArgumentException::class)
    fun unpack(zip: Path, target: Path = zip.parent / zip.nameWithoutExtension, override: Boolean = false): Path {
        if (target.exists()) {
            if (!override) throw IllegalArgumentException("Target directory already exists!")
            @OptIn(ExperimentalPathApi::class) target.deleteRecursively()
        }

        ZipFile(zip.toFile()).use { zipFile ->
            zipFile.entries().asSequence().forEach { entry ->
                val targetFile = target.resolve(entry.name)
                if (entry.isDirectory) {
                    Files.createDirectories(targetFile)
                } else {
                    Files.createDirectories(targetFile.parent)
                    Files.copy(zipFile.getInputStream(entry), targetFile, StandardCopyOption.REPLACE_EXISTING)
                }
            }
        }

        return target
    }

    /**
     * This function reads the [zip] file and returns a list of all entries.
     * @param zip the zip file to read
     * @return a list of all entries
     * @throws Exception if something goes wrong while reading the zip file
     */
    fun read(zip: Path): List<ZipEntry> = ZipFile(zip.toFile()).entries().asSequence().toList()

}