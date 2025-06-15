package com.github.ldcdorn.haelth.presenter;

import android.content.Context
import java.sql.Date;
import com.github.ldcdorn.haelth.data.DB
import com.github.ldcdorn.haelth.data.Exercise
import com.github.ldcdorn.haelth.data.Workout

class Fitness {

    private val db = DB();

    public fun getWorkouts(context: Context): List<Workout> {
        val allExercises = db.loadExercises(context)

        return allExercises
            .groupBy { it.date }
            .map { (date, exercisesOnDate) ->
                Workout(date = date, exercises = exercisesOnDate)
            }
            .sortedByDescending { it.date }
    }

    public fun getExercisesOfWorkout(context: Context, date: Date): List<Exercise>{
        return db.loadExercises(context).filter { it.date == date }
    }

    public fun deleteExerciseFromLog(context: Context, exercise: Exercise){
        db.deleteExercise(context, exercise.toString)

    }

}