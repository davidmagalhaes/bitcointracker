package com.davidmag.bitcointracker.data.source.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime
import java.math.BigDecimal

@Entity
data class StatsDb (
    @PrimaryKey
    val id : Long = 1,
    val updatedAt : OffsetDateTime,
    val price : BigDecimal
)