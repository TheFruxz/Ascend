package de.fruxz.ascend.application.configuration

import de.fruxz.ascend.extension.data.readJson
import de.fruxz.ascend.extension.data.writeJson
import java.io.File

/**
 * This class is responsible for the configuration of applications.
 * It manages, stores and loads the configuration of them.
 * @author Fruxz
 * @since 1.0
 */
object AscendAppConfigController {

	/**
	 * The general configuration file of the Ascend management system itself
	 * @author Fruxz
	 * @since 1.0
	 */
	private val ascendConfig = File("ascend.apps.json")

	/**
	 * The current configuration state of the application
	 * @author Fruxz
	 * @since 1.0
	 */
	private lateinit var configState: AscendAppConfiguration // TODO multi application support

	/**
	 * Gets the current or the loaded configuration state of the application
	 * @return the current or saved state of the config
	 * @author Fruxz
	 * @since 1.0
	 */
	fun get(): AscendAppConfiguration {
		return if (AscendAppConfigController::configState.isInitialized) {
			configState
		} else {
			if (ascendConfig.exists()) {
				configState = ascendConfig.readJson()
				configState
			} else {
				set()
				get()
			}
		}
	}

	/**
	 * Changes the current configuration state of the application to [content]
	 * @author Fruxz
	 * @since 1.0
	 */
	fun set(content: AscendAppConfiguration = AscendAppConfiguration(
		apps = emptyList()
	)
	) {
		configState = content
		ascendConfig.writeJson(content)
	}

	/**
	 * This function adds an app to the configuration with the module
	 * values, which often use the default values.
	 * @param appModule the module of the app, to get saved
	 * @author Fruxz
	 * @since 1.0
	 */
	fun addApp(appModule: AscendAppConfigModule) {
		val get = get()
		if (get.apps.none { it.identity == appModule.identity }) {
			set(get.let {
				return@let it.copy(apps = (it.apps + appModule))
			})
		}
	}

	/**
	 * This function returns the [AscendAppConfigModule] of the searched
	 * Ascend app [app] or returns null, if the module is not stored.
	 * The module contains, most importantly, the configured path
	 * of the files, which the [app] uses.
	 * @param app is the searched app
	 * @return the found module or null
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getApp(app: AscendApp): AscendAppConfigModule? {
		return get().apps.firstOrNull { it.identity == app.identity }
	}

}