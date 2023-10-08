package dev.fruxz.ascend.json

import dev.fruxz.ascend.extension.forceCastOrNull
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.serializer
import java.nio.file.Path
import kotlin.io.path.absolute
import kotlin.io.path.createParentDirectories
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 * Represents a JSON property that can be read from and written to a file.
 * The property value is of type [T].
 *
 * @param file The file path to read from and write to.
 * @param key The key of the property in the JSON file.
 * @param type The type of the property value.
 * @param json The [Json] object used for JSON serialization and deserialization.
 * @param default A lambda function that returns the default value of the property.
 *
 * @property content The current value of the JSON property.
 *
 * @constructor Creates a new instance of the [JsonProperty] class and initializes the [content] property.
 *
 * @throws IllegalStateException If the cached content is invalid or corrupted.
 *
 * @since 2023.1
 */
data class JsonProperty<T : Any>(
    val file: Path,
    val key: String,
    val type: KType,
    val json: Json = globalJson,
    val default: () -> T,
) {

	private fun JsonElement.toRequested(): T? =
		json.decodeFromJsonElement(json.serializersModule.serializer(type), this)?.forceCastOrNull()

	private fun fromProvided(data: T): JsonElement =
		json.encodeToJsonElement(serializer = json.serializersModule.serializer(type), value = data)

	var content: T
		get() {
			return when (val cachedValue = cache[file]?.get(key)) {
				null -> {
					val fileContent = file.readJsonObjectOrNull(json = json)
					val propertyValue = fileContent?.get(key)

					when (val transformedValue = propertyValue?.toRequested()) {
						null -> default().also { this.content = it }
						else -> {

							cache[file] = fileContent

							transformedValue
						}
					}

				}
				else -> cachedValue.toRequested() ?: error("Problem of resolving cached content, invalid or corrupted!")
			}
		}
		set(value) {
			val currentObject = file.readJsonObjectOrNull(json = json) ?: JsonObject(emptyMap())
			val newObject = buildJsonObject(currentObject) {
				put(key = key, element = fromProvided(value))
			}

			file.createParentDirectories()
			file.writeJson(newObject)
			cache[file] = newObject

		}

	init {
		content = content
	}

	operator fun getValue(thisRef: Any?, property: KProperty<*>): T = this::content.get()

	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = this::content.set(value)

	companion object {
		internal val cache: MutableMap<Path, JsonObject> = mutableMapOf()
	}

}

/**
 * This function creates a [JsonProperty] under the [file] path with the given [key] and [defaultValue] value.
 * If this gets created with e.g. `val <name> by [property]...` the default value will be instantly be set,
 * to initialize the file with all its possible properties.
 * This has the benefit, that for example, properties can be directly edited with its full potential, without
 * having to call every code snipped to fully initialize the file.
 * @author Fruxz
 * @since 2023.1
 */
inline fun <reified T : Any> property(
    file: Path,
    key: String,
    json: Json = globalJson,
    noinline defaultValue: () -> T,
): JsonProperty<T> = JsonProperty(
	file = file.absolute(),
	key = key,
	type = typeOf<T>(),
	json = json,
	default = defaultValue
)