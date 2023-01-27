package de.fruxz.ascend.extension.security

import java.security.MessageDigest

/**
 * Hashing Utils
 * @author Sam Clarke <www.samclarke.com>
 * @license MIT
 * @since 1.0
 */
object HashUtils {

	fun md5(input: String) = hashString("MD5", input)

	fun sha512(input: String) = hashString("SHA-512", input)

	fun sha256(input: String) = hashString("SHA-256", input)

	fun sha1(input: String) = hashString("SHA-1", input)

	/**
	 * Supported algorithms on Android:
	 *
	 * Algorithm	Supported API Levels
	 * MD5          1+
	 * SHA-1	    1+
	 * SHA-224	    1-8,22+
	 * SHA-256	    1+
	 * SHA-384	    1+
	 * SHA-512	    1+
	 */
	private fun hashString(type: String, input: String): String {
		val HEX_CHARS = "0123456789ABCDEF"
		val bytes = MessageDigest
			.getInstance(type)
			.digest(input.toByteArray())
		val result = StringBuilder(bytes.size * 2)

		bytes.forEach {
			val i = it.toInt()
			result.append(HEX_CHARS[i shr 4 and 0x0f])
			result.append(HEX_CHARS[i and 0x0f])
		}

		return result.toString()
	}

}

/**
 * This enum class defines the supported hash types.
 * @author Fruxz
 * @since 1.0
 */
enum class HashType {

	MD5, SHA1, SHA256, SHA512;

	val hash: (String) -> String
		get() = when (this) {
			MD5 -> HashUtils::md5
			SHA1 -> HashUtils::sha1
			SHA256 -> HashUtils::sha256
			SHA512 -> HashUtils::sha512
		}

}