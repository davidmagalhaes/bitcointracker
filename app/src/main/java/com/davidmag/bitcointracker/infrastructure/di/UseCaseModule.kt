package com.davidmag.bitcointracker.infrastructure.di

import com.davidmag.bitcointracker.domain.repo.FlotationRepository
import com.davidmag.bitcointracker.domain.repo.StatsRepository
import com.davidmag.bitcointracker.domain.usecase.GetFlotationUseCase
import com.davidmag.bitcointracker.domain.usecase.GetStatsUseCase
import com.davidmag.bitcointracker.domain.usecase.HasDataUseCase
import com.davidmag.bitcointracker.domain.usecase.LoadDataUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetFlotationUseCase(
        flotationRepository: FlotationRepository
    ) : GetFlotationUseCase {
        return GetFlotationUseCase(
            flotationRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetStatsUseCase(
        statsRepository: StatsRepository
    ) : GetStatsUseCase {
        return GetStatsUseCase(
            statsRepository
        )
    }

    @Provides
    @Singleton
    fun provideLoadDataUseCase(
        statsRepository: StatsRepository,
        flotationRepository: FlotationRepository
    ) : LoadDataUseCase {
        return LoadDataUseCase(
            statsRepository = statsRepository,
            floationRepository = flotationRepository
        )
    }

    @Provides
    @Singleton
    fun provideHasDataUseCase(
        flotationRepository: FlotationRepository
    ) : HasDataUseCase {
        return HasDataUseCase(
            flotationRepository
        )
    }
}