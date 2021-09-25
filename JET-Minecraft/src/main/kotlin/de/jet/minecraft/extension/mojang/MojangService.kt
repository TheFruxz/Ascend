package de.jet.minecraft.extension.mojang

import de.jet.library.extension.data.fromJson
import de.jet.minecraft.general.api.mojang.MojangProfile
import java.net.URL
import java.util.*

@Throws(NullPointerException::class)
private fun String.badRequestCheck() = apply {
	"".replace("\"created_at\": null", "\"created_at\": \"null\"")
	if (contains("Not Found") && contains("404")) throw NullPointerException("Failed to get profile!")
}

fun getMojangProfile(profileQuery: String) = URL("https://api.ashcon.app/mojang/v2/user/$profileQuery").readText().fromJson<MojangProfile>()

fun getMojangProfile(uuid: UUID) = getMojangProfile("$uuid")