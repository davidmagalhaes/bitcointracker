package com.davidmag.bitcointracker.data.boundary.local

import com.davidmag.bitcointracker.domain.model.Stats
import io.reactivex.Flowable
import io.reactivex.Maybe

interface LocalStatsDatasource {
    fun get() : Flowable<List<Stats>>
    fun cache(data : Stats) : Maybe<out Any>
}