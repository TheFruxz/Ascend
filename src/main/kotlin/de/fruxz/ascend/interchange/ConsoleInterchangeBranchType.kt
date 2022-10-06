package de.fruxz.ascend.interchange

import de.fruxz.ascend.extension.data.buildRandomTag
import de.fruxz.ascend.tree.TreeBranchType

/**
 * This class defines the branch types of the console interchanges.
 * @author Fruxz
 * @since 1.0
 */
class ConsoleInterchangeBranchType(identity: String = buildRandomTag(hashtag = false)) : TreeBranchType(identity) {

	companion object {

		/**
		 * The general branch type.
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		val OBJECT = ConsoleInterchangeBranchType("OBJECT")

		/**
		 * The branch type of empty interchanges, that only define a bridge to other interchanges.
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		val BRIDGE = ConsoleInterchangeBranchType("BRIDGE")

	}

}