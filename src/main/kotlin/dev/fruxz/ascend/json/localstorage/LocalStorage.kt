package dev.fruxz.ascend.json.localstorage

import dev.fruxz.ascend.extension.createFileAndDirectories
import dev.fruxz.ascend.extension.logging.getItsLogger
import dev.fruxz.ascend.json.readJson
import dev.fruxz.ascend.json.writeJson
import kotlinx.serialization.Serializable
import java.nio.file.Path
import kotlin.io.path.copyTo
import kotlin.io.path.div
import kotlin.io.path.fileSize
import kotlin.io.path.notExists
import kotlin.io.path.pathString
import kotlin.reflect.KProperty
import kotlin.reflect.typeOf

/**
 * Creates a new [JsonLocalStorage] instance for the specified type [T].
 * Adds [beforePush] and [afterPush] hooks for custom processing before and after saving the state.
 * @return a new [JsonLocalStorage] instance for the specified type [T].
 * @see JsonLocalStorage
 * @author Fruxz
 * @since 2025.7
 */
inline fun <reified T : Any> storedJson(
    path: Path,
    noinline beforePush: (T) -> T = { it },
    noinline afterPush: (T) -> Unit = { },
    noinline default: () -> (@Serializable T),
) = object : JsonLocalStorage<T> {

    var exception: Exception? = null

    override val defaultValue: () -> @Serializable T = default
    override val filePath: Path = path
    override val contentType = typeOf<T>()
    private var cache: T? = null
    override var state: T
        get() = cache ?: readFile() ?: default()
        set(value) {
            writeFile(beforePush(value))
            afterPush(value)
        }

    override fun readFile() = try {
        when {
            path.notExists() || path.fileSize() == 0L -> null
            else -> path.readJson<T>().also { cache = it }
        }
    } catch (e: Exception) {
        getItsLogger().warning { "Exception '${e.message}' on loading ${path.pathString} - defaulting" }
        e.printStackTrace()
        cache = default()
        exception = e
        null
    }

    override fun writeFile(value: T) = try {
        createBackupFile()
        when {
            exception == null -> {
                path.writeJson(value).also { cache = value }
                deleteBackupFile()
            }
            else -> getItsLogger().warning { "stored object @ '${path.pathString}' of type ${value::class.qualifiedName} is in error state, not saving" }
        }
        path
    } catch (e: Exception) {
        restoreFromBackup()
        getItsLogger().warning { "Exception '${e.message}' on saving ${path.pathString}" }
        e.printStackTrace()
        exception = e
        throw e
    }

    override fun provideDelegate(thisRef: Any?, property: KProperty<*>): JsonLocalStorage<T> {

        if (path.notExists() || path.fileSize() == 0L) {
            cache = default()
            path.createFileAndDirectories()
            path.writeJson(cache)
        }

        return this
    }

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
        state

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        state = value
    }

    private fun createBackupFile() {
        if (path.notExists()) {
            getItsLogger().warning { "Cannot create backup file, path does not exist: ${path.pathString}" }
            return
        }

        val backupPath = path.parent / "${path.fileName}.old"
        if (backupPath.notExists()) backupPath.createFileAndDirectories()
        path.copyTo(backupPath, overwrite = true)
    }

    private fun deleteBackupFile() {
        val backupPath = path.parent / "${path.fileName}.old"
        if (backupPath.notExists()) {
            getItsLogger().warning { "Cannot delete backup file, path does not exist: ${backupPath.pathString}" }
            return
        }
        backupPath.toFile().delete()
    }

    private fun restoreFromBackup() {
        val backupPath = path.parent / "${path.fileName}.old"
        if (backupPath.notExists()) {
            getItsLogger().warning { "Cannot restore from backup, backup file does not exist: ${backupPath.pathString}" }
            return
        }

        try {
            backupPath.copyTo(path, overwrite = true)
            getItsLogger().info { "Restored from backup: ${backupPath.pathString} to ${path.pathString}" }
        } catch (e: Exception) {
            getItsLogger().warning { "Failed to restore from backup: ${e.message}" }
            e.printStackTrace()
        }
    }

}