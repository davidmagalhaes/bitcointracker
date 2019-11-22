package com.davidmag.bitcointracker.data.source.local.mapper

import com.davidmag.bitcointracker.data.source.common.EntityDtoMapper
import com.davidmag.bitcointracker.data.source.local.dto.StatsDb
import com.davidmag.bitcointracker.domain.model.Stats

object LocalStatsMapper : EntityDtoMapper<Stats, StatsDb>() {
    override val toDtoMapper: (Stats) -> StatsDb = {
        val dto = StatsDb(
            updatedAt = it.updatedAt,
            price = it.price
        )

        dto
    }

    override val toEntityMapper: (StatsDb) -> Stats = {
        val entity = Stats(
            updatedAt = it.updatedAt,
            price = it.price
        )

        entity
    }
}