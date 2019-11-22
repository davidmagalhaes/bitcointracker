package com.davidmag.bitcointracker.data.boundary.remote

import com.davidmag.bitcointracker.domain.model.Stats
import io.reactivex.Maybe

interface RemoteStatsDatasource {
    fun fetch() : Maybe<List<Stats>>
    fun cancel()
}