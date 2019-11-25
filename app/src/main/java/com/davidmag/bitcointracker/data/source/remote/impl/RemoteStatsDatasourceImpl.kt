package com.davidmag.bitcointracker.data.source.remote.impl

import com.davidmag.bitcointracker.data.boundary.remote.RemoteStatsDatasource
import com.davidmag.bitcointracker.data.source.remote.api.StatsApi
import com.davidmag.bitcointracker.data.source.remote.mapper.RemoteStatsMapper
import com.davidmag.bitcointracker.data.source.remote.util.AbstractRemoteDatasource
import com.davidmag.bitcointracker.domain.model.Stats
import io.reactivex.Maybe

class RemoteStatsDatasourceImpl(
    val statsApi: StatsApi
) : AbstractRemoteDatasource(), RemoteStatsDatasource {

    override fun fetch() : Maybe<Stats> {
        return Maybe.fromCallable {
            doRequest(statsApi.getStats()) {
                RemoteStatsMapper.toEntity(it)
            }
        }
    }
}