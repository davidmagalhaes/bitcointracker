package com.davidmag.bitcointracker.infrastructure.di

import android.app.Application
import android.content.Context
import com.davidmag.bitcointracker.domain.usecase.GetFlotationUseCase
import com.davidmag.bitcointracker.domain.usecase.GetStatsUseCase
import com.davidmag.bitcointracker.domain.usecase.HasDataUseCase
import com.davidmag.bitcointracker.domain.usecase.LoadDataUseCase
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    PersistenceModule::class,
    NetworkModule::class,
    DatasourceModule::class,
    RepositoryModule::class,
    UseCaseModule::class
])
interface ApplicationComponent {

    fun getStatsUseCase() : GetStatsUseCase
    fun getFlotationUseCase() : GetFlotationUseCase
    fun loadDataUseCase() : LoadDataUseCase
    fun hasDataUseCase() : HasDataUseCase

    @Component.Builder
    interface Builder {

        fun build(): ApplicationComponent

        @BindsInstance
        fun bind(context : Context): Builder
    }

    fun inject(application : Application)
}