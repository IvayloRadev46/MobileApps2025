package com.example.mobileapplicationtermp.History

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapplicationtermp.Data.AppDatabase
import com.example.mobileapplicationtermp.Data.BmiRecord
import com.example.mobileapplicationtermp.Data.BmiRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.pow

class HistoryViewModel(
    appDatabase: AppDatabase
) : ViewModel() {

    private val bmiHistoryRepository = BmiRepository(appDatabase.bmiDao())

    // публичен поток, който HistoryScreen наблюдава
    val records = bmiHistoryRepository.allRecords
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun delete(recordToRemove: BmiRecord) {
        viewModelScope.launch {
            bmiHistoryRepository.deleteRecord(recordToRemove)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            bmiHistoryRepository.deleteAll()
        }
    }

    fun updateRecord(
        originalRecord: BmiRecord,
        newWeightKg: Double,
        newHeightCm: Double,
        newGender: String
    ) {
        viewModelScope.launch {
            val heightMeters = newHeightCm / 100.0
            var recalculatedBmi = newWeightKg / heightMeters.pow(2)

            recalculatedBmi = when (newGender) {
                "Мъж" -> recalculatedBmi - 1
                "Жена" -> recalculatedBmi + 1
                else -> recalculatedBmi
            }

            val recalculatedCategory = when {
                recalculatedBmi < 18.5 -> "Поднормено тегло"
                recalculatedBmi < 25 -> "Нормално тегло"
                recalculatedBmi < 30 -> "Наднормено тегло"
                else -> "Затлъстяване"
            }

            val updatedRecord = originalRecord.copy(
                weightKg = newWeightKg,
                heightCm = newHeightCm,
                gender = newGender,
                bmi = recalculatedBmi,
                category = recalculatedCategory,
                timestamp = System.currentTimeMillis()
            )

            bmiHistoryRepository.updateRecord(updatedRecord)
        }
    }
}
