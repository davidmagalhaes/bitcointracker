package com.davidmag.bitcointracker.data.source.remote.util

import com.davidmag.bitcointracker.infrastructure.App
import retrofit2.Call
import retrofit2.HttpException
import java.io.IOException

abstract class AbstractRemoteDatasource {

    private var currentCall : Call<*>? = null

    val authToken
        get() = App.authToken

    @Throws(HttpException::class, IOException::class)
    fun <T, Q> doRequest(call : Call<T>, parser : ((T)->Q)) : Q? {
        currentCall = call

        val response = call.execute()

        if(response.isSuccessful){
            val body = response.body()
            return if(body != null) parser(body) else null
        }
        else{
            throw HttpException(response)
        }
    }

    fun cancel(){
        currentCall?.cancel()
        currentCall = null
    }

}
