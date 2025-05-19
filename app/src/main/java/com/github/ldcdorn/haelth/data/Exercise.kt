package com.github.ldcdorn.haelth.data

import java.sql.Date

data class Exercise(
    val name: String,
    val type: String,
    val sets: Int,
    val reps: Int,
    val weight: Int,
    val date: Date
)
{
    val toString = "$name;$type;$sets;$reps;$weight;$date"
}

