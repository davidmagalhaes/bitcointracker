package com.davidmag.bitcointracker.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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
    version = 1,
    exportSchema = true
)
@TypeConverters(OffsetDateTimeTypeConverter::class, BigDecimalConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getStatsDao() : StatsDao
    abstract fun getFlotationDao() : FlotationDao

    companion object {
        var instance: LocalDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            LocalDatabase::class.java, "database.db").
                fallbackToDestructiveMigration().
                setJournalMode(JournalMode.WRITE_AHEAD_LOGGING).
                build()
    }
}