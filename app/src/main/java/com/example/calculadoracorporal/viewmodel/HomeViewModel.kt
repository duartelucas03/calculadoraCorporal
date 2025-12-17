package com.example.calculadoracorporal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.calculadoracorporal.data.CalcDao
import com.example.calculadoracorporal.data.CalcEntity
import com.example.calculadoracorporal.domain.UseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dao: CalcDao,
    private val calcularIMCUseCase: UseCase = UseCase()
) : ViewModel() {

    private val _resultMessage = MutableStateFlow("")
    val resultMessage: StateFlow<String> = _resultMessage

    private val _textFieldError = MutableStateFlow(false)
    val textFieldError: StateFlow<Boolean> = _textFieldError

    /** GEMINI - INÍCIO
     * Prompt: Agora preciso criar outra tela, que exiba o histórico de entradas, com uma tabela exibindo identificador, data e hora, peso, altura, IMC, classificação de IMC, tmb, peso ideal e calorias diárias
     *
     */
    val historyList: StateFlow<List<CalcEntity>> = dao.getAllCalculations()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /** GEMINI - FINAL */

    fun calcularIMC(height: String, weight: String, activity: String, gender: String, age: String) {
        val result = calcularIMCUseCase.execute(height, weight, activity, gender, age)
        _resultMessage.value = result.message
        _textFieldError.value = result.hasError

        if (!result.hasError) {
            // Usamos os dados retornados pelo UseCase
            saveCalculationToDb(
                height = height.toDoubleOrNull() ?: 0.0,
                weight = weight.toDoubleOrNull() ?: 0.0,
                age = age,
                gender = gender,
                activity = activity,
                resultMessage = result.message,
                imc = result.imc ?: 0.0,
                imcClassification = result.imcClassification ?: "",
                tmb = result.tmb ?: 0.0,
                idealWeight = result.idealWeight ?: 0.0,
                dailyCalories = result.dailyCalories ?: 0.0
            )
        }
    }

    /** GEMINI - INÍCIO
     * Prompt: Faça a implementação do banco de dados utilizando o Room. (codigo conextualizado em anexo)
     *
     */

    private fun saveCalculationToDb(
        height: Double,
        weight: Double,
        age: String,
        gender: String,
        activity: String,
        resultMessage: String,
        imc: Double,
        imcClassification: String,
        tmb: Double,
        idealWeight: Double,
        dailyCalories: Double
    ) {
        // viewModelScope garante que a operação ocorra fora da Thread Principal
        viewModelScope.launch {
            val userCalc = CalcEntity(
                height = height,
                weight = weight,
                age = age,
                gender = gender,
                activityLevel = activity,
                result = resultMessage,
                imc = imc,
                imcClassification = imcClassification,
                tmb = tmb,
                idealWeight = idealWeight,
                dailyCalories = dailyCalories
            )
            dao.insertCalculation(userCalc)
        }
    }
}

// 3. Factory necessária para passar o DAO para o ViewModel
class HomeViewModelFactory(private val dao: CalcDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

/** GEMINI - FINAL */