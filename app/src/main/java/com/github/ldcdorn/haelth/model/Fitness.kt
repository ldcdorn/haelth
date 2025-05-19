package com.github.ldcdorn.haelth.model;

import android.content.Context
import java.sql.Date;
import com.github.ldcdorn.haelth.data.DB

class Fitness {

    private val db = DB();

    // List of days workouts happened on
    public fun getWorkoutDates(context: Context): List<Date> {
        return db.loadExercises(context)
            .map { it.date }
            .distinct()
            .sortedDescending()
    }
}