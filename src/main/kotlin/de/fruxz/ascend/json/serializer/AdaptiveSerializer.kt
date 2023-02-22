package de.fruxz.ascend.json.serializer

import de.fruxz.ascend.extension.forceCast
import kotlinx.serialization.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

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