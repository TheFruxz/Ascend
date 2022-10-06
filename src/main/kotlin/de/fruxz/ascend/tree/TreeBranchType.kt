package de.fruxz.ascend.tree

import de.fruxz.ascend.extension.data.buildRandomTag
import de.fruxz.ascend.tool.smart.identification.Identifiable

open class TreeBranchType(override val identity: String = buildRandomTag(hashtag = false)) : Identifiable<TreeBranchType> {

    companion object {

        @JvmStatic
        val OBJECT = TreeBranchType("OBJECT")

        @JvmStatic
        val DIRECTORY = TreeBranchType("DIRECTORY")

    }

}