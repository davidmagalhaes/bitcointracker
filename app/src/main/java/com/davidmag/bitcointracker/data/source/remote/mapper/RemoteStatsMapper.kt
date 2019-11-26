package com.davidmag.bitcointracker.data.source.remote.mapper

import com.davidmag.bitcointracker.data.source.common.EntityDtoMapper
import com.davidmag.bitcointracker.data.source.remote.dto.StatsObject
import com.davidmag.bitcointracker.domain.model.Stats
import org.threeten.bp.OffsetDateTime

object RemoteStatsMapper : EntityDtoMapper<Stats, StatsObject>() {
    override val toDtoMapper: (Stats) -> StatsObject
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val toEntityMapper: (StatsObject) -> Stats = {
        val entity = Stats(
            updatedAt = OffsetDateTime.now(),
            price = it.price
        )

        entity
    }
}