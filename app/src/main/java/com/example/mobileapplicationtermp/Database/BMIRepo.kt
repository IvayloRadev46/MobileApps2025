package com.example.mobileapplicationtermp.Data

import kotlinx.coroutines.flow.Flow

class BmiRepository(
    private val bmiDao: BmiDao
) {

    val allRecords: Flow<List<BmiRecord>> = bmiDao.getAll()

    suspend fun addRecord(entry: BmiRecord) {
        bmiDao.insert(entry)
    }

    suspend fun updateRecord(entry: BmiRecord) {
        bmiDao.update(entry)
    }

    suspend fun deleteRecord(entry: BmiRecord) {
        bmiDao.delete(entry)
    }

    suspend fun deleteAll() {
        bmiDao.deleteAll()
    }
}
