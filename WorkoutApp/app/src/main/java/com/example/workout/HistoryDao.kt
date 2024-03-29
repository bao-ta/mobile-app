package com.example.workout

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    @Delete
    suspend fun delete(historyEntity: HistoryEntity)

    @Query("Select * from `history-table` where date=:date")
    fun findById(date: String):Flow <HistoryEntity>

    @Query("SELECT * from `history-table`")
    fun fetchHistory(): Flow<List<HistoryEntity>>
}