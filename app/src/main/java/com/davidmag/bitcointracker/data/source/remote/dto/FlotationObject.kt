package com.davidmag.bitcointracker.data.source.remote.dto

import com.google.gson.annotations.SerializedName
import org.threeten.bp.OffsetDateTime
import java.math.BigDecimal

data class FlotationObject (
    @SerializedName("x")
    val date : OffsetDateTime,
    @SerializedName("y")
    val price : BigDecimal
)