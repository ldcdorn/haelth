package com.github.ldcdorn.haelth.ui.nutrition

import Nutrition
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.github.ldcdorn.haelth.R
import com.github.ldcdorn.haelth.ui.theme.HaelthTheme
import com.github.ldcdorn.haelth.util.UtilFunctions
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale


class NutritionUI {

    private val nutrition = Nutrition()
    private val util = UtilFunctions()
    data class Goals(val caloriesGoal: Int, val carbsGoal: Int, val fatsGoal: Int, val proteinGoal: Int)
    val testGoals = Goals(3000,300,80,140)

    @Composable
    fun NutritionScreen(context: Context) {
        var nutritionDates = remember { mutableStateOf(nutrition.getDailyNutritions(context)
            .map{it.date }
            .distinct().sortedDescending()) }
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        var showOverlay by remember { mutableStateOf(false) }
        var expandedNutritionDate by remember { mutableStateOf<String?>(null) }
        var nutritions = remember { mutableStateOf(nutrition.getDailyNutritions(context)) }
        var refreshTrigger by remember { mutableStateOf(0) }
        var summary by remember(refreshTrigger) {
            mutableStateOf(nutrition.getTodayNutritionSummaryAsArray(context))
        }


        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            DailyGoalsCard(
                calories = summary[0],
                carbs = summary[1],
                fats = summary[2],
                protein = summary[3]
            )
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                items(nutritions.value) { dailyNutrition ->
                    Column {
                        DailyMealsCard(
                            dateText = formatter.format(dailyNutrition.date),
                            onClick = {
                                expandedNutritionDate = if (expandedNutritionDate == dailyNutrition.date.toString()) null else dailyNutrition.date.toString()
                            }
                        )

                        if (expandedNutritionDate == dailyNutrition.date.toString()) {
                            dailyNutrition.meals.forEach { meal ->
                                MealCard(
                                    name = meal.name,
                                    calories = meal.calories,
                                    carbs = meal.carbs,
                                    fats = meal.fats,
                                    protein = meal.protein,
                                    onDelete = {
                                        nutrition.deleteMealFromLog(context, meal)
                                        nutritions.value = nutrition.getDailyNutritions(context)
                                        nutritionDates.value = nutrition.getDailyNutritions(context)
                                            .map{it.date }
                                            .distinct().sortedDescending()
                                    }
                                )
                            }
                        }
                    }
                }
            }
            Button(
                onClick = { showOverlay = true },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("Add Meal for today")
            }
        }
        if (showOverlay) {
            var message by remember { mutableStateOf("") }
            AddMealOverlay(
                context = context,
                onNewEntryAdded = {
                    nutritionDates.value = nutrition.getDailyNutritions(context)
                        .map{it.date }
                        .distinct().sortedDescending()

                },
                onDismiss = { showOverlay = false },
                onConfirm = { name, calories, carbs, fats, protein ->

                    try {
                        val file = File(context.filesDir, "nutrition-log.txt")
                        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(java.util.Date())
                        var caloriesChecked = "0"
                        if(calories=="0"){
                            val temp = util.calculateCalories(protein = protein, carbs = carbs, fat = fats)
                            caloriesChecked = temp.toString();
                        }else{
                            caloriesChecked = calories
                        }
                        val entry = "$name;$caloriesChecked;$carbs;$fats;$protein;$today"
                        file.appendText("$entry\n")
                        message = "Entry added!"
                        nutritions.value = nutrition.getDailyNutritions(context)
                        Toast.makeText(context, "Meal added", Toast.LENGTH_SHORT).show()

                    } catch (e: Exception) {
                        message = "Error: ${e.localizedMessage}"
                    }
                    refreshTrigger++
                    showOverlay = false

                }
            )
        }
    }

    @Composable
    fun AddMealOverlay(
        onDismiss: () -> Unit,
        onConfirm: (String, String, Int, Int, Int) -> Unit, // name, type, sets, reps, weight
        context: Context,
        onNewEntryAdded: () -> Unit,

        ) {
        // States für die Eingaben
        var name by remember { mutableStateOf("") }
        var calories by remember { mutableStateOf("") }
        var carbs by remember { mutableStateOf("") }
        var fats by remember { mutableStateOf("") }
        var protein by remember { mutableStateOf("") }

        // Halbtransparenter Hintergrund
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(onClick = onDismiss),
            contentAlignment = Alignment.Center
        ) {
            // Innencontainer
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp))
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.weight(1f),

                        )
                    OutlinedTextField(
                        value = calories,
                        onValueChange = { calories = it },
                        label = { Text("Calories") },
                        modifier = Modifier.weight(1f),

                        )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = carbs,
                        onValueChange = { carbs = it },
                        label = { Text("Carbs") },
                        modifier = Modifier
                            .weight(1f)
                            .widthIn(min = 100.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),

                        )
                    OutlinedTextField(
                        value = fats,
                        onValueChange = { fats = it },
                        label = { Text("Fats") },
                        modifier = Modifier.weight(1f).widthIn(min = 100.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),

                        )
                    OutlinedTextField(
                        value = protein,
                        onValueChange = { protein = it },
                        label = { Text("Protein") },
                        modifier = Modifier.weight(1f).widthIn(min = 100.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),

                        )
                }
                var message by remember { mutableStateOf("") }

                Button(
                    /*
                                        onClick = {
                                            if (name.isNotBlank() && type.isNotBlank() && sets.isNotBlank()
                                                && reps.isNotBlank() && weight.isNotBlank()
                                            ) {
                                            try {
                                                val file = File(context.filesDir, "exercise-log.txt")
                                                val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(java.util.Date())
                                                val entry = "$name;$type;$sets;$reps;$weight;$today"
                                                file.appendText("$entry\n")
                                                message = "Entry added!"
                                                onNewEntryAdded()
                                            } catch (e: Exception) {
                                                message = "Error: ${e.localizedMessage}"
                                            }}
                                        },*/

                    onClick = {
                        if (name.isNotBlank() && calories.isNotBlank() && carbs.isNotBlank()
                            && fats.isNotBlank() && protein.isNotBlank()
                        ) {
                            onConfirm(
                                name,
                                calories,
                                carbs.toIntOrNull() ?: 0,
                                fats.toIntOrNull() ?: 0,
                                protein.toIntOrNull() ?: 0,
                            )
                            onDismiss()
                        }

                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Confirm")
                }
            }
        }
    }

    @Composable
    fun MealCard(
        name: String,
        calories: Int,
        carbs: Int,
        fats: Int,
        protein: Int,
        onDelete: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xffece6f0)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Min),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = name, fontWeight = FontWeight.Bold)
                        Text(text = "$calories calories")
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "${carbs}g of carbs, ${fats}g of fats, ${protein}g of proteins")

                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Black
                )
            }
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
                colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.size(200.dp),
                centerText = "Calories: $calories / ${testGoals.caloriesGoal}"
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
                    centerText = "Carbs",
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
                    centerText = "Fats",
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
                    centerText = "Proteins",
                    modifier = Modifier.size(60.dp)
                )
            }

        }
    }




    @Composable
    @Preview
    fun PreviewDailyGoalsCard(){
        DailyGoalsCard(2000,250,60,120)
    }
    @Composable
    fun NutritionGoalPieChart(
        data: Map<String, Float>,
        colors: List<Color>,
        modifier: Modifier = Modifier,
        centerText: String = ""
    ) {
        var actualSize: IntSize by remember { mutableStateOf(IntSize.Zero) }
        val total = data.values.sum().takeIf { it != 0f } ?: 1f
        val proportions = data.values.map { it / total }
        val angles = proportions.map { it * 360f }

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { newSize -> actualSize = newSize }) {
                var startAngle = -90f
                angles.forEachIndexed { index, angle ->
                    drawArc(
                        color = colors[index % colors.size],
                        startAngle = startAngle,
                        sweepAngle = angle,
                        useCenter = false,
                        style = Stroke(
                            width = actualSize.width * 0.11f,
                            cap = StrokeCap.Butt,
                            join = StrokeJoin.Round
                        )
                    )
                    startAngle += angle
                }
            }

            if (centerText.isNotEmpty()) {
                Text(
                    text = centerText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }


    @Preview
    @Composable
    fun NutritionGoalPieChart(){
        NutritionGoalPieChart()
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
                .clickable{onClick()}
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
                    contentDescription = "Edit Meals Icon",
                    tint = Color(0xff65558f),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Nutrition on $dateText",
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
                dateText = "09/14/2024",
                onClick = { /* TODO: Preview Click */ }
            )
        }
    }

}