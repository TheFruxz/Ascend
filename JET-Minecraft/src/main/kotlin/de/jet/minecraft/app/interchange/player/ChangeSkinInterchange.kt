package de.jet.minecraft.app.interchange.player

import de.jet.library.extension.collection.replaceVariables
import de.jet.library.extension.paper.getPlayer
import de.jet.minecraft.extension.display.notification
import de.jet.minecraft.extension.get
import de.jet.minecraft.extension.lang
import de.jet.minecraft.extension.mojang.applySkin
import de.jet.minecraft.extension.mojang.resetSkin
import de.jet.minecraft.extension.system
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.command.CompletionVariable
import de.jet.minecraft.structure.command.Interchange
import de.jet.minecraft.structure.command.InterchangeExecutorType.PLAYER
import de.jet.minecraft.structure.command.InterchangeResult
import de.jet.minecraft.structure.command.InterchangeResult.SUCCESS
import de.jet.minecraft.structure.command.buildCompletion
import de.jet.minecraft.structure.command.isRequired
import de.jet.minecraft.structure.command.label
import de.jet.minecraft.structure.command.live.InterchangeAccess
import de.jet.minecraft.structure.command.mustMatchOutput
import de.jet.minecraft.structure.command.next
import de.jet.minecraft.structure.command.plus
import de.jet.minecraft.tool.display.message.Transmission.Level.*
import org.bukkit.entity.Player

class ChangeSkinInterchange(vendor: App = system) : Interchange(
	vendor = vendor,
	label = "changeskin",
	requiresAuthorization = true,
	requiredExecutorType = PLAYER,
	completion = buildCompletion {
		next(CompletionVariable.PLAYER_NAME) isRequired true mustMatchOutput true
		next("/reset") plus CompletionVariable.PLAYER_NAME isRequired true mustMatchOutput false label "skin-player-name"
	},

) {
	override val execution: InterchangeAccess.() -> InterchangeResult = {
		val player = executor as Player
		val target = getPlayer(parameters.first())

		fun notifyTarget() {
			if (player != target) {
				target?.let {
					lang["interchange.internal.changeskin.remote"]
						.notification(INFO, it).display()
				}
			}
		}

		if (target != null) {

			when (parameters.first().uppercase()) {
				"/RESET" -> {

					executor.resetSkin()

					lang["interchange.internal.changeskin.reset"]
						.replaceVariables("player" to target.name)
						.notification(APPLIED, executor).display()

					notifyTarget()

				}
				else -> {

					try {

						executor.applySkin(parameters.last())

						lang["interchange.internal.changeskin.change"]
							.replaceVariables("skin" to parameters.last(), "player" to target.name)
							.notification(APPLIED, executor).display()

						notifyTarget()

					} catch (exception: NullPointerException) {

						lang["interchange.internal.changeskin.failed"]
							.replaceVariables("target" to parameters.last())
							.notification(FAIL, executor).display()

					}

				}
			}

		}

		SUCCESS
	}

}