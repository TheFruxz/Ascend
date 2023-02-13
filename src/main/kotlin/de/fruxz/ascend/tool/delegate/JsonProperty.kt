package de.fruxz.ascend.tool.delegate

import de.fruxz.ascend.annotation.LanguageFeature
import de.fruxz.ascend.extension.data.*
import de.fruxz.ascend.extension.forceCastOrNull
import de.fruxz.ascend.extension.objects.takeIfCastableTo
import kotlinx.serialization.json.*
import kotlinx.serialization.json.JsonNull.content
import java.nio.file.Path
import kotlin.io.path.absolute
import kotlin.reflect.KProperty

data class JsonProperty<T>(
	val file: Path,
	val key: String,
	val default: () -> T,
) {

	private fun <T> JsonPrimitive.translate(): T? {
		val content = this.contentOrNull ?: return null

		val asString = content.takeIf { this.isString }?.forceCastOrNull<T>()
		val asBoolean = content.toBooleanStrictOrNull()?.takeIfCastableTo<T>()
		val asInt = content.toIntOrNull()?.takeIfCastableTo<T>()
		val asLong = content.toLongOrNull()?.takeIfCastableTo<T>()
		val asFloat = content.toFloatOrNull()?.takeIfCastableTo<T>()
		val asDouble = content.toDoubleOrNull()?.takeIfCastableTo<T>()

		return asString ?: asBoolean ?: asInt ?: asLong ?: asFloat ?: asDouble ?: error("Delegate properties only accept String, Boolean, Int, Long, Float and Double, because they are primitive types!")
	}

	private fun <T> T.translate(): JsonPrimitive {
		return when (this) {
			is String -> JsonPrimitive(this)
			is Boolean -> JsonPrimitive(this)
			is Int -> JsonPrimitive(this)
			is Long -> JsonPrimitive(this)
			is Float -> JsonPrimitive(this)
			is Double -> JsonPrimitive(this)
			else -> error("Delegate properties only accept String, Boolean, Int, Long, Float and Double, because they are primitive types!")
		}
	}

	var content: T
		get() {
			return when (val cachedValue = cache[file]?.get(key)) {
				null -> {
					val fileContent = file.readJsonObjectOrNull()
					val propertyValue = fileContent?.get(key)

					when (val transformedValue = propertyValue?.jsonPrimitive?.translate<T>()) {
						null -> default().also { this.content = it }
						else -> {

							cache[file] = fileContent

							transformedValue
						}
					}

				}
				else -> cachedValue.jsonPrimitive.translate<T>()!! // TODO should accept every type in the near future!
			}
		}
		set(value) {
			val currentObject = file.readJsonObjectOrNull() ?: JsonObject(emptyMap())
			val newObject = buildJsonObject(currentObject) {
				put(key, value.translate())
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
 * @author Fruxz
 * @since 1.0
 */
@LanguageFeature
fun <T : Any> property(
	file: Path,
	key: String,
	defaultValue: () -> T,
): JsonProperty<T> = JsonProperty(file.absolute(), key, defaultValue)