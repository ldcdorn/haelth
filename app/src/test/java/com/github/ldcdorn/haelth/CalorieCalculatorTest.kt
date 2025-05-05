package com.github.ldcdorn.haelth

import com.github.ldcdorn.haelth.util.UtilFunctions
import junit.framework.TestCase.assertEquals
import org.junit.Test


class CalorieCalculatorTest {
    private val util = UtilFunctions()
    @Test
    fun calculateCalories_multipleCases() {
        val testCases = listOf(
            Triple(0, 0, 0) to 0,
            Triple(10, 0, 0) to 40,
            Triple(0, 10, 0) to 40,
            Triple(0, 0, 10) to 90,
            Triple(5, 10, 2) to (5 * 4 + 10 * 4 + 2 * 9),
            Triple(-5, -20, -10) to -190 // Negative values are allowed in case user wants to correct some previous values or hasn't finished his meal
            )

        for ((input, expected) in testCases) {
            val (protein, carbs, fat) = input
            val actual = util.calculateCalories(protein, carbs, fat)
            assertEquals("Error with input: $protein/$carbs/$fat", expected, actual)
        }
    }

}