package com.davidmag.bitcointracker.data.boundary.local

import com.davidmag.bitcointracker.domain.model.Flotation
import io.reactivex.Flowable
import io.reactivex.Maybe

interface LocalFlotationDatasource {
    fun get() : Flowable<List<Flotation>>
    fun cache(data : List<Flotation>) : Maybe<out Any>
    fun count() : Maybe<Int>
}