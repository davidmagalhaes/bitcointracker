package com.davidmag.bitcointracker.infrastructure.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.davidmag.bitcointracker.data.source.local.LocalDatabase
import com.davidmag.bitcointracker.data.source.local.dao.FlotationDao
import com.davidmag.bitcointracker.data.source.local.dao.StatsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Singleton
    @Provides
    fun provideDatabase(
        context: Context
    ) : LocalDatabase {
        return Room.databaseBuilder (
                context,
                LocalDatabase::class.java,
            "database.db"
            )
            .fallbackToDestructiveMigration()
            .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
            .build()
    }

    @Singleton
    @Provides
    fun provideStatsDao(
        database : LocalDatabase
    ) : StatsDao {
        return database.getStatsDao()
    }

    @Singleton
    @Provides
    fun provideFlotationDao(
        database: LocalDatabase
    ) : FlotationDao {
        return database.getFlotationDao()
    }
}