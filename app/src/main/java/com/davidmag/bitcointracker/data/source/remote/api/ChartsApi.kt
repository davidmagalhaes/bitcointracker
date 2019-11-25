package com.davidmag.bitcointracker.data.source.remote.api

import com.davidmag.bitcointracker.data.source.remote.dto.ChartsApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ChartsApi {

    @GET("charts/market-price")
    fun marketPrices() : Call<ChartsApiResponse>
}