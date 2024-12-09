package com.github.ldcdorn.haelth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.github.ldcdorn.haelth.ui.theme.HaelthTheme
class MainActivity : ComponentActivity() {
    //Variablen für Nutrition
    data class Meal(var name: String, var calories: Int, var carbs: Int, var fats: Int, var protein: Int)
    data class Goals(val caloriesGoal: Int, val carbsGoal: Int, val fatsGoal: Int, val proteinGoal: Int)
    val testGoals = Goals(3000,300,80,140)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            MainScreen(viewModel = viewModel)
        }
    }


    @Composable
    fun MainScreen(viewModel: MainViewModel) {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                NavigationBar(navController = navController, viewModel = viewModel)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "exercise",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("exercise") { ExerciseScreen() }
                composable("nutrition") { NutritionScreen() }
                composable("mindfulness") { MindfulnessScreen() }
            }
        }
    }
    @Composable
    fun NutritionScreen() {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), // Abstand zu den Bildschirmrändern
            //verticalArrangement = Arrangement.spacedBy(2.dp), // Abstand zwischen den Elementen)
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            DailyGoalsCard(2000, 250, 60, 120)
            Spacer(modifier = Modifier.weight(1f))
            DailyMealsCard()
            DailyMealsCard()
            DailyMealsCard()
        }}

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
    fun DailyMealsCard(modifier: Modifier = Modifier) {
        Spacer(modifier = modifier.size(20.dp))
        FloatingActionButton(
            onClick = { },
            containerColor = Color(0xffece6f0),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .requiredWidth(width = 361.dp)
                    .requiredHeight(height = 56.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp,
                            end = 20.dp,
                            top = 16.dp,
                            bottom = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_edit),
                        contentDescription = "Icon",
                        tint = Color(0xff65558f))
                    Text(
                        text = "Meals on 09/14/2024",
                        color = Color(0xff65558f),
                        textAlign = TextAlign.Center,
                        lineHeight = 1.43.em,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically))
                }
            }
        }
    }



    @Preview(widthDp = 361, heightDp = 56)
    @Composable
    private fun DailyMealsCardPreview() {
        DailyMealsCard(Modifier)
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

    @Composable
    fun NavigationBar(navController: NavHostController, viewModel: MainViewModel) {
        val NavigationIconSize = 26.dp
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

        androidx.compose.material3.NavigationBar(
            containerColor = Color(0xfff3edf7),
            contentColor = Color(0xff4a4459)
        ) {
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_exercise),
                        contentDescription = "Exercise Icon",
                        tint = Color(0xff4a4459),
                        modifier = Modifier.size(NavigationIconSize)
                    )
                },
                label = { Text("Exercise") },
                selected = currentDestination == "exercise",
                onClick = { navController.navigate("exercise") }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_nutrition),
                        contentDescription = "Nutrition Icon",
                        tint = Color(0xff4a4459),
                        modifier = Modifier.size(NavigationIconSize)
                    )
                },
                label = { Text("Nutrition") },
                selected = currentDestination == "nutrition",
                onClick = { navController.navigate("nutrition") }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_mindfulness),
                        contentDescription = "Mindfulness Icon",
                        tint = Color(0xff4a4459),
                        modifier = Modifier.size(NavigationIconSize)
                    )
                },
                label = { Text("Mindfulness") },
                selected = currentDestination == "mindfulness",
                onClick = { navController.navigate("mindfulness") }
            )
        }
    }
    @Composable
    fun ExerciseScreen() {
        Text("Exercise Screen Content")
    }



    @Composable
    fun MindfulnessScreen() {
        Text("Mindfulness Screen Content")
    }

    private fun navigateToActivity(targetActivity: Class<out ComponentActivity>) {
        val intent = Intent(this, targetActivity)
        startActivity(intent)
    } //Funktion, um die verschiedenen screens audzurufen
}