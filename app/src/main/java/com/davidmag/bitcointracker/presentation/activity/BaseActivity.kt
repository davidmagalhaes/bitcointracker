package com.davidmag.bitcointracker.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.davidmag.bitcointracker.infrastructure.di.DaggerApplicationComponent
import com.davidmag.bitcointracker.presentation.di.DaggerPresentationComponent

abstract class BaseActivity : AppCompatActivity(){

    val presentationComponent by lazy {
        val applicationComponent = DaggerApplicationComponent
            .builder()
            .bind(this)
            .build()

        DaggerPresentationComponent
            .builder()
            .applicationComponent(applicationComponent)
            .build()
    }

    inline fun <reified T: ViewModel> initViewModel(crossinline factory: () -> T): T = T::class.java.let { clazz ->
        ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if(modelClass == clazz) {
                    @Suppress("UNCHECKED_CAST")
                    return factory() as T
                }
                throw IllegalArgumentException("Unexpected argument: $modelClass")
            }
        }).get(clazz)
    }
}