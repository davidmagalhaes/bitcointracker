package com.davidmag.bitcointracker.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class ChartsApiResponse (
    @SerializedName("values")
    val values : List<FlotationObject>
)