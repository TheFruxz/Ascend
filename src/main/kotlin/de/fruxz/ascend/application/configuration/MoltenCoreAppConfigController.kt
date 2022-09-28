package de.fruxz.ascend.application.configuration

import de.moltenKt.core.extension.data.fromJson
import de.moltenKt.core.extension.data.readJson
import de.moltenKt.core.extension.data.toJson
import de.moltenKt.core.extension.data.writeJson
import java.io.File

/**
 * This class is responsible for the configuration of applications.
 * It manages, stores and loads the configuration of them.
 * @author Fruxz
 * @since 1.0
 */
object MoltenCoreAppConfigController {

	/**
	 * The general configuration file of the MoltenKT management system itself
	 * @author Fruxz
	 * @since 1.0
	 */
	private val moltenConfig = File("molten.apps.json")

	/**
	 * The current configuration state of the application
	 * @author Fruxz
	 * @since 1.0
	 */
	private lateinit var configState: de.fruxz.ascend.application.configuration.MoltenCoreAppConfiguration // TODO multi application support

	/**
	 * Gets the current or the loaded configuration state of the application
	 * @return the current or saved state of the config
	 * @author Fruxz
	 * @since 1.0
	 */
	fun get(): de.fruxz.ascend.application.configuration.MoltenCoreAppConfiguration {
		return if (de.fruxz.ascend.application.configuration.MoltenCoreAppConfigController::configState.isInitialized) {
			de.fruxz.ascend.application.configuration.MoltenCoreAppConfigController.configState
		} else {
			if (de.fruxz.ascend.application.configuration.MoltenCoreAppConfigController.moltenConfig.exists()) {
				de.fruxz.ascend.application.configuration.MoltenCoreAppConfigController.configState = de.fruxz.ascend.application.configuration.MoltenCoreAppConfigController.moltenConfig.readJson()
				de.fruxz.ascend.application.configuration.MoltenCoreAppConfigController.configState
			} else {
				de.fruxz.ascend.application.configuration.MoltenCoreAppConfigController.set()
				de.fruxz.ascend.application.configuration.MoltenCoreAppConfigController.get()
			}
		}
	}

	/**
	 * Changes the current configuration state of the application to [content]
	 * @author Fruxz
	 * @since 1.0
	 */
	fun set(content: de.fruxz.ascend.application.configuration.MoltenCoreAppConfiguration = de.fruxz.ascend.application.configuration.MoltenCoreAppConfiguration(
		apps = emptyList()
	)
	) {
		de.fruxz.ascend.application.configuration.MoltenCoreAppConfigController.configState = content
		de.fruxz.ascend.application.configuration.MoltenCoreAppConfigController.moltenConfig.writeJson(content)
	}

	/**
	 * This function adds an app to the configuration with the module
	 * values, which often use the default values.
	 * @param appModule the module of the app, to get saved
	 * @author Fruxz
	 * @since 1.0
	 */
	fun addApp(appModule: de.fruxz.ascend.application.configuration.MoltenCoreAppConfigModule) {
		val get = de.fruxz.ascend.application.configuration.MoltenCoreAppConfigController.get()
		if (get.apps.none { it.identity == appModule.identity }) {
			de.fruxz.ascend.application.configuration.MoltenCoreAppConfigController.set(get.let {
				return@let it.copy(apps = (it.apps + appModule))
			})
		}
	}

	/**
	 * This function returns the [MoltenCoreAppConfigModule] of the searched
	 * MoltenKT app [app] or returns null, if the module is not stored.
	 * The module contains, most importantly, the configured path
	 * of the files, which the [app] uses.
	 * @param app is the searched app
	 * @return the found module or null
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getApp(app: de.fruxz.ascend.application.configuration.MoltenCoreApp): de.fruxz.ascend.application.configuration.MoltenCoreAppConfigModule? {
		return de.fruxz.ascend.application.configuration.MoltenCoreAppConfigController.get().apps.firstOrNull { it.identity == app.identity }
	}

}