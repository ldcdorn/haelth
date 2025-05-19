package com.github.ldcdorn.haelth.ui.mindfulness

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MindfulnessUI {
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

}