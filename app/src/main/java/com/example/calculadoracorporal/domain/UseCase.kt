package com.example.calculadoracorporal.domain

class UseCase {

    fun execute(height: String, weight: String, activity: String, gender: String, age: String): IMCResult {

        if (height.isEmpty() || weight.isEmpty() || activity.isEmpty() || gender.isEmpty() || age.isEmpty()) {
            return IMCResult("Preencha todos os campos", true)
        }

        val weightFormatted = weight.replace(",", ".").toDoubleOrNull()
        val heightNumber = height.toDoubleOrNull()
        val ageNumber = age.toIntOrNull()

        if (weightFormatted == null || heightNumber == null || ageNumber == null || weightFormatted <= 0 || heightNumber <= 0 || ageNumber <= 0 || weightFormatted > 500 || heightNumber > 300 || ageNumber > 150) {
            return IMCResult("Valores inválidos", true)
        }

        val imc = weightFormatted / ((heightNumber / 100) * (heightNumber / 100))
        val imcFormatted = String.format("%.2f", imc)

        val tmb = (10 * weightFormatted) + (6.25 * heightNumber) - (5 * ageNumber)

        val tmbFinal = if (gender == "Masculino") {
            tmb + 5
        }else {
            tmb - 161
        }

        val tmbFormatted = String.format("%.2f", tmbFinal)

        val heightInches = heightNumber / 2.54
        val idealWeight = 2.3 * (heightInches - 60)

        val idealWeightFinal = if (gender == "Masculino") {
            idealWeight + 50
        }else {
            idealWeight + 45.5
        }

        val idealWeightFormatted = String.format("%.2f", idealWeightFinal)


        val dailyCalories = when {
            activity == "Sedentario" -> tmbFinal * 1.2
            activity == "Leve" -> tmbFinal * 1.375
            activity == "Moderado" -> tmbFinal * 1.55
            else -> tmbFinal * 1.725
        }

        val dailyCaloriesFormatted = String.format("%.2f", dailyCalories)

        val imcClassification = when {
            imc < 18.5 -> "Abaixo do peso"
            imc in 18.5..24.9 -> "Peso normal"
            imc in 25.0..29.9 -> "Sobrepeso"
            imc in 30.0..34.9 -> "Obesidade (Grau 1)"
            imc in 35.0..39.9 -> "Obesidade (Grau 2)"
            else -> "Obesidade Mórbida (Grau 3)"
        }

        val message = """
            IMC: $imcFormatted
            Classificação IMC: $imcClassification
            TMB: $tmbFormatted
            Peso Ideal: $idealWeightFormatted
            Calorias Diárias: $dailyCaloriesFormatted
            
            ------------------------------
            ENTENDA SEUS DADOS:
            
            Faixas do IMC (kg/m²):
            • < 18.5: Abaixo do peso
            • 18.5 a 24.9: Peso ideal
            • 25.0 a 29.9: Sobrepeso
            • 30.0 a 34.9: Obesidade Grau I
            • 35.0 a 39.9: Obesidade Grau II
            • ≥ 40.0: Obesidade Mórbida
            
            Glossário:
            • TMB (Taxa Metabólica Basal): Quanto seu corpo gasta de energia apenas para sobreviver (respirar, bater coração) em repouso absoluto.
            • Peso Ideal: Uma estimativa de peso saudável baseada apenas na sua altura e sexo.
            • Calorias Diárias: Estimativa de energia necessária para MANTER o seu peso atual considerando seu nível de atividade física.
        """.trimIndent()

        return IMCResult(message, false, imc, imcClassification, tmbFinal, idealWeightFinal, dailyCalories)

    }
}

data class IMCResult(
    val message: String,
    val hasError: Boolean,
    val imc: Double? = null,
    val imcClassification: String? = null,
    val tmb: Double? = null,
    val idealWeight: Double? = null,
    val dailyCalories: Double? = null
)