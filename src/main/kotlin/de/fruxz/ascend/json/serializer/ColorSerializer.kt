package de.fruxz.ascend.json.serializer

import kotlinx.serialization.ContextualSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.awt.Color
import java.awt.Color as AwtColor

class ColorSerializer : KSerializer<AwtColor> {
    override val descriptor: SerialDescriptor = ContextualSerializer(AwtColor::class).descriptor

    override fun serialize(encoder: Encoder, value: Color) =
        encoder.encodeInt(value.rgb)

    override fun deserialize(decoder: Decoder): Color =
        Color(decoder.decodeInt())

}