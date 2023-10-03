package dev.fruxz.ascend.json

import kotlinx.serialization.json.Json

/**
 * This is the base json, which Ascend provides.
 * This value returns the current [Json] from the cached value,
 * or creates a new one, if no Json exists, or its modifications
 * are outdated.
 * ***Attention. Changing this var, changes [JsonManager.json]!***
 * @see JsonManager
 * @author Fruxz
 * @since 2023.1
 */
var globalJson: Json by JsonManager::json