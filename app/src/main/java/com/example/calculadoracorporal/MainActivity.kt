package com.example.calculadoracorporal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculadoracorporal.data.CalcDatabase
import com.example.calculadoracorporal.ui.theme.CalculadoraCorporalTheme
import com.example.calculadoracorporal.view.Home
import com.example.calculadoracorporal.viewmodel.HomeViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /** GEMINI - INÍCIO
         * Prompt: Faça a implementação do banco de dados utilizando o Room. (codigo conextualizado em anexo)
         *
         */
        // 1. Inicializa o Banco
        val db = CalcDatabase.getDatabase(applicationContext)

        // 2. Cria a Factory passando o DAO
        val factory = HomeViewModelFactory(db.CalcDao())

        /** GEMINI - FINAL */

        setContent {
            Box(
                modifier = Modifier
                    .safeDrawingPadding()
            ) {
                CalculadoraCorporalTheme {
                    Home(viewModel = viewModel(factory = factory))
                }
            }
        }
    }
}

