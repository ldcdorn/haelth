import android.content.Context
import com.github.ldcdorn.haelth.data.DB
import com.github.ldcdorn.haelth.data.DailyNutrition
import com.github.ldcdorn.haelth.data.Exercise
import com.github.ldcdorn.haelth.data.Meal
import java.sql.Date
import kotlin.collections.component1
import kotlin.collections.component2


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
}