package de.fruxz.ascend.serialization

import de.fruxz.ascend.extension.forceCast
import de.fruxz.ascend.extension.tryOrNull
import kotlinx.serialization.ContextualSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer

/**
 * This serializer helps to serialize objects, which are
 * hidden behind an [Any] type, but are indeed of a specific
 * serializable type.
 * @throws IllegalArgumentException if no supported serializer is found
 */
@ExperimentalSerializationApi
class AdaptiveSerializer : KSerializer<Any> {
	override val descriptor = ContextualSerializer(Any::class, null, emptyArray()).descriptor

	@OptIn(InternalSerializationApi::class)
	override fun serialize(encoder: Encoder, value: Any) {
		encoder.serializersModule.getContextual(value::class) ?: value::class.serializer().let { lookupResult ->
			encoder.encodeSerializableValue(lookupResult.forceCast<KSerializer<Any>>(), value)
		}
	}

	override fun deserialize(decoder: Decoder): Any {
		throw IllegalArgumentException("[AdaptiveSerializer Emergency-Solution] Unsupported type 'Any'!")
	}

}