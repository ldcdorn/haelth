package com.github.ldcdorn.haelth.data

import java.sql.Date

data class Meal(
    val name: String,
    val calories: Int,
    val carbs: Int,
    val fats: Int,
    val protein: Int,
    val date: Date
) {
    override fun toString(): String {
        return "$name;$calories;$carbs;$protein;$fats;$date"
    }
}

