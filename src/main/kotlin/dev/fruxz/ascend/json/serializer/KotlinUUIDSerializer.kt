@file:OptIn(ExperimentalUuidApi::class)

package dev.fruxz.ascend.json.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object KotlinUUIDSerializer : KSerializer<Uuid> {
    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Uuid =
        Uuid.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: Uuid) =
        encoder.encodeString(value.toHexDashString())

}
