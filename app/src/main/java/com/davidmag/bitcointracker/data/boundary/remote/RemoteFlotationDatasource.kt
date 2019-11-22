package com.davidmag.bitcointracker.data.boundary.remote

import com.davidmag.bitcointracker.domain.model.Flotation
import io.reactivex.Maybe

interface RemoteFlotationDatasource {
    fun fetch() : Maybe<List<Flotation>>
    fun cancel()
}