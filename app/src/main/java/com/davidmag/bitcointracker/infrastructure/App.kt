package com.davidmag.bitcointracker.infrastructure

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.davidmag.bitcointracker.BuildConfig
import com.davidmag.bitcointracker.data.source.local.LocalDatabase
import com.facebook.stetho.Stetho
import io.reactivex.plugins.RxJavaPlugins
import java.util.*

class App : Application() {
    companion object {
        val TAG = App::class.java.simpleName

        var initialized : Boolean = false

        lateinit var instance : App
            private set

        val currentLocale = MutableLiveData<Locale>()

        var authToken : String? = ""
    }

    override fun onCreate() {
        init()
        super.onCreate()
    }

    fun init() {
        if(!initialized){
            initialized = true

            initLocalDatabase()

            if(BuildConfig.DEBUG){
                initStetho()
            }

            currentLocale.postValue(Locale.getDefault())
            currentLocale.observeForever {
                Locale.setDefault(it)
            }

            RxJavaPlugins.setErrorHandler {
                Log.i(TAG, "RxJava error handled by the global handler!")
                it.printStackTrace()
            }
        }
    }

    init {
        instance = this
    }

    private fun initStetho(){
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        )
    }

    private fun initLocalDatabase(){
        LocalDatabase(this)
    }
}