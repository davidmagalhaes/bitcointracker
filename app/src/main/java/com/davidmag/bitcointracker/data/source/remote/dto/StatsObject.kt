package com.davidmag.bitcointracker.data.source.remote.dto

import com.google.gson.annotations.SerializedName
import org.threeten.bp.OffsetDateTime
import java.math.BigDecimal

data class StatsObject (
    @SerializedName("market_price_usd")
    val price : BigDecimal
)