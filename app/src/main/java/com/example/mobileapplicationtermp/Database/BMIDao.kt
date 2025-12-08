package com.example.mobileapplicationtermp.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BmiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: BmiRecord)

    @Update
    suspend fun update(entry: BmiRecord)

    @Delete
    suspend fun delete(entry: BmiRecord)

    @Query("DELETE FROM bmi_records")
    suspend fun deleteAll()

    @Query("SELECT * FROM bmi_records ORDER BY timestamp DESC")
    fun getAll(): Flow<List<BmiRecord>>
}
