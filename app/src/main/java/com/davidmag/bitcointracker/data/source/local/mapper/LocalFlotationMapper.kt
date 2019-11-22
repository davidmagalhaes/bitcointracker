package com.davidmag.bitcointracker.data.source.local.mapper

import com.davidmag.bitcointracker.data.source.common.EntityDtoMapper
import com.davidmag.bitcointracker.data.source.local.dto.FlotationDb
import com.davidmag.bitcointracker.domain.model.Flotation

object LocalFlotationMapper : EntityDtoMapper<Flotation, FlotationDb>() {
    override val toDtoMapper: (Flotation) -> FlotationDb = {
        val dto = FlotationDb(
            id = it.date.toEpochSecond(),
            date = it.date,
            price = it.price
        )

        dto
    }

    override val toEntityMapper: (FlotationDb) -> Flotation = {
        val entity = Flotation(
            date = it.date,
            price = it.price
        )

        entity
    }
}