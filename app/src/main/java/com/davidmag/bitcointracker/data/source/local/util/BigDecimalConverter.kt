package com.davidmag.bitcointracker.data.source.local.util

import androidx.room.TypeConverter
import java.math.BigDecimal

object BigDecimalConverter {
    @TypeConverter
    @JvmStatic
    fun fromLong(value : Long?) : BigDecimal? {
        return if(value == null) null else BigDecimal(value).divide(BigDecimal(100))
    }

    @TypeConverter
    @JvmStatic
    fun toLong(bigDecimal: BigDecimal?) : Long? {
        return bigDecimal?.multiply(BigDecimal(100))?.toLong()
    }
}