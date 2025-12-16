package com.example.calculadoracorporal.domain

class UseCase {

    fun execute(height: String, weight: String): IMCResult {

        if (height.isEmpty() && weight.isEmpty()) {
            return IMCResult("Preencha todos os campos", true)
        }

        val weightFormatted = weight.replace(",", ".").toDoubleOrNull()
        val heightNumber = height.toDoubleOrNull()

        if (weightFormatted == null || heightNumber == null) {
            return IMCResult("Valores inválidos", true)
        }

        val imc = weightFormatted / ((heightNumber / 100) * (heightNumber / 100))
        val imcFormatted = String.format("%.2f", imc)


        val message = when {
            imc < 18.5 -> "IMC $imcFormatted \n Abaixo do peso"
            imc in 18.5..24.9 -> "IMC $imcFormatted \n Peso normal"
            imc in 25.0..29.9 -> "IMC $imcFormatted \n Sobrepeso"
            imc in 30.0..34.9 -> "IMC $imcFormatted \n Obesidade (Grau 1)"
            imc in 35.0..39.9 -> "IMC $imcFormatted \n Obesidade (Grau 2)"
            else -> "IMC $imcFormatted \n Obesidade Mórbida (Grau 3)"
        }

        return IMCResult(message, false)

    }
}

data class IMCResult(
    val message: String,
    val hasError: Boolean
)