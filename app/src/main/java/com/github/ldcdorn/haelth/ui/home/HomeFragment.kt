package com.github.ldcdorn.haelth.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.compose.ui.platform.ComposeView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.material.Slider
import kotlinx.coroutines.delay

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        return ComposeView(requireContext()).apply {
            setContent {
                MeditationPage()
            }
        }
    }
}

@Composable
fun MeditationPage() {
    var sliderValue by remember { mutableStateOf(5f) } // Slider value in minutes (5 to 15 minutes)
    var timerLength by remember { mutableStateOf((sliderValue * 60).toInt()) } // Start with 5 minutes (300 seconds)
    var seconds by remember { mutableStateOf(timerLength) } // Current time in seconds for countdown
    var isRunning by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(1f) } // Progress of the circular timer (1 = 100%)

    // Update timerLength and reset seconds immediately when slider changes
    LaunchedEffect(sliderValue) {
        timerLength = (sliderValue.toInt()) * 60 // Convert minutes to seconds, round to full minute
        seconds = timerLength // Reset the countdown to the new value
    }

    // Update the progress of the timer based on the remaining time
    LaunchedEffect(seconds) {
        progress = seconds / timerLength.toFloat() // Update progress based on the remaining seconds
    }

    // The main timer logic (reset when starting)
    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (seconds > 0) {
                delay(1000L)
                seconds--
                progress = seconds / timerLength.toFloat() // Update progress based on remaining seconds
            }
            isRunning = false
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
            style = MaterialTheme.typography.h5.copy(fontSize = 24.sp),
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
            style = MaterialTheme.typography.body1.copy(fontSize = 16.sp),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Set Timer Length Label (Make this bigger)
        Text(
            text = "Set Timer Length: ${sliderValue.toInt()} min",
            style = MaterialTheme.typography.h6.copy(fontSize = 28.sp),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Slider for adjusting meditation timer length (5 to 15 minutes)
        TimerLengthSlider(sliderValue) { newValue ->
            sliderValue = newValue
        }

        // Meditation Timer
        MeditationTimer(timerLength, sliderValue, seconds, progress, isRunning) { isRunningState ->
            isRunning = isRunningState
            if (!isRunningState) {
                seconds = timerLength // Reset the timer when stopped
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
                progress = progress, // Display the progress here
                strokeWidth = 16.dp,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = String.format("%02d:%02d", seconds / 60, seconds % 60),
                style = MaterialTheme.typography.h4.copy(fontSize = 48.sp),
            )
        }

        // Start/Stop button with modern design
        Button(
            onClick = {
                // Start/Pause logic, reset the timer if stopped
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
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(
                text = if (isRunning) "Pause" else "Start",
                style = MaterialTheme.typography.button.copy(fontSize = 18.sp),
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MeditationPagePreview() {
    MeditationPage()
}
