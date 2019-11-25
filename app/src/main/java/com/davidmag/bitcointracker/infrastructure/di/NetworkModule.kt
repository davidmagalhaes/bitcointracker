package com.davidmag.bitcointracker.infrastructure.di

import android.content.Context
import com.davidmag.bitcointracker.BuildConfig
import com.davidmag.bitcointracker.data.source.remote.api.ChartsApi
import com.davidmag.bitcointracker.data.source.remote.api.StatsApi
import com.davidmag.bitcointracker.infrastructure.App
import com.davidmag.bitcointracker.infrastructure.util.UnixTimestampJsonDeserializer
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.*
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.threeten.bp.OffsetDateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        context: Context
    ) : OkHttpClient {
        val okHttp3ClientBuilder = OkHttpClient.Builder()
            .connectionPool(ConnectionPool(10, 1, TimeUnit.MINUTES))
            .readTimeout(BuildConfig.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(BuildConfig.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(BuildConfig.NETWORK_TIMEOUT, TimeUnit.SECONDS)


        if(BuildConfig.DEBUG){
            Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                    .build()
            )

            okHttp3ClientBuilder.addNetworkInterceptor(StethoInterceptor())
        }

        return okHttp3ClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient : OkHttpClient,
        gson : Gson
    ) : Retrofit {
        return Retrofit.Builder().
            baseUrl(BuildConfig.WEBSERVICE_URL).
            client(okHttpClient).
            addConverterFactory(GsonConverterFactory.create(gson)).
            build()
    }

    @Singleton
    @Provides
    fun provideGson() : Gson {
        val gbuilder = GsonBuilder().setDateFormat("yyyy-MM-dd")

        gbuilder.registerTypeAdapter(OffsetDateTime::class.java, UnixTimestampJsonDeserializer)

        return gbuilder.create()
    }

    @Singleton
    @Provides
    fun provideStatsApi(
        retrofit : Retrofit
    ) : StatsApi {
        return retrofit.create(StatsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideChartsApi(
        retrofit: Retrofit
    ) : ChartsApi {
        return retrofit.create(ChartsApi::class.java)
    }
}