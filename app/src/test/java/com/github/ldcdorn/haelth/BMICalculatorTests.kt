package com.github.ldcdorn.haelth

import com.github.ldcdorn.haelth.util.UtilFunctions
import junit.framework.TestCase.assertEquals
import org.junit.Test
import kotlin.Double
import kotlin.math.round

class BMICalculatorTests {
    private val util = UtilFunctions()

    @Test
    fun calculateBMI_multipleCases() {
        data class BMITestCase(val weightKg: Double, val heightCm: Double, val expectedBMI: Double)

        val testCases = listOf(
            // Normal cases
            BMITestCase(70.0, 175.0, 22.9),
            BMITestCase(50.0, 160.0, 19.5),
            BMITestCase(90.0, 180.0, 27.8),
            BMITestCase(110.0, 170.0, 38.1),

            // Edge Values
            BMITestCase(55.5, 175.0, 18.1),   // just under 18.5 -> Underweight
            BMITestCase(56.6, 175.0, 18.5),   // exactly 18.5 → Normal weight
            BMITestCase(76.6, 175.0, 25.0),   // exactly 25.0 → Overweight
            BMITestCase(91.9, 175.0, 30.0),   // exactly 30.0 → Obesity

            // Extreme values
            BMITestCase(30.0, 200.0, 7.5),     // extremely low BMI
            BMITestCase(200.0, 150.0, 88.9),   // extremely high BMI
            BMITestCase(0.1, 100.0, 0.1)       // nearly no weight
        )

        for (test in testCases) {
            val bmi = util.calculateBMI(test.weightKg, test.heightCm)
            val rounded = round(bmi * 10) / 10
            assertEquals(
                "Error for weight ${test.weightKg}kg and size ${test.heightCm}cm",
                test.expectedBMI,
                rounded,
                0.1
            )
        }
    }

    @Test
    fun classifyBMI_multipleCases() {
        val testCases = listOf(
            // Underweight
            16.0 to "Underweight",
            18.4 to "Underweight",

            // Border between Underweight and normal weight
            18.5 to "Normal weight",

            // Normal weight
            20.0 to "Normal weight",
            24.9 to "Normal weight",

            // Border between normal weight and Overweight
            25.0 to "Overweight",

            // Overweight
            27.0 to "Overweight",
            29.9 to "Overweight",

            // Border to Obesity
            30.0 to "Obesity",

            // Obesity
            35.0 to "Obesity",
            88.8 to "Obesity"
        )

        for ((bmi, expectedCategory) in testCases) {
            val result = util.classifyBMI(bmi)
            assertEquals("Error for BMI $bmi", expectedCategory, result)
        }
    }
}