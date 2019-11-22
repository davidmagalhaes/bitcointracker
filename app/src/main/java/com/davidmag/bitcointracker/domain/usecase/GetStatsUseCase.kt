package com.davidmag.bitcointracker.domain.usecase

import com.davidmag.bitcointracker.domain.model.Stats
import com.davidmag.bitcointracker.domain.repo.StatsRepository
import io.reactivex.Flowable

class GetStatsUseCase(
    private val statsRepository: StatsRepository
) {
    fun execute() : Flowable<List<Stats>> {
        return statsRepository.get()
    }
}