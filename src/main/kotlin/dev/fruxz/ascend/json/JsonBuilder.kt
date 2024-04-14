package dev.fruxz.ascend.json

import dev.fruxz.ascend.annotation.RefactoringCandidate
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.buildJsonObject


/**
 * This function puts every item from the [jsonObject] int the current [JsonObjectBuilder].
 * @author Fruxz
 * @since 2023.1
 */
// TODO: "As discussed in Kotlin/kotlinx.serialization#2308, this may be native in the future!"
@RefactoringCandidate
fun JsonObjectBuilder.putAll(jsonObject: JsonObject) = jsonObject.entries.forEach {
    this.put(it.key, it.value)
}

/**
 * This function builds a new [JsonObject] using the [buildJsonObject] function, and
 * [JsonObjectBuilder.putAll] the [base] (if not-null) as the first action, to be used
 * as the base.
 * @author Fruxz
 * @since 2023.1
 */
fun buildJsonObject(base: JsonObject?, builderAction: JsonObjectBuilder.() -> Unit): JsonObject = buildJsonObject {
    base?.let { putAll(it) }
    apply(builderAction)
}