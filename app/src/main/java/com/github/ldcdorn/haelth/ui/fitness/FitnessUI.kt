package com.github.ldcdorn.haelth.ui.fitness

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
import android.content.Context
import androidx.compose.ui.unit.dp
import com.github.ldcdorn.haelth.R
import com.github.ldcdorn.haelth.data.Meal
import com.github.ldcdorn.haelth.ui.theme.HaelthTheme
import com.github.ldcdorn.haelth.model.Fitness
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.sql.Date

class FitnessUI {
    private val fitness = Fitness()


    @Composable
    fun DailyWorkoutCard(
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
                    contentDescription = "Edit Workout Icon",
                    tint = Color(0xff65558f),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Workout on $dateText",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xff65558f))
                )
            }
        }
    }

    //@Preview(showBackground = true, widthDp = 400)
    @Composable
    private fun DailyWorkoutCardPreview() {
        HaelthTheme {
            DailyWorkoutCard(
                dateText = "09/14/2024",
                onClick = { /* TODO: Preview Click */ }
            )
        }
    }

    @Composable
    fun ExerciseScreen(
        context: Context,
        onCardClick: (String) -> Unit
    ) {
        var workoutDates = remember { mutableStateOf(fitness.getWorkoutDates(context)) }
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.US)

        Column(modifier = Modifier.fillMaxSize()) {
            TrainingLogTestScreen(
                context = context,
                onNewEntryAdded = {
                    workoutDates.value = fitness.getWorkoutDates(context)
                }
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                items(workoutDates.value) { date ->
                    DailyWorkoutCard(
                        dateText = formatter.format(date),
                        onClick = { onCardClick(date.toString()) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ExerciseDiaryScreenPreview() {
        // Read dummy data like a file
        val dummyDates = listOf(
            Date.valueOf("2025-05-17"),
            Date.valueOf("2025-05-16"),
            Date.valueOf("2025-05-15")
        )

        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(dummyDates) { date ->
                DailyWorkoutCard(
                    dateText = formatter.format(date),
                    onClick = { /* nichts in Preview */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }
    }

    @Composable
    fun TrainingLogTestScreen(
        context: Context,
        onNewEntryAdded: () -> Unit
    ) {
        var message by remember { mutableStateOf("") }

        Button(onClick = {
            try {
                val file = File(context.filesDir, "exercise-log.txt")
                val eintrag = "Testexercise;Testtype;30;10;2025-05-19"
                file.appendText("$eintrag\n")
                message = "Entry added!"
                onNewEntryAdded() // 🔁 Aktualisieren der Liste
            } catch (e: Exception) {
                message = "Error: ${e.localizedMessage}"
            }
        }) {
            Text("Add test entry")
        }

        if (message.isNotEmpty()) {
            Text(text = message)
        }
    }



}