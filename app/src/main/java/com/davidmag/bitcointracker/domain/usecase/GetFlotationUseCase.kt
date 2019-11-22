package com.davidmag.bitcointracker.domain.usecase

import com.davidmag.bitcointracker.domain.model.Flotation
import com.davidmag.bitcointracker.domain.repo.FlotationRepository
import io.reactivex.Flowable

class GetFlotationUseCase(
    private val flotationRepository: FlotationRepository
) {
    fun execute() : Flowable<List<Flotation>> {
        return flotationRepository.get()
    }
}