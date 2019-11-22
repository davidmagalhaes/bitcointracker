package com.davidmag.bitcointracker.domain.repo

import com.davidmag.bitcointracker.domain.model.Stats
import io.reactivex.Flowable
import io.reactivex.Maybe

interface StatsRepository {
    fun fetch() : Maybe<Any>
    fun get() : Flowable<List<Stats>>
}