package com.davidmag.bitcointracker.presentation.di

import com.davidmag.bitcointracker.domain.usecase.GetFlotationUseCase
import com.davidmag.bitcointracker.domain.usecase.GetStatsUseCase
import com.davidmag.bitcointracker.domain.usecase.HasDataUseCase
import com.davidmag.bitcointracker.domain.usecase.LoadDataUseCase
import com.davidmag.bitcointracker.presentation.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {
    @Provides
    fun provideMainViewModel(
        getStatsUseCase: GetStatsUseCase,
        getFlotationUseCase: GetFlotationUseCase,
        loadDataUseCase: LoadDataUseCase,
        hasDataUseCase: HasDataUseCase
    ) : MainViewModel {
        return MainViewModel(
            getStatsUseCase,
            getFlotationUseCase,
            loadDataUseCase,
            hasDataUseCase
        )
    }
}