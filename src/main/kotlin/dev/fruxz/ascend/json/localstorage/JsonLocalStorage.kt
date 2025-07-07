package dev.fruxz.ascend.json.localstorage

import kotlinx.serialization.Serializable
import java.nio.file.Path
import kotlin.reflect.KProperty
import kotlin.reflect.KType

/**
 * This interface represents a local storage for JSON data, allowing you to read and write
 * serialized objects to a file. It provides a way to manage the state of an object with
 * default values and file paths.
 *
 * @param T The type of the object to be stored, which must be serializable.
 * @author Fruxz
 * @since 2025.7
 */
interface JsonLocalStorage<T : Any> {

    /**
     * A function that provides a default value for the stored object.
     * This function should return a serializable object of type T.
     * Used for writing & reading the first value, if no file exists.
     * @author Fruxz
     * @since 2025.7
     */
    val defaultValue: () -> (@Serializable T)

    /**
     * The path to the file where the JSON data is stored.
     * This should be a valid [Path] object pointing to the desired storage location (file of final destination, not folder!).
     * @author Fruxz
     * @since 2025.7
     */
    val filePath: Path

    val contentType: KType

    /**
     * The current state of the stored object.
     * This is the value that is read from or written to the file.
     * @author Fruxz
     * @since 2025.7
     */
    var state: T

    /**
     * Reads the JSON data from the file and returns it as an object of type T.
     * If the file does not exist or is empty, it returns null.
     * @author Fruxz
     * @since 2025.7
     */
    fun readFile(): T?

    /**
     * Writes the given object to the file as JSON data.
     * Returns the path to the file where the data was written.
     *
     * @param value The object of type T to be written to the file.
     * @author Fruxz
     * @since 2025.7
     */
    fun writeFile(value: T): Path

    operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): JsonLocalStorage<T>

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)

}