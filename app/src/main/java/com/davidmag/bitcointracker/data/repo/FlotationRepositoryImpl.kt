package com.davidmag.bitcointracker.data.repo

import com.davidmag.bitcointracker.data.boundary.local.LocalFlotationDatasource
import com.davidmag.bitcointracker.data.boundary.remote.RemoteFlotationDatasource
import com.davidmag.bitcointracker.domain.model.Flotation
import com.davidmag.bitcointracker.domain.repo.FlotationRepository
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

class FlotationRepositoryImpl(
    private val localDatasource : LocalFlotationDatasource,
    private val remoteDatasource : RemoteFlotationDatasource
) : FlotationRepository {

    override fun fetch(): Maybe<Any> {
        return remoteDatasource.fetch().subscribeOn(Schedulers.io()).flatMap {
            localDatasource.cache(it).subscribeOn(Schedulers.single())
        }
    }

    override fun get(): Flowable<List<Flotation>> {
        return localDatasource.get().subscribeOn(Schedulers.single())
    }

    override fun count(): Maybe<Int> {
        return localDatasource.count().subscribeOn(Schedulers.single())
    }
}