package com.example.mobileapplicationtermp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.assertTextContains
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun calculateBmi_showsResultText() {
        composeRule.onNodeWithText("Маса (kg)").performTextInput("70")
        composeRule.onNodeWithText("Височина (cm)").performTextInput("175")
        composeRule.onNodeWithText("Изчисли BMI").performClick()

        val resultNode = composeRule.onNode(
            hasText("BMI", substring = true) and !hasClickAction()
        )

        resultNode.assertTextContains("BMI", substring = true)
    }
}
