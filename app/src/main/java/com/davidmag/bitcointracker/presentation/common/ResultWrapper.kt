package com.davidmag.bitcointracker.presentation.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers

object ResultWrapper {
    fun <T> wrap(flowable : Flowable<T>,
                 mediator : MediatorLiveData<Result<T>>? = null
    ) : LiveData<@JvmSuppressWildcards Result<T>> {
        val source = LiveDataReactiveStreams.fromPublisher(flowable
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    if(it != null){
                        Result.withData(it)
                    }
                    else {
                        Result.empty()
                    }
                }.onErrorReturn {
                    it.printStackTrace()
                    Result.error(it)
                }
        )

        mediator?.let { med ->
            med.addSource(source) {
                med.postValue(it)
            }

            med.postValue(Result.waiting())
        }

        return mediator ?: source
    }

    fun <T> wrapFirst(flowable : Flowable<List<T>>,
                      mediator : MediatorLiveData<Result<T>>? = null
    ) : LiveData<Result<T>> {
        val source = LiveDataReactiveStreams.fromPublisher(flowable
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    if (it.isNotEmpty()) Result.withData(it.first()) else Result.empty()
                }.onErrorReturn {
                    it.printStackTrace()
                    Result.error(it)
                }
        )

        mediator?.let { med ->
            med.addSource(source) {
                med.postValue(it)
            }

            med.postValue(Result.waiting())
        }

        return mediator ?: source
    }

    fun <T> wrap(maybe : Maybe<T>) : LiveData<Result<T>> {
        val source = LiveDataReactiveStreams.fromPublisher(maybe.toFlowable()
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    if(it != null){
                        Result.withData(it)
                    }
                    else {
                        Result.empty()
                    }
                }.onErrorReturn {
                    it.printStackTrace()
                    Result.error(it)
                }
        )

        val result = MediatorLiveData<Result<T>>()

        result.addSource(source){
            result.removeSource(source)
            result.postValue(it)
        }

        result.postValue(Result.waiting())

        return result
    }
}
