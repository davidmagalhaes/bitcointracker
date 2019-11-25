package com.davidmag.bitcointracker.data.source.remote.mapper

import com.davidmag.bitcointracker.data.source.common.EntityDtoMapper
import com.davidmag.bitcointracker.data.source.remote.dto.FlotationObject
import com.davidmag.bitcointracker.domain.model.Flotation

object RemoteFlotationMapper : EntityDtoMapper<Flotation, FlotationObject>() {
    override val toDtoMapper: (Flotation) -> FlotationObject
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val toEntityMapper: (FlotationObject) -> Flotation = {
        val entity = Flotation(
            date = it.date,
            price = it.price
        )

        entity
    }
}