package com.davidmag.bitcointracker.domain.repo

import com.davidmag.bitcointracker.domain.model.Flotation
import io.reactivex.Flowable
import io.reactivex.Maybe

interface FlotationRepository {
    fun fetch() : Maybe<Any>
    fun get() : Flowable<List<Flotation>>
}