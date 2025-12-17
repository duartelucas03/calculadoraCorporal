/** GEMINI - INÍCIO
 * Prompt: Faça a implementação do banco de dados utilizando o Room. (codigo contextualizado em anexo)
 *
 */

package com.example.calculadoracorporal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CalcEntity::class], version = 1, exportSchema = false)
abstract class CalcDatabase : RoomDatabase() {

    abstract fun CalcDao(): CalcDao

    companion object {
        @Volatile
        private var INSTANCE: CalcDatabase? = null

        fun getDatabase(context: Context): CalcDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CalcDatabase::class.java,
                    "calculadora_corporal_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

/** GEMINI - FINAL */