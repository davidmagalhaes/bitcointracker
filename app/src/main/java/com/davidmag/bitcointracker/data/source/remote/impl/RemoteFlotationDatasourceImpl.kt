package com.davidmag.bitcointracker.data.source.remote.impl

import com.davidmag.bitcointracker.data.boundary.remote.RemoteFlotationDatasource
import com.davidmag.bitcointracker.data.source.remote.api.ChartsApi
import com.davidmag.bitcointracker.data.source.remote.mapper.RemoteFlotationMapper
import com.davidmag.bitcointracker.data.source.remote.util.AbstractRemoteDatasource
import com.davidmag.bitcointracker.domain.model.Flotation
import io.reactivex.Maybe

class RemoteFlotationDatasourceImpl(
    val chartsApi: ChartsApi
) : AbstractRemoteDatasource(), RemoteFlotationDatasource {
    override fun fetch(): Maybe<List<Flotation>> {
        return Maybe.fromCallable {
            doRequest(chartsApi.marketPrices()) {
                RemoteFlotationMapper.toEntity(it.values)
            }
        }
    }
}