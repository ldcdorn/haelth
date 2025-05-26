package com.github.ldcdorn.haelth.data

import java.sql.Date

data class DailyNutrition(
    val date: Date,
    val meals: List<Meal>
)