package com.example.calculadoracorporal.viewmodel

import androidx.lifecycle.ViewModel
import com.example.calculadoracorporal.domain.UseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    private val calcularIMCUseCase: UseCase = UseCase()
) : ViewModel() {

    private val _resultMessage = MutableStateFlow("")
    val resultMessage: StateFlow<String> = _resultMessage

    private val _textFieldError = MutableStateFlow(false)
    val textFieldError: StateFlow<Boolean> = _textFieldError

    fun calcularIMC(height: String, weight: String) {
        val result = calcularIMCUseCase.execute(height, weight)
        _resultMessage.value = result.message
        _textFieldError.value = result.hasError
    }
}
