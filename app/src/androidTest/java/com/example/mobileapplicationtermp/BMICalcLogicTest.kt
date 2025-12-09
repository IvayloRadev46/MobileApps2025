package com.example.mobileapplicationtermp

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.pow

private fun calculateBmi(weightKg: Double, heightCm: Double, gender: String): Pair<Double, String> {
    val heightM = heightCm / 100.0
    var bmi = weightKg / heightM.pow(2)

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

    return bmi to category
}

class BmiCalculatorLogicTest {

    @Test
    fun bmi_isCalculatedCorrectly_forMale() {
        val (bmi, _) = calculateBmi(80.0, 180.0, "Мъж")
        assertTrue(bmi in 23.0..25.5)
    }

    @Test
    fun bmi_isCalculatedCorrectly_forFemale() {
        val (bmi, _) = calculateBmi(60.0, 165.0, "Жена")
        assertTrue(bmi in 22.0..25.5)
    }

    @Test
    fun underweightCategory_isCorrect() {
        val (_, category) = calculateBmi(45.0, 175.0, "Мъж")
        assertEquals("Поднормено тегло", category)
    }

    @Test
    fun normalWeightCategory_isCorrect() {
        val (_, category) = calculateBmi(68.0, 175.0, "Мъж")
        assertEquals("Нормално тегло", category)
    }

    @Test
    fun overweightCategory_isCorrect() {
        val (_, category) = calculateBmi(85.0, 175.0, "Мъж")
        assertEquals("Наднормено тегло", category)
    }

    @Test
    fun obeseCategory_isCorrect() {
        val (_, category) = calculateBmi(110.0, 175.0, "Мъж")
        assertEquals("Затлъстяване", category)
    }
}
