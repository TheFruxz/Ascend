package de.fruxz.ascend.tree

import de.fruxz.ascend.tool.smart.identification.Identifiable
import de.fruxz.ascend.tool.smart.identification.UUID

open class TreeBranchType(override val identity: String = UUID.randomString()) : Identifiable<TreeBranchType> {

    companion object {

        @JvmStatic
        val OBJECT = TreeBranchType("OBJECT")

        @JvmStatic
        val DIRECTORY = TreeBranchType("DIRECTORY")

    }

}