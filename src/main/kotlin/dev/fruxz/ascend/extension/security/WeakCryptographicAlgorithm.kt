package dev.fruxz.ascend.extension.security

@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "This Cryptographic algorithm is weak and should not be used."
)
annotation class WeakCryptographicAlgorithm(
    val message: String = "This Cryptographic algorithm is weak and should not be used."
)
