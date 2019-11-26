package com.davidmag.bitcointracker.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.davidmag.bitcointracker.data.source.local.dto.FlotationDb
import com.davidmag.bitcointracker.data.source.local.util.BaseDao
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface FlotationDao : BaseDao<FlotationDb> {
    @Query("select * from FlotationDb where date(date) > date('now', 'start of month') " +
            "order by date(date) asc")
    fun get() : Flowable<List<FlotationDb>>

    @Query("select count(*) from FlotationDb")
    fun count() : Maybe<Int>

    @Transaction
    fun cache(vararg item : FlotationDb){
        _removeAll()
        _insertAll(*item)
    }

    @Insert
    fun _insertAll(vararg item : FlotationDb)
    @Query("delete from FlotationDb")
    fun _removeAll()
}
