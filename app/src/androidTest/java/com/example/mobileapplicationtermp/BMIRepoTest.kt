package com.example.mobileapplicationtermp

import com.example.mobileapplicationtermp.Data.BmiDao
import com.example.mobileapplicationtermp.Data.BmiRecord
import com.example.mobileapplicationtermp.Data.BmiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FakeBmiDao : BmiDao {

    private val state = MutableStateFlow<List<BmiRecord>>(emptyList())

    override suspend fun insert(entry: BmiRecord) {
        val current = state.value.toMutableList()
        val newId = (current.maxOfOrNull { it.id } ?: 0L) + 1L
        current.add(entry.copy(id = newId))
        state.value = current
    }

    override suspend fun update(entry: BmiRecord) {
        val current = state.value.toMutableList()
        val index = current.indexOfFirst { it.id == entry.id }
        if (index != -1) {
            current[index] = entry
            state.value = current
        }
    }

    override suspend fun delete(entry: BmiRecord) {
        val current = state.value.toMutableList()
        current.removeAll { it.id == entry.id }
        state.value = current
    }

    override suspend fun deleteAll() {
        state.value = emptyList()
    }

    override fun getAll(): Flow<List<BmiRecord>> = state
}

class BmiRepositoryTest {

    private fun createRepository(): BmiRepository {
        return BmiRepository(FakeBmiDao())
    }

    @Test
    fun addRecord_increasesSize() = runBlocking {
        val repo = createRepository()
        val record = BmiRecord(0, 70.0, 175.0, "Мъж", 22.86, "Нормално тегло", 0)
        repo.addRecord(record)
        val list = repo.allRecords.first()
        assertEquals(1, list.size)
        assertTrue(list.first().id > 0)
    }

    @Test
    fun updateRecord_changesEntry() = runBlocking {
        val repo = createRepository()
        val record = BmiRecord(0, 70.0, 175.0, "Мъж", 22.86, "Нормално тегло", 0)
        repo.addRecord(record)

        val initial = repo.allRecords.first().first()
        val updated = initial.copy(weightKg = 80.0, bmi = 26.12, category = "Наднормено тегло")
        repo.updateRecord(updated)

        val after = repo.allRecords.first().first()
        assertEquals(80.0, after.weightKg, 0.001)
        assertEquals("Наднормено тегло", after.category)
    }

    @Test
    fun deleteRecord_removesEntry() = runBlocking {
        val repo = createRepository()
        val r1 = BmiRecord(0, 60.0, 170.0, "Жена", 20.7, "Нормално тегло", 0)
        val r2 = BmiRecord(0, 90.0, 175.0, "Мъж", 29.3, "Наднормено тегло", 0)

        repo.addRecord(r1)
        repo.addRecord(r2)

        val before = repo.allRecords.first()
        repo.deleteRecord(before.first())

        val after = repo.allRecords.first()
        assertEquals(1, after.size)
    }

    @Test
    fun deleteAll_clearsList() = runBlocking {
        val repo = createRepository()
        val r = BmiRecord(0, 65.0, 180.0, "Жена", 20.0, "Нормално тегло", 0)

        repo.addRecord(r)
        repo.addRecord(r.copy(id = 2))

        repo.deleteAll()
        val size = repo.allRecords.first().size
        assertEquals(0, size)
    }
}
