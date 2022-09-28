package de.fruxz.ascend.application.extension

import de.moltenKt.core.extension.input.requestTerminalInterchangeInput
import de.moltenKt.core.interchange.ConsoleInterchangeBranch
import kotlin.reflect.KFunction1

/**
 * Extension to easily create terminal input requests inside an application
 * @author Fruxz
 * @since 1.0
 */
object ConsoleInterchangeExtension : AppExtension<MutableList<ConsoleInterchangeBranch>, Unit, Unit> {

	override val identity = "TerminalConsole"
	override val parallelRunAllowed = false
	override val runtimeAccessor: KFunction1<MutableList<ConsoleInterchangeBranch>.() -> Unit, Unit> = this::runTerminal

	/**
	 * This function executes the terminal input request
	 * @param process The process of building & providing the terminal input request interchanges
	 * @author Fruxz
	 * @since 1.0
	 */
	private fun runTerminal(process: (MutableList<ConsoleInterchangeBranch>.() -> Unit)) =
		requestTerminalInterchangeInput(*buildList(process).toTypedArray())

}