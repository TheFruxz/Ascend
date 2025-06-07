package dev.fruxz.ascend.extension.exposed

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.SchemaUtils

/**
 * This function creates or updates [this] table structure in the database.
 * This utilizes the [SchemaUtils.createMissingTablesAndColumns] function.
 * @author Fruxz
 * @since 2023.1
 */
@Suppress("DEPRECATION")
@Deprecated("Deprecated by JetBrains Exposed, see SchemaUtils.createMissingTablesAndColumns")
fun Table.initialize(withLogs: Boolean = true) =
	SchemaUtils.createMissingTablesAndColumns(this, withLogs = withLogs)

/**
 * This function creates or updates the [tables] structure in the database.
 * This utilizes the [SchemaUtils.createMissingTablesAndColumns] function.
 * @author Fruxz
 * @since 2023.1
 */
@Suppress("DEPRECATION")
@Deprecated("Deprecated by JetBrains Exposed, see SchemaUtils.createMissingTablesAndColumns")
fun initializeTables(vararg tables: Table, withLogs: Boolean = true) =
	SchemaUtils.createMissingTablesAndColumns(*tables, withLogs = withLogs)