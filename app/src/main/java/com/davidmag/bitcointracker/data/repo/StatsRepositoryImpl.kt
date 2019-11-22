package com.davidmag.bitcointracker.data.repo

import com.davidmag.bitcointracker.data.boundary.local.LocalStatsDatasource
import com.davidmag.bitcointracker.data.boundary.remote.RemoteStatsDatasource
import com.davidmag.bitcointracker.domain.model.Stats
import com.davidmag.bitcointracker.domain.repo.StatsRepository
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

class StatsRepositoryImpl(
    private val localDatasource : LocalStatsDatasource,
    private val remoteDatasource : RemoteStatsDatasource
) : StatsRepository {

    override fun fetch(): Maybe<Any> {
        return remoteDatasource.fetch().subscribeOn(Schedulers.io()).flatMap {
            localDatasource.cache(it).subscribeOn(Schedulers.single())
        }
    }

    override fun get(): Flowable<List<Stats>> {
        return localDatasource.get().subscribeOn(Schedulers.single())
    }
}