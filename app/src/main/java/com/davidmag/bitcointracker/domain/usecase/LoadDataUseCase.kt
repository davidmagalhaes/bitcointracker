package com.davidmag.bitcointracker.domain.usecase

import com.davidmag.bitcointracker.domain.repo.FlotationRepository
import com.davidmag.bitcointracker.domain.repo.StatsRepository
import io.reactivex.Maybe

class LoadDataUseCase(
    private val floationRepository: FlotationRepository,
    private val statsRepository: StatsRepository
) {
    fun execute() : Maybe<Any> {
        return floationRepository.fetch().flatMap {
            statsRepository.fetch()
        }
    }
}