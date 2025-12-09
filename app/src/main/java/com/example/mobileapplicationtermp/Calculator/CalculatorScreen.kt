package com.example.mobileapplicationtermp.Calculator

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    onHistoryClick: () -> Unit,
    onShare: (String) -> Unit
) {
    val uiContext = LocalContext.current
    var weightInputText by remember { mutableStateOf("") }
    var heightInputText by remember { mutableStateOf("") }
    var bmiResultText by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("Мъж") }
    val genderChoices = listOf("Мъж", "Жена")
    var isGenderDropdownOpen by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = weightInputText,
            onValueChange = { weightInputText = it },
            label = { Text("Маса (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = heightInputText,
            onValueChange = { heightInputText = it },
            label = { Text("Височина (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { isGenderDropdownOpen = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Пол: $selectedGender")
            }

            DropdownMenu(
                expanded = isGenderDropdownOpen,
                onDismissRequest = { isGenderDropdownOpen = false }
            ) {
                genderChoices.forEach { genderOption ->
                    DropdownMenuItem(
                        text = { Text(genderOption) },
                        onClick = {
                            selectedGender = genderOption
                            isGenderDropdownOpen = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                val parsedWeight = weightInputText.toDoubleOrNull()
                val parsedHeight = heightInputText.toDoubleOrNull()
                if (parsedWeight == null || parsedHeight == null || parsedWeight <= 0 || parsedHeight <= 0) {
                    Toast.makeText(uiContext, "Невалидни стойности", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.calculateAndSave(parsedWeight, parsedHeight, selectedGender) { computedResult ->
                        bmiResultText = computedResult
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Изчисли BMI")
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = if (bmiResultText.isEmpty()) "Резултатът ще се появи тук" else bmiResultText
        )

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                if (bmiResultText.isEmpty()) {
                    Toast.makeText(uiContext, "Първо изчисли BMI", Toast.LENGTH_SHORT).show()
                } else {
                    onShare(bmiResultText)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сподели резултата")
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onHistoryClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("История")
        }
    }
}
