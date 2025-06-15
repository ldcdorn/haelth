package com.github.ldcdorn.haelth.ui.fitness

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.unit.dp
import com.github.ldcdorn.haelth.R
import com.github.ldcdorn.haelth.ui.theme.HaelthTheme
import com.github.ldcdorn.haelth.presenter.Fitness
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.github.ldcdorn.haelth.data.Exercise
import java.io.File
import java.text.SimpleDateFormat
import java.sql.Date
import java.util.Locale


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
    fun ExerciseCard(
        name: String,
        type: String,
        sets: Int,
        reps: Int,
        weight: Int,
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
                        Text(text = type)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "$sets Sets á $reps Repetitions")
                        Text(text = "${weight}kg")
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


    @Preview
    @Composable
    fun PreviewExerciseCard(){
        ExerciseCard(name = "name", type = "type", sets = 5, reps = 10, weight = 20, onDelete = {}, modifier = Modifier)
    }

    @Composable
    fun WorkoutScreen(
        exerciseList: List<Exercise>,
        context: Context,

    ){

    }

    @Composable
    fun ExerciseScreen(
        context: Context
    ) {
        var workoutDates = remember { mutableStateOf(fitness.getWorkouts(context)
                                        .map{it.date }
            .distinct().sortedDescending()) }
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        var showOverlay by remember { mutableStateOf(false) }
        var expandedWorkoutDate by remember { mutableStateOf<String?>(null) }
        var workouts = remember { mutableStateOf(fitness.getWorkouts(context)) }


        Column(modifier = Modifier.fillMaxSize()) {


            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                items(workouts.value) { workout ->
                    Column {
                        DailyWorkoutCard(
                            dateText = formatter.format(workout.date),
                            onClick = {
                                expandedWorkoutDate = if (expandedWorkoutDate == workout.date.toString()) null else workout.date.toString()
                            }
                        )

                        if (expandedWorkoutDate == workout.date.toString()) {
                            workout.exercises.forEach { exercise ->
                                ExerciseCard(
                                    name = exercise.name,
                                    type = exercise.type,
                                    sets = exercise.sets,
                                    reps = exercise.reps,
                                    weight = exercise.weight,
                                    onDelete = {
                                        fitness.deleteExerciseFromLog(context, exercise)
                                        workouts.value = fitness.getWorkouts(context)
                                        workoutDates.value = fitness.getWorkouts(context)
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
                Text("Add Exercise for today")
            }
        }
        if (showOverlay) {
            var message by remember { mutableStateOf("") }
            AddExerciseOverlay(
                context = context,
                onNewEntryAdded = {
                    workoutDates.value = fitness.getWorkouts(context)
                        .map{it.date }
                        .distinct().sortedDescending()

                },
                onDismiss = { showOverlay = false },
                onConfirm = { name, type, sets, reps, weight ->
                    try {
                        val file = File(context.filesDir, "exercise-log.txt")
                        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(java.util.Date())
                        val entry = "$name;$type;$sets;$reps;$weight;$today"
                        file.appendText("$entry\n")
                        message = "Entry added!"
                        workouts.value = fitness.getWorkouts(context)
                    } catch (e: Exception) {
                        message = "Error: ${e.localizedMessage}"
                    }
                    Log.d("Overlay", "Exercise added: $name, $type, $sets x $reps, $weight kg")

                    showOverlay = false
                }
            )
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
                    onClick = { /* Preview */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }
    }

    @Composable
    fun AddExerciseOverlay(
        onDismiss: () -> Unit,
        onConfirm: (String, String, Int, Int, Int) -> Unit, // name, type, sets, reps, weight
        context: Context,
        onNewEntryAdded: () -> Unit,

    ) {
        // States für die Eingaben
        var name by remember { mutableStateOf("") }
        var type by remember { mutableStateOf("") }
        var sets by remember { mutableStateOf("") }
        var reps by remember { mutableStateOf("") }
        var weight by remember { mutableStateOf("") }

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
                        label = { Text("Exercise") },
                        modifier = Modifier.weight(1f),

                    )
                    OutlinedTextField(
                        value = type,
                        onValueChange = { type = it },
                        label = { Text("Type") },
                        modifier = Modifier.weight(1f),

                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = sets,
                        onValueChange = { sets = it },
                        label = { Text("Sets") },
                        modifier = Modifier
                            .weight(1f)
                            .widthIn(min = 100.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),

                    )
                    OutlinedTextField(
                        value = reps,
                        onValueChange = { reps = it },
                        label = { Text("Reps") },
                        modifier = Modifier.weight(1f).widthIn(min = 100.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),

                    )
                    OutlinedTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = { Text("Weight") },
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
                        if (name.isNotBlank() && type.isNotBlank() && sets.isNotBlank()
                            && reps.isNotBlank() && weight.isNotBlank()
                        ) {
                            onConfirm(
                                name,
                                type,
                                sets.toIntOrNull() ?: 0,
                                reps.toIntOrNull() ?: 0,
                                weight.toIntOrNull() ?: 0,
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




}