package com.example.mobileapplicationtermp

import com.example.mobileapplicationtermp.Data.BmiRecord
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.pow

private fun updateRecordLogic(
    record: BmiRecord,
    newWeight: Double,
    newHeight: Double,
    gender: String
): BmiRecord {
    val heightM = newHeight / 100.0
    var bmi = newWeight / heightM.pow(2)

    bmi = when (gender) {
        "Мъж" -> bmi - 1
        "Жена" -> bmi + 1
        else -> bmi
    }

    val category = when {
        bmi < 18.5 -> "Поднормено тегло"
        bmi < 25.0 -> "Нормално тегло"
        bmi < 30.0 -> "Наднормено тегло"
        else -> "Затлъстяване"
    }

    return record.copy(
        weightKg = newWeight,
        heightCm = newHeight,
        gender = gender,
        bmi = bmi,
        category = category,
        timestamp = 123456L
    )
}

class HistoryUpdateLogicTest {

    @Test
    fun updateRecord_changesWeightAndHeight() {
        val original = BmiRecord(1, 70.0, 175.0, "Мъж", 22.8, "Нормално тегло", 0)
        val updated = updateRecordLogic(original, 80.0, 180.0, "Мъж")
        assertEquals(80.0, updated.weightKg, 0.001)
        assertEquals(180.0, updated.heightCm, 0.001)
    }

    @Test
    fun updateRecord_setsCorrectCategory() {
        val original = BmiRecord(2, 100.0, 175.0, "Мъж", 32.0, "Затлъстяване", 0)
        val updated = updateRecordLogic(original, 70.0, 175.0, "Мъж")
        assertEquals("Нормално тегло", updated.category)
    }
}
