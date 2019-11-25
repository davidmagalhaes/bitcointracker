package com.davidmag.bitcointracker.data.source.local.impl

import com.davidmag.bitcointracker.data.boundary.local.LocalFlotationDatasource
import com.davidmag.bitcointracker.data.source.local.dao.FlotationDao
import com.davidmag.bitcointracker.data.source.local.mapper.LocalFlotationMapper
import com.davidmag.bitcointracker.domain.model.Flotation
import io.reactivex.Flowable
import io.reactivex.Maybe

class LocalFlotationDatasourceImpl(
    private val floatingDao: FlotationDao
) : LocalFlotationDatasource {
    override fun get(): Flowable<List<Flotation>> {
        return floatingDao.get().map {
            LocalFlotationMapper.toEntity(it)
        }
    }

    override fun cache(data: List<Flotation>): Maybe<out Any> {
        return Maybe.fromCallable {
            floatingDao.cache(*LocalFlotationMapper.toDto(data).toTypedArray())
        }
    }

    override fun count(): Maybe<Int> {
        return floatingDao.count()
    }

}