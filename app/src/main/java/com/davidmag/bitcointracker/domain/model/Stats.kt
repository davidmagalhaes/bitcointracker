package com.davidmag.bitcointracker.domain.model

import org.threeten.bp.OffsetDateTime
import java.math.BigDecimal

data class Stats (
    val updatedAt : OffsetDateTime,
    val price : BigDecimal
)