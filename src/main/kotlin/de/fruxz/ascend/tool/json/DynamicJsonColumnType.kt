package de.fruxz.ascend.tool.json

import de.fruxz.ascend.extension.data.jsonBase
import de.fruxz.ascend.extension.data.toJsonString
import kotlinx.serialization.serializer
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.currentDialect
import java.sql.ResultSet
import kotlin.reflect.KClass
import kotlin.reflect.full.createType

/**
 * This class defines a column type, which transform its value via
 * the [jsonBase] of ascends json system.
 * @author Fruxz
 * @since 1.0
 */
class DynamicJsonColumnType<T : Any>(private val clazz: KClass<T>) : ColumnType() {
	override fun sqlType() = currentDialect.dataTypeProvider.textType()

	private inline fun <reified A : Any> test(string: String): A {
		return jsonBase.decodeFromString(jsonBase.serializersModule.serializer(), string)
	}

	override fun nonNullValueToString(value: Any) = value.toJsonString()

	override fun valueFromDB(value: Any): T {
		return jsonBase.decodeFromString(jsonBase.serializersModule.serializer(clazz.createType()), "$value") as T
	}

	override fun readObject(rs: ResultSet, index: Int): Any? {
		return super.readObject(rs, index)
	}

	override fun notNullValueToDB(value: Any) = valueToDB(value) ?: error("Value is null")

	override fun valueToDB(value: Any?): Any? {
		return jsonBase.encodeToString(jsonBase.serializersModule.serializer(clazz.createType()), value as T)
	}

}

/**
 * This function defines a column type, which transforms via the json system,
 * provided by [jsonBase] and [DynamicJsonColumnType].
 * @author Fruxz
 * @since 1.0
 */
fun <T : Any> Table.dynamicJson(name: String, clazz: KClass<T>): Column<T> = registerColumn(name, DynamicJsonColumnType(clazz))

/**
 * This function defines a column type, which transforms via the json system,
 * provided by [jsonBase] and [DynamicJsonColumnType].
 * @author Fruxz
 * @since 1.0
 */
inline fun <reified T : Any> Table.dynamicJson(name: String): Column<T> = dynamicJson(name, T::class)