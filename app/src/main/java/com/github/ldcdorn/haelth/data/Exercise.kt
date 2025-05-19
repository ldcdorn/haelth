package com.github.ldcdorn.haelth.data

import java.sql.Date

data class Exercise(
    val name: String,
    val type: String,
    val weight: Int,
    val amount: Int,
    val date: Date
)

