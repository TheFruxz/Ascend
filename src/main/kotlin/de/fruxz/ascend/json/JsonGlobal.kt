package de.fruxz.ascend.json

import de.fruxz.ascend.extension.dump
import de.fruxz.ascend.json.serializer.AdaptiveSerializer
import de.fruxz.ascend.json.serializer.ColorSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import java.awt.Color
import kotlin.reflect.KClass

internal val runningJsonModuleModifications = mutableListOf<SerializersModuleBuilder.() -> Unit>({
    contextual(Any::class, AdaptiveSerializer())
    contextual(Color::class, ColorSerializer())
})
internal var lastKnownJsonModuleModifications = listOf<SerializersModuleBuilder.() -> Unit>()

internal val runningJsonModifications = mutableListOf<JsonBuilder.() -> Unit>()
internal var lastKnownJsonModifications = listOf<JsonBuilder.() -> Unit>()

internal var contextuals = setOf<SerializersModule>()
internal var contextualUpdate = false

private fun produceGlobalJson() = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
    coerceInputValues = true
    encodeDefaults = true
    explicitNulls = true
    allowStructuredMapKeys = true
    allowSpecialFloatingPointValues = true

    serializersModule = SerializersModule {
        include(serializersModule)

        contextuals.forEach { contextual ->
            include(contextual)
        }
        runningJsonModuleModifications.forEach {
            this.apply(it)
        }
    }

    runningJsonModifications.forEach {
        this.apply(it)
    }

}

/**
 * This value returns the current [Json] from the cached value,
 * or creates a new one, if no Json exists, or its modifications
 * are outdated.
 * @author Fruxz
 * @since 1.0
 */
@Suppress("JSON_FORMAT_REDUNDANT")
var globalJson: Json
    get() {
        if (globalJsonCache == null
            || lastKnownJsonModuleModifications != runningJsonModuleModifications
            || lastKnownJsonModifications != runningJsonModifications
            || contextualUpdate
        ) {
            contextualUpdate = false

            produceGlobalJson().let {
                globalJsonCache = it
                return it
            }

        } else
            return globalJsonCache!!
    }
    set(value) { globalJsonCache = value }

/**
 * The current cached state of the json base,
 * can change, if modification-list updates!
 * @see globalJson
 * @author Fruxz
 * @since 1.0
 */
private var globalJsonCache: Json? = null

/**
 * Adds a custom modification to the [SerializersModule] during its build process
 * for the [Json] object, used at the toJson and fromJson functions.
 * @param process the modification to the [SerializersModuleBuilder] in the building process
 * @author Fruxz
 * @since 1.0
 */
fun appendGlobalJsonModuleModification(process: SerializersModuleBuilder.() -> Unit) {
    runningJsonModuleModifications += process
}

fun <T : Any> appendGlobalJsonContextual(clazz: KClass<T>, serializer: KSerializer<T>) {
    contextuals += SerializersModule {
        contextual(clazz, serializer)
    }
    contextualUpdate = true
    globalJson.dump() // trigger update of module
}

/**
 * Adds a custom modification to the [Json] during its build process
 * for the [globalJson], used at the toJson and fromJson functions.
 * @param process the modification to the [JsonBuilder] in the building process
 * @author Fruxz
 * @since 1.0
 */
fun appendGlobalJsonModification(process: JsonBuilder.() -> Unit) {
    runningJsonModifications += process
}