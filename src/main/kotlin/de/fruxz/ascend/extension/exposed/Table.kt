package de.fruxz.ascend.extension.exposed

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table

/**
 * This function utilizes the [SchemaUtils.create] function to create a table.
 * @author Fruxz
 * @since 1.0
 */
@Deprecated("A better and more reliable way is possible!", ReplaceWith("initialize()"))
fun Table.createTable() = SchemaUtils.create(this)

/**
 * This function creates or updates [this] table in the database.
 * This utilizes the [SchemaUtils.createMissingTablesAndColumns] function.
 * @author Fruxz
 * @since 1.0
 */
fun Table.initialize(withLogs: Boolean = true) =
	SchemaUtils.createMissingTablesAndColumns(this, withLogs = withLogs)

/**
 * This function creates or updates the [tables] in the database.
 * This utilizes the [SchemaUtils.createMissingTablesAndColumns] function.
 * @author Fruxz
 * @since 1.0
 */
fun initializeTables(vararg tables: Table, withLogs: Boolean = true) =
	SchemaUtils.createMissingTablesAndColumns(*tables, withLogs = withLogs)