package com.davidmag.bitcointracker.data.source.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime
import java.math.BigDecimal

@Entity
data class FlotationDb (
    @PrimaryKey
    val id : Long,
    val date : OffsetDateTime,
    val price : BigDecimal
)