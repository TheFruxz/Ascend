package de.fruxz.ascend.application.configuration

import de.fruxz.ascend.application.tag.Version
import de.fruxz.ascend.application.tag.version
import de.fruxz.ascend.tool.smart.identification.Identifiable

/**
 * This class represents the raw data of a Ascend application.
 * @param identity is the unique identifier of the application, used to manage configurations and resources.
 * @param version is the version of the application.
 * @author Fruxz
 * @since 1.0
 */
open class AscendApp(
	override val identity: String,
	open val version: Version = 1.0.version,
) : Identifiable<AscendApp>
