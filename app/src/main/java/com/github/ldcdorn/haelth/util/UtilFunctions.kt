package com.github.ldcdorn.haelth.util


// This class will contain functions that other classes will often be using and that (most likely)
// wont be changed often
class UtilFunctions {

    public fun calculateCalories(protein: Int, carbs: Int, fat: Int): Int {
        return (protein * 4) + (carbs * 4) + (fat * 9)
    }
    fun calculateBMI(weightKg: Double, heightCm: Double): Double {
        val heightM = heightCm / 100
        return weightKg / (heightM * heightM)
    }
    fun classifyBMI(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 25 -> "Normal weight"
            bmi < 30 -> "Overweight"
            else -> "Obesity"
        }
    }



}