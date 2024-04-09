package dev.fruxz.ascend.tool

import dev.fruxz.ascend.extension.dump
import dev.fruxz.ascend.extension.forceCast
import dev.fruxz.ascend.json.serializer.AdaptiveSerializer
import dev.fruxz.ascend.json.serializer.ColorSerializer
import dev.fruxz.ascend.tool.smart.Modification
import dev.fruxz.ascend.tool.smart.modification
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import java.awt.Color
import kotlin.reflect.KClass
import dev.fruxz.ascend.json.globalJson

/**
 * This object manages a json instance, which can be used for every purpose.
 * It is designed to adapt to new changes and the current environment, but also
 * being highly customizable.
 * It automatically updates the json instance if something has changed.
 * @see globalJson is an easy way to access the current json instance from anywhere.
 * @author Fruxz
 * @since 2023.3
 */
object JsonManager {

    private val moduleModifications = mutableListOf<Modification<SerializersModuleBuilder>>(modification {
        contextual(Any::class, AdaptiveSerializer())
        contextual(Color::class, ColorSerializer())
    })
    private var moduleStateHash: String = moduleModifications.hashCode().toString(16)

    private val jsonModifications = mutableListOf<Modification<JsonBuilder>>()
    private var jsonStateHash: String = jsonModifications.hashCode().toString(16)

    private var contextuals = setOf<SerializersModule>()
    private var contextualUpdate = false

    /**
     * Updates the module modifications hash, JSON modifications hash, and contextual update flag.
     * This function should be called after any modification to the JSON configuration has been made.
     * @author Fruxz
     * @since 2023.3
     */
    private fun pushUpdate() {
        moduleStateHash = moduleModifications.hashCode().toString(16)
        jsonStateHash = jsonModifications.hashCode().toString(16)
        contextualUpdate = false
    }

    private fun computeJson() = Json {
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
            moduleModifications.forEach {
                this.apply(it::modify)
            }
        }

        jsonModifications.forEach {
            this.apply(it::modify)
        }

    }

    /**
     * The current cached state of the json base,
     * can change, if modification-list updates!
     * @see json
     * @author Fruxz
     * @since 2023.1
     */
    private var cachedJson: Json? = null

    /**
     * This is the base json, which Ascend provides for you.
     * This value returns the current [Json] from the cached value,
     * or creates a new one, if no Json exists, or its modifications
     * are outdated.
     * @author Fruxz
     * @since 2023.1
     */
    var json: Json
        get() {

            when (
                cachedJson != null &&
                moduleStateHash == moduleModifications.hashCode().toString(16) &&
                jsonStateHash == jsonModifications.hashCode().toString(16) &&
                !contextualUpdate
            ) {
                true -> return cachedJson!!
                else -> {

                    pushUpdate()

                    return computeJson().also {
                        cachedJson = it
                    }

                }
            }

        }
        set(value) { cachedJson = value }

    /**
     * Adds a custom modification to the [SerializersModule] during its build process
     * for the [Json] object, used at the toJson and fromJson functions.
     * @param process the modification to the [SerializersModuleBuilder] in the building process
     * @author Fruxz
     * @since 2023.1
     */
    fun addModuleModification(process: Modification<SerializersModuleBuilder>) {
        moduleModifications += process
    }

    fun <T : Any> addContextual(clazz: KClass<T>, serializer: KSerializer<out T>) {
        contextuals += SerializersModule {
            contextual(clazz, serializer = serializer.forceCast())
        }
        contextualUpdate = true
        json.dump() // trigger update of module
    }

    /**
     * Adds a custom modification to the [Json] during its build process
     * for the [json], used at the toJson and fromJson functions.
     * @param process the modification to the [JsonBuilder] in the building process
     * @author Fruxz
     * @since 2023.1
     */
    fun addJsonModification(process: Modification<JsonBuilder>) {
        jsonModifications += process
    }

}