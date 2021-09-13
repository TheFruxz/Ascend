package de.jet.minecraft.app.interchange

import de.jet.library.extension.collection.replace
import de.jet.library.tool.smart.Identity
import de.jet.minecraft.app.JetCache
import de.jet.minecraft.extension.app
import de.jet.minecraft.extension.display.BOLD
import de.jet.minecraft.extension.display.GREEN
import de.jet.minecraft.extension.display.RED
import de.jet.minecraft.extension.display.message
import de.jet.minecraft.extension.display.notification
import de.jet.minecraft.extension.lang
import de.jet.minecraft.extension.system
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.command.CompletionVariable
import de.jet.minecraft.structure.command.Interchange
import de.jet.minecraft.structure.command.InterchangeResult
import de.jet.minecraft.structure.command.InterchangeResult.SUCCESS
import de.jet.minecraft.structure.command.InterchangeResult.WRONG_USAGE
import de.jet.minecraft.structure.command.buildCompletion
import de.jet.minecraft.structure.command.isRequired
import de.jet.minecraft.structure.command.live.InterchangeAccess
import de.jet.minecraft.structure.command.mustMatchOutput
import de.jet.minecraft.structure.command.next
import de.jet.minecraft.structure.command.plus
import de.jet.minecraft.structure.service.Service
import de.jet.minecraft.tool.display.message.Transmission.Level.FAIL
import de.jet.minecraft.tool.display.message.Transmission.Level.INFO
import java.util.*
import kotlin.time.Duration

class ServiceInterchange(vendor: App = system) : Interchange(vendor, "service", requiresAuthorization = true, completion = buildCompletion {
	next(setOf("start", "stop", "restart", "list", "unregister", "reset")) isRequired true mustMatchOutput true
	next(CompletionVariable(vendor, "Service", true) {
		JetCache.registeredServices.map { it.identity }
	}) + "*" + CompletionVariable(vendor, "Service-Group", true) {
		JetCache.registeredApplications.map { "${it.identity}:*" }
	} mustMatchOutput true isRequired false
}) {

	override val execution: InterchangeAccess.() -> InterchangeResult = interchange@{

		if (parameters.size == 1 && parameters.first().equals("list", true)) {

			lang("interchange.internal.service.list.header")
				.message(executor).display()

			JetCache.registeredServices.forEach { service: Service ->

				lang("interchange.internal.service.list.line")
					.replace("[service]", service.identity)
					.replace("[enabled]" to if (service.isRunning) "$GREEN${BOLD}ONLINE" else "$RED${BOLD}OFFLINE")
					.replace("[activeSince]" to Duration.milliseconds(Calendar.getInstance().timeInMillis - (service.controller?.startTime ?: Calendar.getInstance()).timeInMillis).toString())
					.message(executor).display()

			}

		} else if (parameters.size == 2) {
			val services = if (parameters.last() == "*") {
				JetCache.registeredServices
			} else if (parameters.last().endsWith(":*")) {
				val searchApp = Identity<App>(parameters.last().removeSuffix(":*"))
				JetCache.registeredServices.filter { it.vendorIdentity == searchApp }
			} else
				JetCache.registeredServices.firstOrNull { it.identity == parameters.last() }?.let { listOf(it) } ?: emptyList()

			if (services.isNotEmpty()) {

				when (parameters.first().lowercase()) {

					"start" -> {
						services.forEach { currentService ->
							try {

								app(currentService.vendor).start(currentService)

								lang("interchange.internal.service.serviceStarted")
									.replace("[id]" to currentService.identity)
									.notification(INFO, executor).display()

							} catch (exception: IllegalStateException) {
								lang("interchange.internal.service.serviceAlreadyOnline")
									.replace("[id]" to currentService.identity)
									.notification(FAIL, executor).display()
							}
						}
					}

					"stop" -> {
						services.forEach { currentService ->
							try {

								app(currentService.vendor).stop(currentService)

								lang("interchange.internal.service.serviceStopped")
									.replace("[id]" to currentService.identity)
									.notification(INFO, executor).display()

							} catch (exception: IllegalStateException) {
								lang("interchange.internal.service.serviceAlreadyOffline")
									.replace("[id]" to currentService.identity)
									.notification(FAIL, executor).display()
							}
						}
					}

					"restart" -> {
						services.forEach { currentService ->
							app(currentService.vendor).restart(currentService)

							lang("interchange.internal.service.serviceRestarted")
								.replace("[id]" to currentService.identity)
								.notification(INFO, executor).display()
						}
					}

					"unregister" -> {
						services.forEach { currentService ->
							try {

								app(currentService.vendor).stop(currentService)

								lang("interchange.internal.service.serviceUnregistered")
									.replace("[id]" to currentService.identity)
									.notification(INFO, executor).display()

							} catch (exception: IllegalStateException) {
								lang("interchange.internal.service.serviceNotFound")
									.replace("[id]" to currentService.identity)
									.notification(FAIL, executor).display()
							}
						}
					}

					"reset" -> {
						services.forEach { currentService ->
							try {

								app(currentService.vendor).reset(currentService)

								lang("interchange.internal.service.serviceReset")
									.replace("[id]" to currentService.identity)
									.notification(INFO, executor).display()

							} catch (exception: IllegalStateException) {
								lang("interchange.internal.service.serviceNotFound")
									.replace("[id]" to currentService.identity)
									.notification(FAIL, executor).display()
							}
						}
					}

					else -> return@interchange WRONG_USAGE

				}

			} else
				lang("interchange.internal.service.serviceNotFound")
					.replace("[id]" to parameters.last())
					.notification(FAIL, executor).display()

		} else
			return@interchange WRONG_USAGE

		return@interchange SUCCESS
	}

}