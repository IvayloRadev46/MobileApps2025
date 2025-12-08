package com.example.mobileapplicationtermp.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bmi_records")
data class BmiRecord(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val weightKg: Double,
    val heightCm: Double,
    val gender: String,
    val bmi: Double,
    val category: String,
    val timestamp: Long
)
