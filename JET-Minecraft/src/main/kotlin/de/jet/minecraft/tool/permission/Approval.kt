package de.jet.minecraft.tool.permission

import de.jet.library.tool.smart.identification.Identifiable
import de.jet.minecraft.structure.app.App
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.permissions.Permissible

@Serializable
@SerialName("permissionApproval")
data class Approval(
	@SerialName("permission") private val id: String
) : Identifiable<Approval> {

	override var identity: String = id
		private set

	fun hasApproval(permissible: Permissible) =
		permissible.hasPermission(identity)

	operator fun div(value: Any?) {
		identity += ".$value"
	}

	companion object {

		fun fromApp(vendor: Identifiable<App>, subPermission: String) =
			Approval("${vendor.identity}.$subPermission")

	}

}