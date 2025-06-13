package com.github.ldcdorn.haelth.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.github.ldcdorn.haelth.data.Meal
import java.sql.Date
import com.github.ldcdorn.haelth.data.Exercise
import java.io.File

class DB {
    val path: String =""
    var version: String ="1.1"

    fun checkIntegrity() {
        // TODO
    }

    fun saveWorkoutToFile(context: Context, workoutText: String) {
        val filename = "exercise-log.txt"
        try {
            context.openFileOutput(filename, Context.MODE_APPEND).use { output ->
                output.write((workoutText + "\n").toByteArray())
            }
            Toast.makeText(context, "Workout saved!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error saving: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    fun loadExercises(context: Context): List<Exercise> {
        val exercises = mutableListOf<Exercise>()
        val filename = "exercise-log.txt"
        val file = File(context.filesDir, filename)

        Log.d("DB", "loadExercises: Path = ${file.absolutePath}")
        if (!file.exists()) {
            Log.d("DB", "File does not exist yet.")
            return exercises
        }

        context.openFileInput(filename).bufferedReader().useLines { lines ->
            lines.forEachIndexed { index, line ->
                val parts = line.split(";")
                if (parts.size == 6) {
                    try {
                        val exercise = Exercise(
                            name   = parts[0],
                            type   = parts[1],
                            sets = parts[2].toInt(),
                            reps = parts[3].toInt(),
                            weight = parts[4].toInt(),
                            date   = Date.valueOf(parts[5])
                        )
                        exercises.add(exercise)
                    } catch (e: Exception) {
                        Log.e("DB", "Parsing-Error in line $index", e)
                    }
                }
            }
        }

        Log.d("DB", "Loaded Exercises: ${exercises.size}")
        return exercises
    }

    fun deleteExercise(context: Context, exerciseString: String) {
        val file = File(context.filesDir, "exercise-log.txt")
        if (!file.exists()) return

        val targetLine = exerciseString
        val lines = file.readLines().filter { it != targetLine }
        file.writeText(lines.joinToString("\n"))
    }

    fun loadGoals(context: Context): String {
        var goalsString = ""
        val filename = "nutrition-goals.txt"
        val file = File(context.filesDir, filename)

        Log.d("DB", "loadExercises: Path = ${file.absolutePath}")
        if (!file.exists()) {
            Log.d("DB", "File does not exist yet.")
            return goalsString
        }

        goalsString = file.readText()
        Log.d("DB", "loadedGoals: $goalsString")

        return goalsString
    }


    fun saveMealToFile(context: Context, mealText: String) {
        val filename = "nutrition-log.txt"
        try {
            context.openFileOutput(filename, Context.MODE_APPEND).use { output ->
                output.write((mealText + "\n").toByteArray())
            }
            Toast.makeText(context, "Meal saved!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error saving: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    fun loadMeals(context: Context): List<Meal> {
        val meals = mutableListOf<Meal>()
        val filename = "nutrition-log.txt"
        val file = File(context.filesDir, filename)

        Log.d("DB", "loadMeals: Path = ${file.absolutePath}")
        if (!file.exists()) {
            Log.d("DB", "File does not exist yet.")
            return meals
        }

        context.openFileInput(filename).bufferedReader().useLines { lines ->
            lines.forEachIndexed { index, line ->
                val parts = line.split(";")
                if (parts.size == 6) {
                    try {
                        val meal = Meal(
                            name   = parts[0],
                            calories   = parts[1].toInt(),
                            carbs = parts[2].toInt(),
                            protein = parts[3].toInt(),
                            fats = parts[4].toInt(),
                            date   = Date.valueOf(parts[5])
                        )
                        meals.add(meal)
                    } catch (e: Exception) {
                        Log.e("DB", "Parsing-Error in line $index", e)
                    }
                }
            }
        }

        Log.d("DB", "Loaded Meals: ${meals.size}")
        return meals
    }

    fun deleteMeal(context: Context, mealString: String) {
        val file = File(context.filesDir, "nutrition-log.txt")
        if (!file.exists()) return

        val targetLine = mealString
        val lines = file.readLines().filter { it != targetLine }
        file.writeText(lines.joinToString("\n"))
    }





}