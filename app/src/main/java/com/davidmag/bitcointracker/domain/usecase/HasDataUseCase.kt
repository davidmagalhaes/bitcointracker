package com.davidmag.bitcointracker.domain.usecase

import com.davidmag.bitcointracker.domain.repo.FlotationRepository
import io.reactivex.Maybe

class HasDataUseCase(
    private val flotationRepository: FlotationRepository
) {

    fun execute() : Maybe<Boolean> {
        return flotationRepository.count().map {
            it > 0
        }
    }
}