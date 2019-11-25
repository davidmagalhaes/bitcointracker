package com.davidmag.bitcointracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.davidmag.bitcointracker.domain.model.Flotation
import com.davidmag.bitcointracker.domain.model.Stats
import com.davidmag.bitcointracker.domain.usecase.GetFlotationUseCase
import com.davidmag.bitcointracker.domain.usecase.GetStatsUseCase
import com.davidmag.bitcointracker.domain.usecase.HasDataUseCase
import com.davidmag.bitcointracker.domain.usecase.LoadDataUseCase
import com.davidmag.bitcointracker.presentation.common.Result
import com.davidmag.bitcointracker.presentation.common.ResultWrapper

class MainViewModel(
    private val getStatsUseCase: GetStatsUseCase,
    private val getFlotationUseCase: GetFlotationUseCase,
    private val loadDataUseCase: LoadDataUseCase,
    private val hasDataUseCase: HasDataUseCase
) : ViewModel() {

    private var initialized = false

    val stats = MediatorLiveData<Result<Stats>>()
    val flotations = MediatorLiveData<Result<List<Flotation>>>()

    fun init() {
        if(!initialized){
            ResultWrapper.wrap(getFlotationUseCase.execute(), flotations)
            ResultWrapper.wrapFirst(getStatsUseCase.execute(), stats)

            initialized = true
        }
    }

    fun loadData() : LiveData<Result<Any>> {
        return ResultWrapper.wrap(loadDataUseCase.execute())
    }

    fun hasData() : LiveData<Result<Boolean>> {
        return ResultWrapper.wrap(hasDataUseCase.execute())
    }
}