package com.example.calculadoracorporal.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculadoracorporal.data.CalcEntity
import com.example.calculadoracorporal.ui.theme.Black
import com.example.calculadoracorporal.ui.theme.Blue
import com.example.calculadoracorporal.ui.theme.White
import com.example.calculadoracorporal.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(viewModel: HomeViewModel = viewModel()){

    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var activity by remember { mutableStateOf("") }

    // Variáveis para o Dropdown (Caixa de Seleção Flutuante)
    var gender by remember { mutableStateOf("") }
    var expandedGender by remember { mutableStateOf(false) }
    var expandedActivity by remember { mutableStateOf(false) }
    val options = listOf("Masculino", "Feminino") // Opções do menu
    val optionsActivity = listOf("Sedentário", "Leve", "Moderado", "Intenso")


    val resultMessage by viewModel.resultMessage.collectAsState()
    val textFieldError by viewModel.textFieldError.collectAsState()


    var showHistory by remember { mutableStateOf(false) }
    var selectedCalculation by remember { mutableStateOf<CalcEntity?>(null) }

    val focusManager = LocalFocusManager.current

    // --- LÓGICA DE NAVEGAÇÃO ENTRE AS 3 TELAS ---

    /** GEMINI - INÍCIO
     * Prompt: Faça a implementação da navegação entre as 3 telas. (codigo contextualizado em anexo)
     *
     */

    if (selectedCalculation != null) {

        DetailScreen(
            calc = selectedCalculation!!,
            onBack = { selectedCalculation = null }
        )

    } else if (showHistory) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {
            Button(
                onClick = { showHistory = false },
                colors = ButtonDefaults.buttonColors(containerColor = Blue),
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Voltar para Calculadora", color = White)
            }

            HistoryScreen(
                viewModel = viewModel,
                onItemClick = { itemClicado ->
                    selectedCalculation = itemClicado
                }
            )
        }

    } else {
        /** GEMINI - FINAL */
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Calculadora Corporal") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Blue,
                        titleContentColor = White
                    )
                )
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = White)
                    /** GPT-4 - INICIO
                     * Prompt: Faça com que o foco saia dos campos qunado o usuário clicar fora da tela.
                     *
                     */
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        focusManager.clearFocus()
                    }
                        /** GPT-4 - FINAL*/
                    .verticalScroll(rememberScrollState())
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Altura (cm)",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black,
                        modifier = Modifier.padding(20.dp, 100.dp, 0.dp, 0.dp)
                    )

                    Text(
                        text = "Peso (Kg)",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black,
                        modifier = Modifier.padding(20.dp, 100.dp, 20.dp, 0.dp)
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = height,
                        onValueChange = { if (it.length <= 3) height = it },
                        label = { Text("Ex: 165") },
                        modifier = Modifier.fillMaxWidth(0.5f).padding(20.dp, 0.dp, 0.dp, 20.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        colors = defaultTextFieldColors(),
                        isError = textFieldError
                    )

                    OutlinedTextField(
                        value = weight,
                        onValueChange = { if (it.length <= 7) weight = it },
                        label = { Text("Ex: 70.500") },
                        modifier = Modifier.fillMaxWidth(1f).padding(20.dp, 0.dp, 20.dp, 20.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = defaultTextFieldColors(),
                        isError = textFieldError
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Idade",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black,
                        modifier = Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)
                    )

                    Text(
                        text = "Sexo",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black,
                        modifier = Modifier.padding(20.dp, 20.dp, 20.dp, 0.dp)
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    OutlinedTextField(
                        value = age,
                        onValueChange = { if (it.length <= 3) age = it },
                        label = { Text("Ex: 22") },
                        modifier = Modifier.fillMaxWidth(0.5f).padding(20.dp, 0.dp, 0.dp, 20.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        colors = defaultTextFieldColors(),
                        isError = textFieldError
                    )

                    ExposedDropdownMenuBox(
                        expanded = expandedGender,
                        onExpandedChange = { expandedGender = !expandedGender },
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(20.dp, 0.dp, 20.dp, 20.dp)
                            .weight(2f)
                    ) {
                        OutlinedTextField(
                            value = gender,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Selecione") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGender)
                            },
                            colors = defaultTextFieldColors(),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            isError = textFieldError
                        )

                        ExposedDropdownMenu(
                            expanded = expandedGender,
                            onDismissRequest = { expandedGender = false },
                            modifier = Modifier.background(White)
                        ) {
                            options.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption, color = Black) },
                                    onClick = {
                                        gender = selectionOption
                                        expandedGender = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Nível de atividade física",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black,
                        modifier = Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)
                    )

                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    ExposedDropdownMenuBox(
                        expanded = expandedActivity,
                        onExpandedChange = { expandedActivity = !expandedActivity },
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(20.dp, 0.dp, 20.dp, 20.dp)
                            .weight(2f)
                    ) {
                        OutlinedTextField(
                            value = activity,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Selecione") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedActivity)
                            },
                            colors = defaultTextFieldColors(),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            isError = textFieldError
                        )

                        ExposedDropdownMenu(
                            expanded = expandedActivity,
                            onDismissRequest = { expandedActivity = false },
                            modifier = Modifier.background(White)
                        ) {
                            optionsActivity.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption, color = Black) },
                                    onClick = {
                                        activity = selectionOption
                                        expandedActivity = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }
                }


                Button(
                    onClick = { viewModel.calcularIMC(height, weight, activity, gender, age) },
                    colors = ButtonDefaults.buttonColors(containerColor = Blue),
                    modifier = Modifier.fillMaxWidth().height(150.dp).padding(50.dp)
                ) {
                    Text(
                        text = "Calcular",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                }

                Text(
                    text = resultMessage,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blue,
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    textAlign = TextAlign.Center
                )

                Button(
                    onClick = { showHistory = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Blue),
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                ) {
                    Text("Ver Histórico", color = White)
                }
            }
        }
    }
}

@Composable
fun defaultTextFieldColors() = TextFieldDefaults.colors(
    unfocusedContainerColor = White,
    focusedContainerColor = White,
    focusedLabelColor = Blue,
    focusedIndicatorColor = Blue,
    cursorColor = Blue,
    errorContainerColor = White,
    focusedTextColor = Black,
    unfocusedTextColor = Black
)


@Preview
@Composable
fun HomePreview(){
    Home()
}