package com.davidmag.bitcointracker.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.davidmag.bitcointracker.data.source.local.util.OffsetDateTimeTypeConverter
import com.davidmag.bitcointracker.data.source.local.dao.FlotationDao
import com.davidmag.bitcointracker.data.source.local.dao.StatsDao
import com.davidmag.bitcointracker.data.source.local.dto.FlotationDb
import com.davidmag.bitcointracker.data.source.local.dto.StatsDb
import com.davidmag.bitcointracker.data.source.local.util.BigDecimalConverter

@Database(
    entities = [
        StatsDb::class,
        FlotationDb::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(OffsetDateTimeTypeConverter::class, BigDecimalConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getStatsDao() : StatsDao
    abstract fun getFlotationDao() : FlotationDao
}