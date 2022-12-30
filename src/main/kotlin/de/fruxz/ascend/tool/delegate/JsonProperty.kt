package de.fruxz.ascend.tool.delegate

import de.fruxz.ascend.annotation.LanguageFeature
import de.fruxz.ascend.extension.data.readJsonOrDefault
import de.fruxz.ascend.extension.data.readJsonOrNull
import de.fruxz.ascend.extension.data.writeJson
import de.fruxz.ascend.extension.forceCastOrNull
import de.fruxz.ascend.extension.objects.takeIfCastableTo
import kotlinx.serialization.json.JsonNull.content
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
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
			return (cache[file]?.get(key)

				?: file.readJsonOrNull<Map<String, JsonPrimitive>>()?.also {
					cache[file] = (cache[file] ?: mutableMapOf()).apply { putAll(it) }
				}?.get(key))?.translate<T>()

				?: default().also {
					this.content = it
				}
		}
		set(value) {
			val currentContent = file.readJsonOrDefault<Map<String, JsonPrimitive>>(emptyMap())
			val newContent = currentContent + (key to value.translate())

			file.writeJson(newContent)

			cache[file] = newContent.toMutableMap()

		}

	init {
		content = content
	}

	operator fun getValue(thisRef: Any?, property: KProperty<*>): T = this::content.get()

	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = this::content.set(value)

	companion object {
		internal val cache: MutableMap<Path, MutableMap<String, JsonPrimitive>> = mutableMapOf()
	}

}
data class SmartJsonProperty<T>(
	val file: Path,
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

	private fun getContent(key: String): T {
		return (cache[file]?.get(key)

			?: file.readJsonOrNull<Map<String, JsonPrimitive>>()?.also {
				cache[file] = (cache[file] ?: mutableMapOf()).apply { putAll(it) }
			}?.get(key))?.translate<T>()

			?: default().also {
				setContent(key, it)
			}
	}

	private fun setContent(key: String, value: T) {
		val currentContent = file.readJsonOrDefault<Map<String, JsonPrimitive>>(emptyMap())
		val newContent = currentContent + (key to value.translate())

		file.writeJson(newContent)

		cache[file] = newContent.toMutableMap()
	}

	operator fun getValue(thisRef: Any?, property: KProperty<*>): T = getContent(property.name)

	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = setContent(property.name, value)

	companion object {
		internal val cache: MutableMap<Path, MutableMap<String, JsonPrimitive>> = mutableMapOf()
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

/**
 * This function creates a [SmartJsonProperty] under the [file] and [defaultValue] value, but without a key.
 * Instead of the same [property] function, which has a key, this function will use the property name as key.
 * This makes it a 'on demand' solution, but comes with the downside, that the default value will only be written,
 * if the property is accessed for the first time.
 * @author Fruxz
 * @since 1.0
 */
@LanguageFeature
fun <T : Any> property(
	file: Path,
	defaultValue: () -> T,
): SmartJsonProperty<T> = SmartJsonProperty(file.absolute(), defaultValue)