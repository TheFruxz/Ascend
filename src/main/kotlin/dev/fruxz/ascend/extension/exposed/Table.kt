package dev.fruxz.ascend.extension.exposed

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.SchemaUtils

/**
 * This function creates [this] table structure in the database.
 * This utilizes the [SchemaUtils.create] function.
 * @author Fruxz
 * @since 2025.6
 */
fun Table.initialize(inBatch: Boolean = false) =
	SchemaUtils.create(this, inBatch = inBatch)

/**
 * This function creates the [tables] structure in the database.
 * This utilizes the [SchemaUtils.create] function.
 * @author Fruxz
 * @since 2025.6
 */
fun initializeTables(vararg tables: Table, inBatch: Boolean = false) =
	SchemaUtils.create(*tables, inBatch = inBatch)