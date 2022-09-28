package de.fruxz.ascend.application.configuration

import de.fruxz.ascend.tool.smart.identification.Identifiable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This data class represents the current configured
 * state of the application.
 * @param [identity] is the unique identity of the application.
 * @param appFileFolderPath is the path to the application file folder.
 * @author Fruxz
 * @since 1.0
 */
@Serializable
@SerialName("AscendAppModule")
data class AscendAppConfigModule(
	override val identity: String,
	val appFileFolderPath: String,
) : Identifiable<AscendAppConfigModule> {

	companion object {

		/**
		 * This function automatically generate a new configuration module
		 * from the provided [app].
		 * @param [app] is the application to generate the configuration module from.
		 * @return the generated configuration module.
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		fun autoGenerateFromApp(app: de.fruxz.ascend.application.configuration.AscendApp) =
			AscendAppConfigModule(app.identity, "APP_${app.identity}/")

	}

}
