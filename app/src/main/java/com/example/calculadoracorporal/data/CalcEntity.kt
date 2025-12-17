package com.example.calculadoracorporal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_calc_table")
data class CalcEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val height: Double,
    val weight: Double,
    val age: String,
    val gender: String,
    val activityLevel: String,
    val result: String,
    val date: Long = System.currentTimeMillis(),
    val imc: Double,
    val imcClassification: String,
    val tmb: Double,
    val idealWeight: Double,
    val dailyCalories: Double
)