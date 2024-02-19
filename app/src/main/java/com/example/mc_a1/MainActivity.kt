package com.example.mc_a1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mc_a1.ui.theme.MC_A1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MC_A1Theme(true) {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun StartJourneyButton(onStartJourney: () -> Unit) {
    Button(
        onClick = { onStartJourney() }, modifier = Modifier
            .padding(4.dp)
    ) {
        Text("Start Journey")
    }
}

@Composable
fun NextStopButton(onNextStop: () -> Unit) {
    Button(
        onClick = { onNextStop() }, modifier = Modifier
            .padding(4.dp)
    ) {
        Text("Next Stop")
    }
}

@Composable
fun ProgressSection(
    currentStop: Stop?,
    totalDistanceCovered: Float,
    totalDistanceLeft: Float,
    showInKm: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        currentStop?.let {
            Text("Current Stop: ${it.name}")
        }

        Spacer(modifier = Modifier.height(8.dp))

        var formattedDistance = String.format("%.2f", totalDistanceCovered)
        Text("Total Distance Covered: $formattedDistance ${if (showInKm) "km" else "miles"}")
        Spacer(modifier = Modifier.height(8.dp))

        formattedDistance = String.format("%.2f", totalDistanceLeft)
        Text("Total Distance Left: $formattedDistance ${if (showInKm) "km" else "miles"}")
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ProgressBarSection(progress: Float) {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp), progress = progress
    )
}

@Preview
@Composable
fun App() {
    var stops by remember { mutableStateOf(generateStops(10)) }
    var showInKm by remember { mutableStateOf(true) }
    var currentStopIndex by remember { mutableIntStateOf(0) }
    var totalDistanceCovered by remember {
        mutableFloatStateOf(
            calculateTotalDistanceCovered(
                stops, currentStopIndex, showInKm
            )
        )
    }
    var totalDistanceLeft by remember {
        mutableFloatStateOf(
            calculateTotalDistanceLeft(
                stops, currentStopIndex, showInKm
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Stops Tracker",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))
        GenerateStopsInput { numberOfStops ->
            stops = generateStops(numberOfStops)
            currentStopIndex = 0
            totalDistanceCovered = 0f
            totalDistanceLeft = calculateTotalDistanceLeft(stops, currentStopIndex, showInKm)
        }

        Spacer(modifier = Modifier.height(8.dp))
        DynamicStopList(stops, showInKm)

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DistanceSwitchButton(onToggle = { showInKm = it }, showInKm = showInKm)

            StartJourneyButton {
                // Reset progress when starting a new journey
                currentStopIndex = 0
                totalDistanceCovered = 0f
                totalDistanceLeft = calculateTotalDistanceLeft(stops, currentStopIndex, showInKm)
            }

            NextStopButton {
                if (currentStopIndex < stops.size - 1) {
                    currentStopIndex++
                }
            }
        }

        totalDistanceCovered = calculateTotalDistanceCovered(stops, currentStopIndex, showInKm)
        totalDistanceLeft = calculateTotalDistanceLeft(stops, currentStopIndex, showInKm)

        Spacer(modifier = Modifier.height(4.dp))
        ProgressSection(
            currentStop = stops.getOrNull(currentStopIndex),
            totalDistanceCovered = totalDistanceCovered,
            totalDistanceLeft = totalDistanceLeft,
            showInKm = showInKm
        )

        Spacer(modifier = Modifier.height(8.dp))
        ProgressBarSection(
            progress = totalDistanceCovered / (totalDistanceCovered + totalDistanceLeft)
        )
    }
}