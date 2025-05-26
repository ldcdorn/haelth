package com.github.ldcdorn.haelth.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import com.github.ldcdorn.haelth.ui.theme.HaelthTheme
import com.github.ldcdorn.haelth.viewmodel.MainViewModel
import com.github.ldcdorn.haelth.R
import kotlinx.coroutines.delay
import androidx.compose.foundation.lazy.items
import com.github.ldcdorn.haelth.data.Meal
import com.github.ldcdorn.haelth.ui.nutrition.NutritionUI
import com.github.ldcdorn.haelth.ui.fitness.FitnessUI
import com.github.ldcdorn.haelth.ui.mindfulness.MindfulnessUI

class MainActivity : ComponentActivity() {

    private val nutritionUI = NutritionUI();
    private val fitnessUI = FitnessUI();
    private val mindfulnessUI = MindfulnessUI();


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
        val context = LocalContext.current
        Spacer(modifier = Modifier.size(24.dp))
        Scaffold(
            bottomBar = {
                NavigationBar(navController = navController, viewModel = viewModel)
            },
            topBar = {
                Box(
                    modifier = Modifier.padding(top = 20.dp)
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
                composable("exercise") { fitnessUI.ExerciseScreen(context = context) }/*context, onCardClick = { dateString ->
                Toast.makeText(context, "Card clicked $dateString", Toast.LENGTH_SHORT).show()}*/

                composable("nutrition") { nutritionUI.NutritionScreen(context = context) }
                composable("mindfulness") { mindfulnessUI.MindfulnessScreen() }
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