package com.example.mobileapplicationtermp.History

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mobileapplicationtermp.Data.BmiRecord
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onBack: () -> Unit,
    onShare: (String) -> Unit
) {
    val context = LocalContext.current
    val recordList by viewModel.records.collectAsState()

    var editingRecord by remember { mutableStateOf<BmiRecord?>(null) }
    var editedWeight by remember { mutableStateOf("") }
    var editedHeight by remember { mutableStateOf("") }
    var editedGender by remember { mutableStateOf("Мъж") }
    var genderMenuExpanded by remember { mutableStateOf(false) }

    // Диалог за редакция
    if (editingRecord != null) {
        AlertDialog(
            onDismissRequest = { editingRecord = null },
            confirmButton = {
                TextButton(onClick = {
                    val weightValue = editedWeight.toDoubleOrNull()
                    val heightValue = editedHeight.toDoubleOrNull()
                    if (weightValue == null || heightValue == null || weightValue <= 0 || heightValue <= 0) {
                        Toast.makeText(context, "Невалидни стойности", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.updateRecord(
                            originalRecord = editingRecord!!,
                            newWeightKg = weightValue,
                            newHeightCm = heightValue,
                            newGender = editedGender
                        )
                        editingRecord = null
                    }
                }) {
                    Text("Запази")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingRecord = null }) {
                    Text("Отказ")
                }
            },
            title = { Text("Редакция на запис") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editedWeight,
                        onValueChange = { editedWeight = it },
                        label = { Text("Маса (kg)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editedHeight,
                        onValueChange = { editedHeight = it },
                        label = { Text("Височина (cm)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { genderMenuExpanded = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Пол: $editedGender")
                        }
                        DropdownMenu(
                            expanded = genderMenuExpanded,
                            onDismissRequest = { genderMenuExpanded = false }
                        ) {
                            listOf("Мъж", "Жена").forEach { genderOption ->
                                DropdownMenuItem(
                                    text = { Text(genderOption) },
                                    onClick = {
                                        editedGender = genderOption
                                        genderMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        )
    }

    // Основно съдържание
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onBack) {
                Text("Назад")
            }
            OutlinedButton(onClick = { viewModel.deleteAll() }) {
                Text("Изтрий всичко")
            }
        }

        Spacer(Modifier.height(16.dp))

        if (recordList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Няма записи")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(recordList) { record ->
                    HistoryItem(
                        record = record,
                        onDelete = { viewModel.delete(record) },
                        onEdit = {
                            editingRecord = record
                            editedWeight = record.weightKg.toString()
                            editedHeight = record.heightCm.toString()
                            editedGender = record.gender
                        },
                        onShare = {
                            onShare(formatRecordShareText(record))
                        }
                    )
                }
            }
        }
    }
}

private fun formatRecordShareText(record: BmiRecord): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val dateText = sdf.format(Date(record.timestamp))
    return String.format(
        Locale.getDefault(),
        "Пол: %s | Маса: %.1f kg | Ръст: %.0f cm | BMI: %.2f (%s) | Дата: %s",
        record.gender,
        record.weightKg,
        record.heightCm,
        record.bmi,
        record.category,
        dateText
    )
}

@Composable
private fun HistoryItem(
    record: BmiRecord,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onShare: () -> Unit
) {
    val sdf = remember { SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit() }
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = String.format(
                Locale.getDefault(),
                "Пол: %s | М: %.1f kg | Р: %.0f cm | BMI: %.2f (%s)",
                record.gender,
                record.weightKg,
                record.heightCm,
                record.bmi,
                record.category
            )
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = sdf.format(Date(record.timestamp))
        )
        Spacer(Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(onClick = onShare) {
                Text("Сподели")
            }
            Spacer(
                modifier = Modifier
                    .height(0.dp)
                    .padding(horizontal = 4.dp)
            )
            OutlinedButton(onClick = onDelete) {
                Text("Изтрий")
            }
        }
    }
}
