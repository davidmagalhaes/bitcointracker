package com.davidmag.bitcointracker.data.source.remote.dto

import org.threeten.bp.OffsetDateTime
import java.math.BigDecimal

data class StatsObject (
    val date : OffsetDateTime = OffsetDateTime.now(),
    val price : BigDecimal
)