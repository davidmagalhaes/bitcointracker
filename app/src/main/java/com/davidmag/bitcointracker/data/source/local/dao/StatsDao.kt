package com.davidmag.bitcointracker.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.davidmag.bitcointracker.data.source.local.dto.StatsDb
import com.davidmag.bitcointracker.data.source.local.util.BaseDao
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface StatsDao : BaseDao<StatsDb> {
    @Query("select * from StatsDb")
    fun get() : Flowable<List<StatsDb>>

    @Query("select count(*) from StatsDb")
    fun count() : Maybe<Int>

    @Transaction
    fun cache(vararg item : StatsDb){
        _removeAll()
        _insertAll(*item)
    }

    @Insert
    fun _insertAll(vararg item : StatsDb)
    @Query("delete from StatsDb")
    fun _removeAll()
}