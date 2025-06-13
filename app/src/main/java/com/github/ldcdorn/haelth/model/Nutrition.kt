import android.content.Context
import com.github.ldcdorn.haelth.data.DB
import com.github.ldcdorn.haelth.data.DailyNutrition
import com.github.ldcdorn.haelth.data.Exercise
import com.github.ldcdorn.haelth.data.Goals
import com.github.ldcdorn.haelth.data.Meal
import java.sql.Date
import kotlin.collections.component1
import kotlin.collections.component2
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale


class Nutrition {

    private val db = DB();

    public fun getDailyNutritions(context: Context): List<DailyNutrition> {
        val allMeals = db.loadMeals(context)

        return allMeals
            .groupBy { it.date }
            .map { (date, mealsOnDate) ->
                DailyNutrition(date = date, meals = mealsOnDate)
            }
            .sortedByDescending { it.date }
    }

    public fun getMealsOfDay(context: Context, date: Date): List<Meal>{
        return db.loadMeals(context).filter { it.date == date }
    }

    public fun deleteMealFromLog(context: Context, meal: Meal){
        db.deleteExercise(context, meal.toString())

    }

    public fun getGoals(context: Context): Goals{
        val goalString = db.loadGoals(context)
        val values = goalString.split(";").map { it.toInt() }

        return Goals(
            caloriesGoal = values[0],
            carbsGoal = values[1],
            fatsGoal = values[2],
            proteinGoal = values[3]
        )
    }


    fun getTodayNutritionSummaryAsArray(context: Context): IntArray {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(java.util.Date())

        var caloriesSum = 0
        var carbsSum = 0
        var fatsSum = 0
        var proteinsSum = 0

        val file = context.getFileStreamPath("nutrition-log.txt")
        if (!file.exists()) return intArrayOf(0, 0, 0, 0)

        BufferedReader(InputStreamReader(context.openFileInput("nutrition-log.txt"))).useLines { lines ->
            lines.forEach { line ->
                val parts = line.split(";")
                if (parts.size == 6 && parts[5] == today) {
                    caloriesSum += parts[1].toIntOrNull() ?: 0
                    carbsSum += parts[2].toIntOrNull() ?: 0
                    fatsSum += parts[3].toIntOrNull() ?: 0
                    proteinsSum += parts[4].toIntOrNull() ?: 0
                }
            }
        }

        return intArrayOf(caloriesSum, carbsSum, fatsSum, proteinsSum)
    }

}