package de.fruxz.ascend.json.database

import de.fruxz.ascend.extension.forceCast
import de.fruxz.ascend.extension.forceCastOrNull
import de.fruxz.ascend.json.globalJson
import de.fruxz.ascend.json.toJsonString
import kotlinx.serialization.serializer
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.currentDialect
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.typeOf

/**
 * This class defines a column type, which transform its value via
 * the [globalJson] of ascends json system.
 * The sql type for this column is 'TEXT' be default, but can vary by the dialect.
 * @author Fruxz
 * @since 1.0
 */
class DynamicJsonColumnType<T : Any>(private val type: KType) : ColumnType() {

	constructor(clazz: KClass<T>) : this(clazz.createType())

	constructor(
		clazz: KClass<T>,
		arguments: List<KTypeProjection> = emptyList(),
		nullable: Boolean = false,
		annotations: List<Annotation> = emptyList(),
	) : this(clazz.createType(arguments, nullable, annotations))

	override fun sqlType(): String =
		currentDialect.dataTypeProvider.textType()

	override fun nonNullValueToString(value: Any): String =
		"'${value.toJsonString()}'"

	override fun valueFromDB(value: Any): T = when (value) {
		is String -> globalJson.decodeFromString(globalJson.serializersModule.serializer(type), "$value".removeSurrounding("'")).forceCast()
		else -> value.forceCast()
	}

	override fun notNullValueToDB(value: Any): String =
		"'${globalJson.encodeToString(globalJson.serializersModule.serializer(type), value.forceCastOrNull<T?>())}'"

}

/**
 * This function defines a column type, which transforms via the json system,
 * provided by [globalJson] and [DynamicJsonColumnType].
 * @author Fruxz
 * @since 1.0
 */
fun <T : Any> Table.dynamicJson(name: String, clazz: KClass<T>): Column<T> = registerColumn(name, DynamicJsonColumnType(clazz))

fun <T : Any> Table.dynamicJson(name: String, type: KType): Column<T> = registerColumn(name, DynamicJsonColumnType<T>(type))

/**
 * This function defines a column type, which transforms via the json system,
 * provided by [globalJson] and [DynamicJsonColumnType].
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Any> Table.dynamicJson(name: String): Column<T> = dynamicJson(name, typeOf<T>())