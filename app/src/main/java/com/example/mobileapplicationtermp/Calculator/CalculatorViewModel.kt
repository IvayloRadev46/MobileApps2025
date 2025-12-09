package com.example.mobileapplicationtermp.Calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapplicationtermp.Data.AppDatabase
import com.example.mobileapplicationtermp.Data.BmiRecord
import com.example.mobileapplicationtermp.Data.BmiRepository
import kotlinx.coroutines.launch
import kotlin.math.pow

class CalculatorViewModel(
    appDatabase: AppDatabase
) : ViewModel() {

    private val bmiDataRepository = BmiRepository(appDatabase.bmiDao())

    fun calculateAndSave(
        inputWeightKg: Double,
        inputHeightCm: Double,
        selectedGender: String,
        onResultTextReady: (String) -> Unit
    ) {
        val heightInMeters = inputHeightCm / 100.0
        var calculatedBmiValue = inputWeightKg / heightInMeters.pow(2)

        calculatedBmiValue = when (selectedGender) {
            "Мъж" -> calculatedBmiValue - 1
            "Жена" -> calculatedBmiValue + 1
            else -> calculatedBmiValue
        }

        val weightCategoryLabel = when {
            calculatedBmiValue < 18.5 -> "Поднормено тегло"
            calculatedBmiValue < 25 -> "Нормално тегло"
            calculatedBmiValue < 30 -> "Наднормено тегло"
            else -> "Затлъстяване"
        }

        val resultText = String.format(
            "BMI: %.2f (%s), Пол: %s",
            calculatedBmiValue,
            weightCategoryLabel,
            selectedGender
        )

        val newBmiRecord = BmiRecord(
            weightKg = inputWeightKg,
            heightCm = inputHeightCm,
            gender = selectedGender,
            bmi = calculatedBmiValue,
            category = weightCategoryLabel,
            timestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            bmiDataRepository.addRecord(newBmiRecord)
        }

        onResultTextReady(resultText)
    }
}
