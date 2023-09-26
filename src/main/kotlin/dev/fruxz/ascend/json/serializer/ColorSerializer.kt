package dev.fruxz.ascend.json.serializer

import dev.fruxz.ascend.extension.Color
import dev.fruxz.ascend.extension.hexString
import kotlinx.serialization.ContextualSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.awt.Color
import java.awt.Color as AwtColor

/**
 *  Serializes and deserializes `java.awt.Color` objects.
 *
 *  The `ColorSerializer` class implements the `KSerializer` interface for `AwtColor` objects.
 *  It provides functionality to serialize `AwtColor` objects to an `Encoder` and deserialize them
 *  from a `Decoder`.
 */
class ColorSerializer : KSerializer<AwtColor> {
    override val descriptor: SerialDescriptor = ContextualSerializer(AwtColor::class).descriptor

    override fun serialize(encoder: Encoder, value: Color) =
        encoder.encodeString(value.hexString)

    override fun deserialize(decoder: Decoder): Color =
        Color(decoder.decodeString())

}