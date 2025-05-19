package com.github.ldcdorn.haelth.data

import java.sql.Date


data class Workout(
    val date: Date,
    val exercises: List<Exercise>
)
