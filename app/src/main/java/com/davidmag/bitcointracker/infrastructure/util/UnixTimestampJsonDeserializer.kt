package com.davidmag.bitcointracker.infrastructure.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.threeten.bp.Instant
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import java.lang.reflect.Type

object UnixTimestampJsonDeserializer : JsonDeserializer<OffsetDateTime> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): OffsetDateTime? {
        return if(json != null)
            OffsetDateTime.ofInstant(Instant.ofEpochMilli(
                json.asJsonPrimitive.asLong * 1000),
                ZoneId.systemDefault()
            )
        else null
    }
}