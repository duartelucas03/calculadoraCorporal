package com.example.calculadoracorporal.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoracorporal.data.CalcEntity
import com.example.calculadoracorporal.ui.theme.Blue
import com.example.calculadoracorporal.ui.theme.White

@Composable
fun DetailScreen(
    calc: CalcEntity,
    onBack: () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(White)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Detalhes do Cálculo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Blue,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Text(
                text = "Data: ${convertDate(calc.date)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Blue,
                modifier = Modifier.padding(bottom = 10.dp)
            )


            Text(
                text = calc.result,
                fontSize = 18.sp,
                color = Blue,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Text(
                text = "\nENTRADAS:",
                fontSize = 18.sp,
                color = Blue,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Text(
                text = """
                    Altura: ${calc.height} cm
                    Peso: ${calc.weight} kg 
                    Idade: ${calc.age}
                    Sexo: ${calc.gender}
                    Nível de atividade física: ${calc.activityLevel}
                    
                    """.trimIndent(),
                fontSize = 18.sp,
                color = Blue,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = Blue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Voltar", color = White)
            }
        }
    }
}
