package com.davidmag.bitcointracker.domain.model

import org.threeten.bp.OffsetDateTime
import java.math.BigDecimal

data class Flotation (
    val date : OffsetDateTime,
    val value : BigDecimal
)