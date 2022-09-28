package de.fruxz.ascend.extension.app

import de.fruxz.ascend.application.configuration.AscendApp
import de.fruxz.ascend.application.runtime.AscendAppRuntime
import de.fruxz.ascend.application.tag.Version
import de.fruxz.ascend.application.tag.version

/**
 * This function creates and runs a new application run
 * @see AscendApp
 * @param appName is the name of the running application
 * @param appVersion is the version of the running application
 * @param appInstance is the [AscendApp] on which the [AscendAppRuntime] is based
 * @param runtime is the process, which get [apply]'d after the initialization of the [AscendAppRuntime]
 * @author Fruxz
 * @since 1.0
 */
fun runApp(appName: String, appVersion: Version = 1.0.version, appInstance: de.fruxz.ascend.application.configuration.AscendApp = de.fruxz.ascend.application.configuration.AscendApp(
	appName,
	appVersion
), runtime: AscendAppRuntime.() -> Unit) {
	with(appInstance) { AscendAppRuntime(this.identity, this.version) }.apply {
		init()
	}.apply(runtime)
}