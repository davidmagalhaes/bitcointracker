package com.davidmag.bitcointracker.data.source.remote.api

import com.davidmag.bitcointracker.data.source.remote.dto.StatsObject
import retrofit2.Call
import retrofit2.http.GET

interface StatsApi {
    @GET("stats")
    fun getStats() : Call<StatsObject>
}