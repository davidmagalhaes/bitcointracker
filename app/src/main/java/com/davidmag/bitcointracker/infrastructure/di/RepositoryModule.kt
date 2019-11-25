package com.davidmag.bitcointracker.infrastructure.di

import com.davidmag.bitcointracker.data.boundary.local.LocalFlotationDatasource
import com.davidmag.bitcointracker.data.boundary.local.LocalStatsDatasource
import com.davidmag.bitcointracker.data.boundary.remote.RemoteFlotationDatasource
import com.davidmag.bitcointracker.data.boundary.remote.RemoteStatsDatasource
import com.davidmag.bitcointracker.data.repo.FlotationRepositoryImpl
import com.davidmag.bitcointracker.data.repo.StatsRepositoryImpl
import com.davidmag.bitcointracker.domain.repo.FlotationRepository
import com.davidmag.bitcointracker.domain.repo.StatsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideStatsRepository(
        localStatsDatasource: LocalStatsDatasource,
        remoteStatsDatasource: RemoteStatsDatasource
    ) : StatsRepository {
        return StatsRepositoryImpl(
            localStatsDatasource,
            remoteStatsDatasource
        )
    }

    @Provides
    @Singleton
    fun provideFlotationRepository(
        localFlotationDatasource: LocalFlotationDatasource,
        remoteFlotationDatasource: RemoteFlotationDatasource
    ) : FlotationRepository {
        return FlotationRepositoryImpl(
            localFlotationDatasource,
            remoteFlotationDatasource
        )
    }

}