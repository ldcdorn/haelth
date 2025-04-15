package com.github.ldcdorn.haelth.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import com.github.ldcdorn.haelth.ui.theme.HaelthTheme
import com.github.ldcdorn.haelth.viewmodel.MainViewModel
import com.github.ldcdorn.haelth.R
import kotlinx.coroutines.delay

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
        Spacer(modifier = Modifier.size(24.dp))
        Scaffold(
            bottomBar = {
                NavigationBar(navController = navController, viewModel = viewModel)
            },
            topBar = {
                Box(
                    modifier = Modifier.padding(top = 20.dp) // Verschiebe die TopBar leicht nach unten
                ) {
                    TopLabel(Modifier)
                }
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
        val sliderValue = remember { mutableStateOf(5f) } // Slider value in minutes (5 to 15 minutes)
        val timerLength = remember { mutableStateOf((sliderValue.value * 60).toInt()) } // Start with 5 minutes (300 seconds)
        val seconds = remember { mutableStateOf(timerLength.value) } // Current time in seconds for countdown
        val isRunning = remember { mutableStateOf(false) }
        val progress = remember { mutableStateOf(1f) } // Progress of the circular timer (1 = 100%)

        // Update timerLength and reset seconds immediately when slider changes
        LaunchedEffect(sliderValue.value) {
            timerLength.value = (sliderValue.value.toInt()) * 60 // Convert minutes to seconds, round to full minute
            seconds.value = timerLength.value // Reset the countdown to the new value
        }

        // Update the progress of the timer based on the remaining time
        LaunchedEffect(seconds.value) {
            progress.value = seconds.value / timerLength.value.toFloat() // Update progress based on the remaining seconds
        }

        // The main timer logic (reset when starting)
        LaunchedEffect(isRunning.value) {
            if (isRunning.value) {
                while (seconds.value > 0) {
                    delay(1000L)
                    seconds.value-- // Decrease the time by 1 second
                }
                isRunning.value = false // Stop the timer when seconds reach 0
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Mindfulness Tips at the Top
            Text(
                text = "Tips for Better Mindfulness",
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 24.sp),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = """
            - Take deep breaths.
            - Focus on the present moment.
            - Let go of distractions.
            - Practice gratitude daily.
            - Stay calm, even in stressful situations.
        """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Set Timer Length Label (Make this bigger)
            Text(
                text = "Set Timer Length: ${sliderValue.value.toInt()} min",
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 28.sp),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Slider for adjusting meditation timer length (5 to 15 minutes)
            TimerLengthSlider(sliderValue.value) { newValue ->
                sliderValue.value = newValue
            }

            // Meditation Timer
            MeditationTimer(timerLength.value, sliderValue.value, seconds.value, progress.value, isRunning.value) { isRunningState ->
                isRunning.value = isRunningState
                if (!isRunningState) {
                    seconds.value = timerLength.value // Reset the timer when stopped
                }
            }
        }
    }

    @Composable
    fun TimerLengthSlider(sliderValue: Float, onSliderValueChange: (Float) -> Unit) {
        Slider(
            value = sliderValue,
            onValueChange = onSliderValueChange,
            valueRange = 5f..15f, // Slider range between 5 and 15 minutes
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }

    @Composable
    fun MeditationTimer(
        timerLength: Int,
        sliderValue: Float,
        seconds: Int,
        progress: Float,
        isRunning: Boolean,
        onStartStopClicked: (Boolean) -> Unit
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Circular progress bar (time countdown)
            Box(
                modifier = Modifier
                    .size(200.dp) // Size of the circular progress
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = progress.coerceIn(0f, 1f), // Ensure the progress value is between 0 and 1
                    strokeWidth = 16.dp,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = String.format("%02d:%02d", seconds / 60, seconds % 60),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 48.sp),
                )
            }

            // Start/Stop button with modern design
            Button(
                onClick = {
                    // Start/Pause-Logik
                    if (isRunning) {
                        onStartStopClicked(false)
                    } else {
                        onStartStopClicked(true)
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary // Verwendung von colorScheme
                )
            ) {
                Text(
                    text = if (isRunning) "Reset" else "Start",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                    color = MaterialTheme.colorScheme.onPrimary // Verwendung von onPrimary aus colorScheme
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MeditationPagePreview() {
        MindfulnessScreen()
    }

    @Composable
    fun TopLabel(modifier: Modifier = Modifier) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .requiredWidth(width = 412.dp)
                .requiredHeight(height = 100.dp)
                .background(color = Color(0xffece6f0))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp,
                        vertical = 14.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Text(
                        text = "HÆLTH",
                        color = Color(0xff65558f),
                        textAlign = TextAlign.Center,
                        lineHeight = 1.12.em,
                        style = TextStyle(
                            fontSize = 57.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.25).sp),
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically))
                }
            }
        }
    }

    @Preview(widthDp = 412, heightDp = 100)
    @Composable
    private fun TopLabelPreview() {
        TopLabel(Modifier)
    }
    private fun navigateToActivity(targetActivity: Class<out ComponentActivity>) {
        val intent = Intent(this, targetActivity)
        startActivity(intent)
    } //Funktion, um die verschiedenen screens audzurufen
}