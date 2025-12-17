package com.example.calculadoracorporal.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoracorporal.data.CalcEntity
import com.example.calculadoracorporal.viewmodel.HomeViewModel
import com.example.calculadoracorporal.viewmodel.HomeViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** GEMINI - INÍCIO
 * Prompt: Crie uma tabela com um histórico de entradas. (codigo contextualizado em anexo)
 *
 */
@Composable
fun HistoryScreen(
    viewModel: HomeViewModel,
    onItemClick: (CalcEntity) -> Unit
    ) {
    val historyList by viewModel.historyList.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Histórico de Cálculos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            Column {
                HeaderRow()

                LazyColumn(modifier = Modifier.height(600.dp)) {
                    items(historyList) { item ->
                        HistoryRow(
                            item = item,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderRow() {
    Row(
        modifier = Modifier
            .background(Color.LightGray)
            .border(1.dp, Color.Black)
    ) {
        TableCell(text = "ID", width = 50)
        TableCell(text = "Data", width = 120)
        TableCell(text = "Peso", width = 80)
        TableCell(text = "Altura", width = 80)
        TableCell(text = "IMC", width = 80)
        TableCell(text = "Classif.", width = 150)
        TableCell(text = "TMB", width = 80)
        TableCell(text = "Peso Ideal", width = 120)
        TableCell(text = "Calorias", width = 100)
    }
}

@Composable
fun HistoryRow(item: CalcEntity, onClick: () -> Unit) {
    Row(
        modifier = Modifier.border(1.dp, Color.Black).clickable { onClick() }
    ) {
        TableCell(text = item.id.toString(), width = 50)
        TableCell(text = convertDate(item.date), width = 120)
        TableCell(text = "${item.weight} kg", width = 80)
        TableCell(text = "${item.height} cm", width = 80)
        TableCell(text = "%.2f".format(item.imc), width = 80)
        TableCell(text = item.imcClassification, width = 150)
        TableCell(text = "%.0f".format(item.tmb), width = 80)
        TableCell(text = "%.2f".format(item.idealWeight), width = 120)
        TableCell(text = "%.0f".format(item.dailyCalories), width = 100)
    }
}

@Composable
fun TableCell(text: String, width: Int) {
    Text(
        text = text,
        modifier = Modifier
            .width(width.dp)
            .padding(8.dp),
        textAlign = TextAlign.Center,
        fontSize = 14.sp
    )
}

/** GEMINI - FINAL */



/** GEMINI - INÍCIO
 * Prompt: A data está com um erro na formatacao. Como defino corretamente?
 *
 */

fun convertDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

/** GEMINI - FINAL */
