package com.github.ldcdorn.haelth.ui.nutrition

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.github.ldcdorn.haelth.R
import com.github.ldcdorn.haelth.data.Meal
import com.github.ldcdorn.haelth.ui.theme.HaelthTheme
import java.sql.Date


class NutritionUI {
    data class Goals(val caloriesGoal: Int, val carbsGoal: Int, val fatsGoal: Int, val proteinGoal: Int)
    val testGoals = Goals(3000,300,80,140)
    @Composable
    fun NutritionScreen() {
        val meals = listOf(
            Meal("Meal 1", 500, 50, 20, 30, Date.valueOf("2000-10-10")),
            Meal("Meal 2", 600, 60, 25, 35, Date.valueOf("2000-10-10")),
            Meal("Meal 3", 450, 45, 18, 28, Date.valueOf("2000-10-10"))
        )

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), // Abstand zu den Bildschirmrändern
            //verticalArrangement = Arrangement.spacedBy(2.dp), // Abstand zwischen den Elementen)
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            DailyGoalsCard(2000, 250, 60, 120)
            Spacer(modifier = Modifier.weight(1f))
            DailyMealsLazyList(
                meals = meals,
                onMealClick = { clickedMeal ->
                    println("Clicked on ${clickedMeal.name}")
                }
            )
        }}

    @Composable
    fun DailyMealsLazyList(
        meals: List<Meal>,
        onMealClick: (Meal) -> Unit
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(meals) { meal: Meal ->  // Explicitly mark type as meal
                DailyMealsCard(
                    dateText = meal.name,
                    onClick = { onMealClick(meal) }
                )
            }
        }

    }
    @Preview(showBackground = true)
    @Composable
    fun PreviewNutritionScreen() {
        HaelthTheme {
            NutritionScreen()
        }
    }

    @Composable
    fun DailyGoalsCard(calories: Int, carbs: Int, fats: Int, protein: Int) {
        val caloriesFloat = calories.toFloat()
        val carbsFloat = carbs.toFloat()
        val fatsFloat = fats.toFloat()
        val proteinFloat = protein.toFloat()
        val remainingCaloriesFloat = testGoals.caloriesGoal - caloriesFloat
        val remainingFatsFloat = testGoals.fatsGoal - fatsFloat
        val remainingCarbsFloat = testGoals.carbsGoal - carbsFloat
        val remainingProteinFloat = testGoals.proteinGoal - proteinFloat


        Row(modifier = Modifier
            .padding(20.dp),
            horizontalArrangement = Arrangement.Center,
            //verticalAlignment = Alignment.CenterVertically
        ) {

            NutritionGoalPieChart(
                data = mapOf(
                    "Erreicht" to caloriesFloat,
                    "Übrig" to remainingCaloriesFloat,
                ),
                colors = listOf(MaterialTheme.colorScheme.primary,MaterialTheme.colorScheme.primaryContainer,   Color.Green, Color.Yellow),
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.size(20.dp))

            Column() {


                NutritionGoalPieChart(
                    data = mapOf(
                        "Erreicht" to carbsFloat,
                        "Übrig" to remainingCarbsFloat,
                    ),
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer,
                        Color.Green,
                        Color.Yellow
                    ),
                    modifier = (Modifier.size(60.dp)
                            )
                )
                Spacer(modifier = Modifier.size(10.dp))
                NutritionGoalPieChart(
                    data = mapOf(
                        "Erreicht" to fatsFloat,
                        "Übrig" to remainingFatsFloat,
                    ),
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer,
                        Color.Green,
                        Color.Yellow
                    ),
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                NutritionGoalPieChart(
                    data = mapOf(
                        "Erreicht" to proteinFloat,
                        "Übrig" to remainingProteinFloat,
                    ),
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer,
                        Color.Green,
                        Color.Yellow
                    ),
                    modifier = Modifier.size(60.dp)
                )
            }

        }
    }

    @Composable
    fun DailyMealsCard(
        dateText: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Card(
            onClick = onClick,
            colors = CardDefaults.cardColors(
                containerColor = Color(0xffece6f0)
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(72.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_edit),
                    contentDescription = "Edit Meal Icon",
                    tint = Color(0xff65558f),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = dateText,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xff65558f))
                )
            }
        }
    }

    @Preview(showBackground = true, widthDp = 400)
    @Composable
    private fun DailyMealsCardPreview() {
        HaelthTheme {
            DailyMealsCard(
                dateText = "Meals on 09/14/2024",
                onClick = { /* TODO: Preview Click */ }
            )
        }
    }


    @Composable
    @Preview
    fun PreviewDailyGoalsCard(){
        DailyGoalsCard(2000,250,60,120)
    }
    @Composable
    fun NutritionGoalPieChart(
        data: Map<String, Float>, // Key: Label, Value: Percentage
        colors: List<Color>,
        modifier: Modifier = Modifier
    ) {
        var actualSize: IntSize = IntSize(0, 0)
        val total = data.values.sum()
        val proportions = data.values.map { it / total }
        val angles = proportions.map { it * 360f }



        Canvas(modifier = modifier
            .onSizeChanged { newSize -> actualSize = newSize }) {
            var startAngle = -90f
            angles.forEachIndexed { index, angle ->
                drawArc(
                    color = colors[index % colors.size],
                    startAngle = startAngle,
                    sweepAngle = angle,
                    useCenter = false,
                    style = Stroke(
                        width = actualSize.width*0.11f,
                        cap = StrokeCap.Butt,
                        join = StrokeJoin.Round
                    )
                )
                startAngle += angle
            }
        }
    }


}