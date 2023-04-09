package dev.fruxz.ascend.json

import dev.fruxz.ascend.annotation.ExperimentalAscendApi
import dev.fruxz.ascend.annotation.LanguageFeature
import dev.fruxz.ascend.extension.objects.takeIfCastableTo
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.serializer
import java.nio.file.Path
import kotlin.io.path.absolute
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

data class JsonProperty<T : Any>(
    val file: Path,
    val key: String,
    val type: KType,
    val json: Json = globalJson,
    val default: () -> T,
) {

	private fun JsonElement.toRequested(): T? =
		json.decodeFromJsonElement(json.serializersModule.serializer(type), this)?.takeIfCastableTo()

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
 * **Currently there is an issue with specific types (e.g. lists is having problems), because of that [ExperimentalAscendApi]**
 * @author Fruxz
 * @since 1.0
 */
@dev.fruxz.ascend.annotation.LanguageFeature
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