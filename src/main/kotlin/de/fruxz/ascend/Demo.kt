package de.fruxz.ascend

import de.fruxz.ascend.extension.data.jsonBase
import de.fruxz.ascend.extension.data.randomInt
import de.fruxz.ascend.extension.getHomePath
import de.fruxz.ascend.tool.delegate.property
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.io.path.div
import kotlin.reflect.KClass
import kotlin.reflect.full.superclasses

@Serializable
data class Demo(
    val name: String,
    val age: Int,
)

val path = getHomePath() / "demo.json"

var test by property(path, "test") { Demo("Test-Name", randomInt(1..20)) }
var primitive by property(path, "primitve") { 20 }
var list by property(path, "list") { listOf("Apple", "Banana", "Pineapple") }

fun main() {

    try {
        test = test.copy(age = randomInt(1..20))
        primitive = randomInt(1..100)
        //list = setOf("Apple", "Banana", "Pineapple", "Orange")
    } catch (e: Exception) {
        println("catch")
        e.printStackTrace()
    }

}