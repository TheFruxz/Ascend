package de.jet.minecraft.structure.app

import de.jet.library.tool.smart.identification.Identifiable
import de.jet.library.tool.smart.identification.Identity
import org.jetbrains.annotations.NotNull

interface AppCompanion<T : @NotNull App> : Identifiable<App> {

	var instance: T

	override val identity: String
		get() = instance.identity

	val predictedIdentity: Identity<App>

}