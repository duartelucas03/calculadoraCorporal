package com.example.calculadoracorporal.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CalcDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalculation(userCalc: CalcEntity)

    @Query("SELECT * FROM user_calc_table ORDER BY date DESC")
    fun getAllCalculations(): Flow<List<CalcEntity>>
}