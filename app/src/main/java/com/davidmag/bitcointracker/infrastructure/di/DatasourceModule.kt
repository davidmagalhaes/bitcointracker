package com.davidmag.bitcointracker.infrastructure.di

import com.davidmag.bitcointracker.data.boundary.local.LocalFlotationDatasource
import com.davidmag.bitcointracker.data.boundary.local.LocalStatsDatasource
import com.davidmag.bitcointracker.data.boundary.remote.RemoteFlotationDatasource
import com.davidmag.bitcointracker.data.boundary.remote.RemoteStatsDatasource
import com.davidmag.bitcointracker.data.source.local.dao.FlotationDao
import com.davidmag.bitcointracker.data.source.local.dao.StatsDao
import com.davidmag.bitcointracker.data.source.local.impl.LocalFlotationDatasourceImpl
import com.davidmag.bitcointracker.data.source.local.impl.LocalStatsDatasourceImpl
import com.davidmag.bitcointracker.data.source.remote.api.ChartsApi
import com.davidmag.bitcointracker.data.source.remote.api.StatsApi
import com.davidmag.bitcointracker.data.source.remote.impl.RemoteFlotationDatasourceImpl
import com.davidmag.bitcointracker.data.source.remote.impl.RemoteStatsDatasourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatasourceModule {
    @Singleton
    @Provides
    fun provideLocalFlotationDatasource(
        flotationDao: FlotationDao
    ) : LocalFlotationDatasource {
        return LocalFlotationDatasourceImpl(
            flotationDao
        )
    }

    @Singleton
    @Provides
    fun provideLocalStatsDatasource(
        statsDao: StatsDao
    ) : LocalStatsDatasource {
        return LocalStatsDatasourceImpl(
            statsDao
        )
    }

    @Singleton
    @Provides
    fun provideFlotationDatasource(
        chartsApi: ChartsApi
    ) : RemoteFlotationDatasource {
        return RemoteFlotationDatasourceImpl(
            chartsApi
        )
    }

    @Singleton
    @Provides
    fun provideRemoteStatsDatasource(
        statsApi: StatsApi
    ) : RemoteStatsDatasource {
        return RemoteStatsDatasourceImpl(
            statsApi
        )
    }
}