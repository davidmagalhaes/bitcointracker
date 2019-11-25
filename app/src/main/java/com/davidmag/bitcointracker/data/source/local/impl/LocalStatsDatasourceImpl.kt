package com.davidmag.bitcointracker.data.source.local.impl

import com.davidmag.bitcointracker.data.boundary.local.LocalStatsDatasource
import com.davidmag.bitcointracker.data.source.local.dao.StatsDao
import com.davidmag.bitcointracker.data.source.local.mapper.LocalStatsMapper
import com.davidmag.bitcointracker.domain.model.Stats
import io.reactivex.Flowable
import io.reactivex.Maybe

class LocalStatsDatasourceImpl(
    private val statsDao: StatsDao
) : LocalStatsDatasource {
    override fun get(): Flowable<List<Stats>> {
        return statsDao.get().map {
            LocalStatsMapper.toEntity(it)
        }
    }

    override fun cache(data: Stats): Maybe<out Any> {
        return Maybe.fromCallable {
            statsDao.cache(LocalStatsMapper.toDto(data))
        }
    }
}