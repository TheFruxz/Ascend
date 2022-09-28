package de.fruxz.ascend.application.configuration

import de.fruxz.ascend.application.tag.Version
import de.fruxz.ascend.application.tag.version
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This data class represents the configuration of Ascend.
 * This configuration contains the version of the Ascend,
 * that created the configuration file. (compatibility)
 * and also a list of [AscendAppConfigModule]s.
 * @param ascendVersion the Ascend-instance, that created the configuration file.
 * @param apps the list of [AscendAppConfigModule]s, used/configured to run with MoltenKT.
 * @author Fruxz
 * @since 1.0
 */
@Serializable
@SerialName("appConfiguration")
data class AscendAppConfiguration(
	val ascendVersion: Version = 1.0.version,
	val apps: List<AscendAppConfigModule>,
)
