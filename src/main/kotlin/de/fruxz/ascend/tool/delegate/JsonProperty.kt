package de.fruxz.ascend.tool.delegate

import de.fruxz.ascend.annotation.ExperimentalAscendApi
import de.fruxz.ascend.annotation.LanguageFeature
import de.fruxz.ascend.extension.data.*
import de.fruxz.ascend.extension.forceCast
import de.fruxz.ascend.extension.forceCastOrNull
import de.fruxz.ascend.extension.objects.takeIfCastableTo
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
	val type: KType,
	val json: Json = jsonBase,
	val default: () -> T,
) {

	private fun JsonElement.toRequested(): T? {
		return json.decodeFromJsonElement(json.serializersModule.serializer(type), this)?.takeIfCastableTo()
	}

	private fun fromProvided(data: T): JsonElement =
		json.also { println("$type") }
			.encodeToJsonElement<T>(
				serializer = json.serializersModule.serializer(type).also { println("test ${it.descriptor.serialName}") },
				value = data.also { println("test2 $it ${it::class.qualifiedName}") },
			) // TODO serializer was casted to .forceCast<KSerializer<T>>() but was removed with the type introduction, still required? I dont think so

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
				put(
					key = key,
					element = fromProvided(value).also { println("result: $it") }
				)
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
@ExperimentalAscendApi
@LanguageFeature
inline fun <reified T : Any> property(
	file: Path,
	key: String,
	json: Json = jsonBase,
	noinline defaultValue: () -> T,
): JsonProperty<T> = println("T:: is -> ${T::class.qualifiedName}").let { JsonProperty<T>(
	file = file.absolute(),
	key = key,
	type = typeOf<T>(),
	json = json,
	default = defaultValue
) }