package com.davidmag.bitcointracker.data.source.local.util

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Maybe

interface BaseDao<T> {
    @Insert
    fun insert(vararg item : T) : Maybe<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(vararg item : T) : Maybe<List<Long>>

    @Delete
    fun delete(vararg item : T) : Maybe<Int>

    @Update
    fun update(vararg item : T) : Maybe<Int>
}