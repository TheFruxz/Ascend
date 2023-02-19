package de.fruxz.ascend.tool.delegate

import de.fruxz.ascend.annotation.LanguageFeature
import de.fruxz.ascend.extension.data.*
import de.fruxz.ascend.extension.forceCast
import de.fruxz.ascend.extension.forceCastOrNull
import de.fruxz.ascend.extension.objects.takeIfCastableTo
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.*
import kotlinx.serialization.serializer
import java.nio.file.Path
import kotlin.io.path.absolute
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

data class JsonProperty<T : Any>(
	val file: Path,
	val key: String,
	val json: Json = jsonBase,
	val type: KType,
	val default: () -> T,
) {

	private fun <T : Any> JsonElement.toRequested(): T? {
		return when (this) {
			is JsonPrimitive -> {
				val content = this.contentOrNull ?: return null

				val asString = content.takeIf { this.isString }?.forceCastOrNull<T>()
				val asBoolean = content.toBooleanStrictOrNull()?.takeIfCastableTo<T>()
				val asInt = content.toIntOrNull()?.takeIfCastableTo<T>()
				val asLong = content.toLongOrNull()?.takeIfCastableTo<T>()
				val asFloat = content.toFloatOrNull()?.takeIfCastableTo<T>()
				val asDouble = content.toDoubleOrNull()?.takeIfCastableTo<T>()

				asString ?: asBoolean ?: asInt ?: asLong ?: asFloat ?: asDouble ?: throw IllegalStateException("Primitive json type has no content, which met the requirements")

			}
			is JsonArray -> this.toList().takeIfCastableTo()
			is JsonObject -> json.decodeFromJsonElement(json.serializersModule.serializer(type).forceCast<KSerializer<T>>(), this).takeIfCastableTo()
		}
	}

	private fun <T : Any> T.fromProvided(): JsonElement =
		(this as? Number)?.jsonPrimitive() ?:
		(this as? Double)?.jsonPrimitive() ?:
		(this as? String)?.jsonPrimitive() ?:
		json
			.encodeToJsonElement(
				serializer = json.serializersModule.serializer(type),
				value = this,
			) // TODO serializer was casted to .forceCast<KSerializer<T>>() but was removed with the type introduction, still required? I dont think so

	var content: T
		get() {
			return when (val cachedValue = cache[file]?.get(key)) {
				null -> {
					val fileContent = file.readJsonObjectOrNull(json = json)
					val propertyValue = fileContent?.get(key)

					when (val transformedValue = propertyValue?.toRequested<T>()) {
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
				put(key, value.fromProvided())
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
inline fun <reified T : Any> property(
	file: Path,
	key: String,
	json: Json = jsonBase,
	noinline defaultValue: () -> T,
): JsonProperty<T> = JsonProperty(file.absolute(), key, json, typeOf<T>(), defaultValue)